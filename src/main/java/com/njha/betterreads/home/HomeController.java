package com.njha.betterreads.home;

import com.njha.betterreads.userallbooks.BookInfoByUserId;
import com.njha.betterreads.userallbooks.UserAllBooksRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.query.CassandraPageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

import static com.njha.betterreads.common.Constants.COVER_IMAGE_ROOT;
import static com.njha.betterreads.common.Constants.DEFAULT_NO_BOOK_COVER_IMG;

@Controller
public class HomeController {

    @Autowired
    private UserAllBooksRepository userAllBooksRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal OAuth2User principal) {
        if (principal != null && principal.getAttribute("login") != null) {
            String userId = principal.getAttribute("login");
            model.addAttribute("loginId", userId);

            Slice<BookInfoByUserId> top100UserBooks = userAllBooksRepository.findAllById(userId, CassandraPageRequest.of(0, 100));
            List<BookInfoByUserId> uniqueUserBooks = top100UserBooks.stream().distinct().collect(Collectors.toList());
            model.addAttribute("userBooks", uniqueUserBooks);
        }

        return "index";
    }
}
