package com.njha.betterreads.userbook;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.njha.betterreads.book.Book;
import com.njha.betterreads.book.BookRepository;
import com.njha.betterreads.userallbooks.BookInfoByUserId;
import com.njha.betterreads.userallbooks.UserAllBooksRepository;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.Optional;

@Controller
public class UserBookController {

    @Autowired
    private UserBookRepository userBookRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserAllBooksRepository userAllBooksRepository;

    @PostMapping("/addBookInfoForUser")
    public ModelAndView addBookInfoForUser(@RequestBody MultiValueMap<String, String> formData, @AuthenticationPrincipal OAuth2User principal) {

        if (principal == null || principal.getAttribute("login") == null) {
            return null; // user should be logged-in to do this
        }
        String userId = principal.getAttribute("login");

        String bookId = formData.getFirst("bookId");
        if (Strings.isBlank(bookId)) {
            return null; // book id should be present
        }

        UserBookComboPrimaryKey userBookPK = UserBookComboPrimaryKey.builder()
                .userId(userId)
                .bookId(bookId)
                .build();

        BookInfoByUserIdAndBookId userBookInfo = BookInfoByUserIdAndBookId.builder()
                .key(userBookPK)
                .rating(Integer.valueOf(formData.getFirst("rating")))
                .readingStatus(formData.getFirst("readingStatus"))
                .startedDate(LocalDate.parse(formData.getFirst("startDate")))
                .completedDate(LocalDate.parse(formData.getFirst("completedDate")))
                .build();

        userBookRepository.save(userBookInfo);

        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            BookInfoByUserId allUserBooksInfo = BookInfoByUserId.builder()
                    .id(userId)
                    .bookId(book.getId())
                    .timeUuid(Uuids.timeBased())
                    .readingStatus(formData.getFirst("readingStatus"))
                    .bookName(book.getName())
                    .authorNames(book.getAuthorNames())
                    .coverIds(book.getCoverIds())
                    .rating(Integer.valueOf(formData.getFirst("rating")))
                    .build();
            userAllBooksRepository.save(allUserBooksInfo);
        }

        return new ModelAndView("redirect:book/"+ bookId);
    }
}
