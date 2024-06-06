package com.williamblondel.infinitrade.requests;

import com.williamblondel.infinitrade.enumeration.TradeTypeEnum;
import com.williamblondel.infinitrade.model.Trade;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CreatePairTradeRequest {
    @NotNull(message = "The trade type is required.")
    private TradeTypeEnum tradeType;

    @NotNull(message = "The trade amount is required.")
    @Positive(message = "The trade amount must be greater than 0.")
    private Double amount;

    public Trade toTrade() {
        Trade trade = new Trade();

        trade.setTradeType(tradeType);
        trade.setAmount(amount);

        return trade;
    }
}
