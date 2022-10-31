package com.njha.betterreads.home;

import com.njha.betterreads.userallbooks.BookInfoByUserIdDto;
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
            List<BookInfoByUserIdDto> userBooks = top100UserBooks.stream().distinct().map(book -> {
                String coverImageUrl = DEFAULT_NO_BOOK_COVER_IMG;
                if (book.getCoverIds() != null & book.getCoverIds().size() > 0) {
                    coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-M.jpg";
                }
                BookInfoByUserIdDto bookInfoByUserIdDto = BookInfoByUserIdDto.builder()
                        .userBook(book)
                        .coverUrl(coverImageUrl)
                        .build();
                return bookInfoByUserIdDto;
            }).collect(Collectors.toList());

            model.addAttribute("books", userBooks);
        }

        return "index";
    }
}
