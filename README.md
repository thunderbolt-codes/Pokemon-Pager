# Pokemon Pager

This Android project:

- Fetches the list of Pokemon data in pages from a public GraphQL API
- Caches the fetched data in local database using Room
- Uses the latest Paging library components to handle pagination
- Displays page items smartly (rendering only what is being visible) using LazyColumn

It uses clean architecture (presentation → domain ← data) and has dependencies on the following
libraries:

- Jetpack Compose
- Coroutines
- Hilt
- Paging v3
- Coil
- Apollo GraphQL
- Room

For more details,
see [this Medium article](https://proandroiddev.com/paging-in-android-jetpack-compose-from-caching-data-with-room-to-displaying-in-lazycolumn-a018f0b6cb2).
