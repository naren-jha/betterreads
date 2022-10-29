# betterreads
https://youtu.be/LxVGFBRpEFM?list=PL9MzuY4_smVbXddmeo8WU-OuvQMlRcSDx

Cassandra demo app - BetterReads (Similar to GoodReads). Data is first loaded into datastax Cassandra cluster using [this app](https://github.com/njha-collab/betterread-data-loader)

## [GitHub User authentication setup](https://youtu.be/nwyf_4aSkqM)
1. Create a GitHub App and get the Client ID and Client Secret values. (Specify callback URL as http://localhost:8080/login/oauth2/code/github for development, uncheck Web hooks)
2. Add those values in application.yml
3. Run the Spring Boot App.


## Cassandra console :
<img width="1792" alt="image" src="https://user-images.githubusercontent.com/58611230/198153521-0814456b-fffc-4286-8911-ed7f466eed7a.png">

## UI end results :
### Book detail page
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198833633-e4d6ca3b-12f8-4636-a9fd-e5a3bb87b1a5.png">

### Search result page
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198833464-f3a995ae-be1f-468c-9ac3-29b910b44a9a.png">
