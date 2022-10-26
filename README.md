# betterreads
https://youtu.be/LxVGFBRpEFM?list=PL9MzuY4_smVbXddmeo8WU-OuvQMlRcSDx

Cassandra demo app - BetterReads (Similar to GoodReads). Data is first loaded into datastax Cassandra cluster using [this app](https://github.com/njha-collab/betterread-data-loader)

## [GitHub User authentication setup](https://youtu.be/nwyf_4aSkqM)
1. Create a GitHub App and get the Client ID and Client Secret values. (Specify callback URL as http://localhost:8080/login/oauth2/code/github for development, uncheck Web hooks)
2. Add those values in application.yml
3. Run the Spring Boot App.
