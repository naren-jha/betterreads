# betterreads
https://youtu.be/LxVGFBRpEFM?list=PL9MzuY4_smVbXddmeo8WU-OuvQMlRcSDx

Cassandra demo app - BetterReads (Similar to GoodReads). Data is first loaded into datastax Cassandra cluster using [this app](https://github.com/njha-collab/betterread-data-loader)

## GitHub User authentication setup
Follow this [video](https://youtu.be/nwyf_4aSkqM) and [this] or [this (for rective)](https://github.com/koushikkothagal/spring-github-login-starter) github repository to this
1. Create a GitHub App and get the Client ID and Client Secret values. (Specify callback URL as http://localhost:8080/login/oauth2/code/github for development, uncheck Web hooks)
2. Add those values in application.yml
3. Run the Spring Boot App.


##  Summary :
### Uploading data to Cassandra cluster:
First we upload data to datastax Cassandra cluster using [betterread-data-loader](https://github.com/njha-collab/betterread-data-loader) app. We get data dump from https://openlibrary.org/data
Cassandra console after data uplaod:
<img width="1792" alt="image" src="https://user-images.githubusercontent.com/58611230/198153521-0814456b-fffc-4286-8911-ed7f466eed7a.png">

### Book detail page
We get book by id from Cassandra book_by_id table and display it to UI
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198833633-e4d6ca3b-12f8-4636-a9fd-e5a3bb87b1a5.png">

### Search result page
We make call to https://openlibrary.org/search.json?q={query} api to get search result. We can't perform search in Cassandra as that would be very slow. Ideally we should used an Apache Lucene based text search engine (such as elastic-search or solr). We should have build an index in elastic-search from Cassandra (or Kafka depending on how we design it) and then we should have performed the search on elastic-search. However, since the scope of this app was to learn how to use Cassandra db, we are leveraging openlibrary's available API. 
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198834770-57aa76d2-e1e7-4c95-8aa2-3c7591c07b64.png">
