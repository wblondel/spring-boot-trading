package com.williamblondel.infinitrade.requests;

import com.williamblondel.infinitrade.model.Currency;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateCurrencyRequest {
    @NotNull(message = "The ticker is required.")
    @Size(min = 1, max = 15, message = "The ticker must be between 1 and 15 characters.")
    private String ticker;

    @NotNull(message = "The name is required.")
    @Size(min = 1, max = 30, message = "The name must be between 1 and 30 characters.")
    private String name;

    @NotNull(message = "The description is required.")
    @Size(min = 30, max = 10000, message = "The description must be between 30 and 10000 characters.")
    private String description;

    @NotNull(message = "The website is required.")
    @URL(message = "The website must be a URL.")
    private String website;

    public Currency toCurrency() {
        Currency currency = new Currency();

        currency.setTicker(ticker);
        currency.setName(name);
        currency.setDescription(description);
        currency.setWebsite(website);

        return currency;
    }
}
