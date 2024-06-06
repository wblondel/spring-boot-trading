package com.williamblondel.infinitrade.tasks;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.williamblondel.infinitrade.model.Pair;
import com.williamblondel.infinitrade.repository.PairRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.format.DateTimeFormatter;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final PairRepository pairRepository;

    public ScheduledTasks(PairRepository pairRepository) {
        this.pairRepository = pairRepository;
    }

    @Scheduled(
            fixedRateString = "${get.prices.fixed-rate.in.milliseconds}",
            initialDelayString = "${get.prices.initial-delay.in.milliseconds}"
    )
    public void getPairPrices() {
        // TODO: refactor this code into Services!
        //  Proper error handling & timeout handling should also be done.

        logger.info("Getting pair prices...");
        WebClient client = WebClient.create();

        logger.debug("Fetching from Binance...");

        // Get prices from Binance
        // We only support pairs ETH-USDT and BTC-USDT for now, so we do not fetch all tickers (saves bandwidth)
        WebClient.ResponseSpec responseSpecBinance = client.get()
                .uri("https://api.binance.com/api/v3/ticker/bookTicker?symbols=[\"ETHUSDT\",\"BTCUSDT\"]")
                .retrieve();
        String responseBodyBinance = responseSpecBinance.bodyToMono(String.class).block();
        ObjectMapper objectMapperBinance = new ObjectMapper();

        logger.debug("OK.");

        logger.debug("Fetching from Huobi...");
        // Get prices rom Huobi
        // Other endpoints exist to only get the useful tickers, but based on specs this URL must be used
        WebClient.ResponseSpec responseSpecHuobi = client.get()
                .uri("https://api.huobi.pro/market/tickers")
                .retrieve();
        String responseBodyHuobi = responseSpecHuobi.bodyToMono(String.class).block();
        ObjectMapper objectMapperHuobi = new ObjectMapper();

        logger.debug("OK.");

        logger.debug("Parsing JSON...");
        // Get JsonNode
        JsonNode rootNodeBinance;
        JsonNode rootNodeHuobi;
        try {
            rootNodeBinance = objectMapperBinance.readTree(responseBodyBinance);
            rootNodeHuobi = objectMapperHuobi.readTree(responseBodyHuobi).get("data");
        } catch (Exception e) {
            logger.error("Failed to parse response to JSON object.");
            return;
        }

        logger.debug("OK.");

        logger.debug("Updating pairs...");

        // Let's iterate over all the Pairs in our database, and find the best price in the two json nodes
        // then, save the best price
        for (Pair pair : pairRepository.findAll()) {
            String currentPairCode = pair.getPairCode();

            // find the item with "symbol" = currentPairCode in rootNodeBinance
            // Currently, Binance symbols are in uppercase, while Huobi symbols are in lower case.
            // Since this might change in the future, we check for both.
            String[] symbolsToFind = {currentPairCode, currentPairCode.toLowerCase()};

            JsonNode pairBinanceNode = findFirstNodeBySymbol(rootNodeBinance, symbolsToFind);
            JsonNode pairHuobiNode = findFirstNodeBySymbol(rootNodeHuobi, symbolsToFind);

            // We must do price aggregation, I assume that if one of the two sources is not available, we
            // do not update the price
            if (pairBinanceNode == null || pairHuobiNode == null) {
                logger.debug("Pair {} not found", currentPairCode);
                continue;
            }

            double newBidPrice;
            double newAskPrice;
            // let's assume the "best pricing" is the set of prices where the bid price is the highest
            double binanceBidPrice = pairBinanceNode.get("bidPrice").asDouble();
            double huobiBidPrice = pairHuobiNode.get("bid").asDouble();
            double binanceAskPrice = pairBinanceNode.get("askPrice").asDouble();
            double huobiAskPrice = pairHuobiNode.get("ask").asDouble();

            if (binanceBidPrice >= huobiBidPrice) {
                newBidPrice = binanceBidPrice;
                newAskPrice = binanceAskPrice;
            } else {
                newBidPrice = huobiBidPrice;
                newAskPrice = huobiAskPrice;
            }

            // let's update the pair
            logger.debug("Updating prices of pair {} from {} {} to {} {}.", pair.getPairCode(), pair.getBidPrice(),
                    pair.getAskPrice(), newBidPrice, newAskPrice);
            pair.setBidPrice(newBidPrice);
            pair.setAskPrice(newAskPrice);
            pairRepository.save(pair);
            logger.debug("Pair {} updated.", pair.getPairCode());
        }
        logger.info("Done.");
    }

    private static JsonNode findNodeBySymbol(JsonNode rootNode, String symbol) {
        if (rootNode.isArray()) {
            for (JsonNode node : rootNode) {
                if (node.has("symbol") && node.get("symbol").asText().equals(symbol)) {
                    return node;
                }
            }
        }

        return null;
    }

    private static JsonNode findFirstNodeBySymbol(JsonNode rootNode, String[] symbols) {
        for (String symbol : symbols) {
            JsonNode node = findNodeBySymbol(rootNode, symbol);

            if (node != null) {
                return node;
            }
        }

        return null;
    }
}
