package com.njha.betterreads.search;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

/**
 * This class configures beans for making search API calls.
 * We are going to explore two approaches
 *  - a traditional approach using RestTemplate
 *  - and a contemporary approach using WebClient.
 *
 * RestTemplate -:
 * RestTemplate is a synchronous client to perform HTTP requests, exposing a simple, template method API over underlying HTTP client libraries such as the JDK HttpURLConnection, Apache HttpComponents, and others.
 * further reading:
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/client/RestTemplate.html
 * https://howtodoinjava.com/spring-boot2/resttemplate/spring-restful-client-resttemplate-example/
 * https://www.baeldung.com/rest-template
 *
 * WebClient -:
 * WebClient is a non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.
 * further reading:
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/web/reactive/function/client/WebClient.html
 * https://www.baeldung.com/spring-5-webclient
 * https://reflectoring.io/spring-webclient/
 *
 * Comparison: RestTemplate vs WebClient -:
 * https://www.baeldung.com/spring-webclient-resttemplate
 * https://stackoverflow.com/questions/47974757/webclient-vs-resttemplate
 */
@Configuration
public class SearchConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofMillis(3000))
                .setReadTimeout(Duration.ofMillis(3000))
                .build();
    }

    @Bean
    public WebClient webClient() {
        ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
                .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024))
                .build();

        return WebClient.builder()
                .exchangeStrategies(exchangeStrategies)
                .baseUrl("http://openlibrary.org/search.json")
                .build();

        // we are defining exchangeStrategies and overriding memory size here,
        // because default buffer size supported by WebClient doesn't seem to be enough
        // for the result that we are receiving from openlibrary search API call
    }

}
