package com.njha.betterreads.search;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.njha.betterreads.common.Constants.COVER_IMAGE_ROOT;
import static com.njha.betterreads.common.Constants.DEFAULT_NO_BOOK_COVER_IMG;

@Controller
@Slf4j
public class SearchController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    //http://localhost:8080/search?query=the+lord+of+the+rings
    @GetMapping("/search")
    public String getSearchResults(@RequestParam String query, Model model) {
        // Approach 1: using RestTemplate
        /*Map<String, String> params = new HashMap<>();
        params.put("query", query);
        SearchResult searchResult = restTemplate.getForObject("https://openlibrary.org/search.json?q={query}", SearchResult.class, params);*/

        // Approach 2: using WebClient
        SearchResult searchResult = webClient.get()
                .uri("?q={query}", query)
                .retrieve()
                .bodyToMono(SearchResult.class)
                .block();

        log.info(searchResult.toString());

        List<SearchResultBook> books = searchResult.getDocs()
                .stream()
                .limit(10)
                .map(bookResult -> {
                    // set correct book id
                    bookResult.setKey(bookResult.getKey().replace("/works/", ""));

                    // set correct image path
                    String coverId = bookResult.getCover_i();
                    String coverImage = DEFAULT_NO_BOOK_COVER_IMG;
                    if (Strings.isNotBlank(coverId)) {
                        coverImage = COVER_IMAGE_ROOT + coverId + "-M.jpg";
                    }
                    bookResult.setCover_i(coverImage);

                    return bookResult;
                })
                .collect(Collectors.toList());

        model.addAttribute("searchResultBooks", books);

        return "search-result";
    }
}
