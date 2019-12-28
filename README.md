# A simplified stock market implementation
It is a test implementation of a basic stock market for an unmentioned challenge. You can post stocks and trades and also perform certain operations on them (please see the api docs for further details on this). 

# Requirements:
    - The project is using maven so it needs to be installed.

# How to run?

To start the api from terminal, type: mvn spring-boot:run

The api is available on localhost:8080

You can view the api docs on: http://localhost:8080/swagger-ui.html

# How to test?
The easiest way is to import the JP.postman_collection.json into postman where I prepared some api calls you can play around with.

# Further improvements
## It was not part of the description, but in real world scenario I would:
    - Implement GET, POST, PUT and DELETE for all of the endpoints
    - Write unit test for the controller and service layer
    - Write integration tests (e.g.: with mock mvc)
    - Write e2e tests
    - Persist data in DB
    
# Assumptions made:
    - The dividend in the P/E ratio formula is the last dividend for common and fixed dividend * par value for preferred stocks.
        - That way I could calculate the dividend yield as getDividend() / price for both cases. This will reduce redundancy,
         but there is a possibility, that the dividend should be calculated differently (e.g.: used the last dividend in both cases),
         so I just use the given formula for each.
    - If the dividend is zero, the P/E ratio is defined to be zero.
    - I assume that stock symbol is unique.
