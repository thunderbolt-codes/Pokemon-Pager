# Pokemon Pager

This project:

- Fetches the list of Pokemon data in pages from a public GraphQL API
- Caches the fetched data in local database using Room
- Uses the latest Paging library components to handle pagination
- Displays page items smartly (rendering only what is being visible) using LazyColumn

This project uses clean architecture (presentation → domain ← data) and has dependencies on the following libraries:

- Jetpack Compose
- Hilt
- Paging v3
- Coil
- Apollo GraphQL
- Room
