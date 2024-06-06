# InfiniTrade trading platform
![Banner](https://exc-prodp.s3.eu-west-2.amazonaws.com/uploads/trading-psychology-in-forex-rules-and-tips-for-traders.png)

[![en](https://img.shields.io/badge/lang-en-red.svg)](./README.md)

The InfiniTrade trading platform is an experimental project.

It is a Gradle - Groovy project, and Java is used as the language.

Java 22 is set as the compatible version so that I can experiment with the latest features.

The framework used is Spring Boot.

API collections are available for you to quickly test the API:
- [Bruno](https://www.usebruno.com/) API collection: [.apiCollection/InfiniTrade](./.apiCollection/InfiniTrade) folder
- [Postman](https://www.postman.com/) API collection: [.apiCollection/InfiniTrade/postman.json](./.apiCollection/InfiniTrade/postman.json)

## Limitations and known bugs
- No roles, permissions, or authentication: we assume the authenticated user is User 1, and this user can access all endpoints
- I made the mistake of using doubles, which lead to accuracy problems (I wanted to save time...). I should store the amounts in the currency's smallest unit using a BigInteger, and store the smallest unit (aka precision) on each Currency.
- The logic inside the `getPairPrices()` scheduled task should be extracted to a Service.
- There is no proper error handling and timeout handling inside the `getPairPrices()` task.
- No tests were written.

## Models
### Currency
A Currency has the following data:
- Ticker (e.g BTC)
- Name
- Description
- Website

### Pair
A Pair has the following data:
- Base Currency (link to Currency)
- Quote Currency (link to Currency)
- Pair code (e.g BTCUSDT)
- Bid price
- Ask price

A Pair has many Trades.

### Trade
A Trade is a trading transaction of any available Pair, and it has the following data:
- Pair (link to Pair)
- User (link to User)
- Type (BUY or SELL, via an enum)
- Amount (amount of base currency)
- Price (price per unit of base currency)

### Transaction
A Transaction is a deposit to or a withdrawal from a Wallet, and it has the following data:
- Wallet (link to Wallet)
- Amount
- Type (DEPOSIT or WITHDRAWAL, via an enum)
- Status (PENDING, COMPLETED, or FAILED, via an enum)

### User
A User has the following data:
- Username
- Email

A User has many Wallets and has many Trades.

### Wallet
A Wallet has the following data:
- User (link to User)
- Currency (link to Currency)
- Balance

A Wallet has many Transactions.

## Features
### General
- Every 10 seconds, a task is executed to get the best pricing from Binance and Huobi for the pairs ETHUSDT and BTCUSDT (the best pricing is the set of prices where the bid price is the highest)

### Currency
- Users can view all Currencies available on the platform
- Users can view a specific Currency available on the platform
- Users can create a Currency

### Pair
- Users can view all Pairs available on the platform
- Users can view a specific Pair available on the platform
- Users can view all Trades of a specific Pair
- Users can trade a specific Pair (buy and sell)

### Trade
- Users can view all Trades
- Users can view a specific Trade
- Users can view their Trade history via the `/me/trades` route

### Transaction
- Users can view all Transactions
- Users can view a specific Transaction

### Wallet
- Users can view all Wallets
- Users can view a specific Wallet
- Users can view all Transactions of a specific Wallet
- Users can view their own Wallets via the `/me/wallets` route

### Users
- Users can view all Users
- Users can view a specific User
- Users can view all Trades of a specific User
- Users can view all Wallets of a specific User
- Users can view their own User via the `/me` route
