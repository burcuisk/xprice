
X-price is an app that fetches product prices from e-commerce websites and displays them along with their details. Currently, it only supports the "MacBook Air M2" product, but the architecture is extendable to support different product types.

## Technologies
- **Postgres**: Used for storing persistent data such as product names and e-commerce category links.
- **Redis**: Used as a cache to store lists of product prices.
- **React**: Used on the frontend.
- **Java 21 - Spring Boot 3.2.5**: Used on the backend and for the API.
  
## Installation

Run the following command to build and run all services:
```bash
docker compose up --build
```

Access the frontend in your browser at:
http://localhost:3000

Access the swagger api documentation and usage at: 
http://localhost:8080/swagger-ui/index.html


## Limitations
- The current codebase uses mock data instead of scraping. However, a developer can implement a `ScraperService` to enable real scraping for each website.
- Currently, implementations for Hepsiburada and Trendyol are available. The `ThirdPartyPriceCheckerService` interface can be extended to support additional e-commerce websites.

## How It Works

1. **Fetching Prices**: The application is designed to fetch the latest prices for products from various e-commerce websites. Currently, it supports fetching prices for the "MacBook Air M2".

2. **Caching with Redis**: To improve performance and reduce load on the e-commerce websites, fetched prices are cached in Redis:
    - **Time-to-Live (TTL)**: The cached prices have a TTL of 3 hours. This means that the prices will expire and be removed from the cache after 3 hours.
    - **Cache Refreshing**: When a new request is made:
        - If the cache is still valid (within the 3-hour TTL), the application retrieves the prices from Redis.
        - If the cache has expired or does not exist, the application fetches the latest prices from the e-commerce websites, updates the cache in Redis with the new prices, and resets the TTL to 3 hours.

By using this architecture, XPrice ensures that users get timely and accurate product prices while optimizing performance.
