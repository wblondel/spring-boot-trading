package com.williamblondel.infinitrade.requests;

import com.williamblondel.infinitrade.enumeration.TradeTypeEnum;
import com.williamblondel.infinitrade.model.Trade;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreatePairTradeRequest {
    @NotNull(message = "The trade type is required.")
    private TradeTypeEnum tradeType;

    @NotNull(message = "The trade amount is required.")
    @Positive(message = "The trade amount must be greater than 0.")
    private Double amount;

    public Trade toTrade() {
        return new Trade()
                .setTradeType(tradeType)
                .setAmount(amount);
    }
}
