
XPrice is an app that fetches product prices from e-commerce websites and displays them along with their details. Currently, it only supports the "MacBook Air M2" product, but the architecture is extendable to support different product types.

## Technologies
- **Postgres**: Used for storing persistent data such as product names and e-commerce category links.
- **Redis**: Used as a cache to store lists of product prices.
- **React**: Used on the frontend.
- **Spring**: Used on the backend and for the API.
  
## Installation

Run the following command to build and run all services:
```bash
docker compose up --build
```

Access the frontend in your browser at:
http://localhost:3000

Access the swagger api documentation and usage at: 
http://localhost:8080/swagger-ui/index.html

Access the frontend in your browser at:
http://localhost:3000


## Limitations
- The current codebase uses mock data instead of scraping. However, a developer can implement a `ScraperService` to enable real scraping for each website.
