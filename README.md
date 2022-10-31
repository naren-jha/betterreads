# betterreads

Cassandra demo app - BetterReads (Similar to GoodReads). Data is first loaded into datastax Cassandra cluster using [this app](https://github.com/njha-collab/betterread-data-loader)

[Youtube playlist](https://youtu.be/LxVGFBRpEFM?list=PL9MzuY4_smVbXddmeo8WU-OuvQMlRcSDx)

### GitHub User authentication setup
Follow this [video](https://youtu.be/nwyf_4aSkqM) and [this](https://github.com/koushikkothagal/spring-github-login-starter) (or [this](https://github.com/koushikkothagal/spring-reactive-github-login-starter)) github repositories to build oauth2Login using github.
1. Create a GitHub App and get the Client ID and Client Secret values. (Specify callback URL as http://localhost:8080/login/oauth2/code/github for development, uncheck Web hooks)
2. Add those values in application.yml
3. Run the Spring Boot App.

### Creating books data in Cassandra cluster:
* First we create books data in datastax Cassandra cluster using [betterread-data-loader](https://github.com/njha-collab/betterread-data-loader) app. 
* We get data dump from https://openlibrary.org/data. checkout openlibrary developers support page - https://openlibrary.org/developers
* And then process dump file and create authors data and books data in Cassandra cluster.

**Cassandra console after data creation:**
<img width="1792" alt="image" src="https://user-images.githubusercontent.com/58611230/198153521-0814456b-fffc-4286-8911-ed7f466eed7a.png">

Checkout the [JavaClass](https://github.com/njha-collab/betterreads/blob/master/src/main/java/com/njha/betterreads/book/Book.java) For book_by_id table which facilitates this feature.

### Book detail:
We get book by id from Cassandra book_by_id table and display it to UI
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198833633-e4d6ca3b-12f8-4636-a9fd-e5a3bb87b1a5.png">

### Book search:
We make call to https://openlibrary.org/search.json?q={query} api to get search result. We can't perform search in Cassandra as that would be very slow. Ideally we should have used an Apache Lucene based text search engine (such as elastic-search or solr). We should have build an index in elastic-search from Cassandra (or Kafka depending on how we design it) and then we should have performed the search query on elastic-search. However, since the scope of this app was to learn how to use Cassandra db, we are leveraging openlibrary's available search API - https://openlibrary.org/dev/docs/api/search. 
<img width="1790" alt="image" src="https://user-images.githubusercontent.com/58611230/198834770-57aa76d2-e1e7-4c95-8aa2-3c7591c07b64.png">

### Tracking user interactions with books:
**Cassandra console:** For every user interaction with a new book, a new row will be added in this table
1. user njha-collab adds info for book OL10005006W 
2. user njha-collab updates info for book OL10005006W (same entry is updated)
3. user njha-collab adds info for another book OL10009701W 
![image](https://user-images.githubusercontent.com/58611230/198901956-bcc42b3f-50e7-4eea-b1ad-7701fcc50d66.png)

**UI:**
![image](https://user-images.githubusercontent.com/58611230/198902049-70b959b2-8675-476e-9e39-edcab624ef9a.png)
![image](https://user-images.githubusercontent.com/58611230/198902063-b7b948af-95bc-4eac-879d-569b20d27803.png)


### My books feature:
Displays top books that a user has interacted with on user's home page
**Cassandra console:**
![image](https://user-images.githubusercontent.com/58611230/198909098-f6b616c3-0c4d-49ec-885d-e33a353b09f6.png)

**UI:**
![image](https://user-images.githubusercontent.com/58611230/198909034-d1c1ce34-e267-493e-9a59-984e5100e526.png)
