package com.njha.betterreads.book;

import com.njha.betterreads.userbook.BookInfoByUserIdAndBookId;
import com.njha.betterreads.userbook.UserBookComboPrimaryKey;
import com.njha.betterreads.userbook.UserBookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

import static com.njha.betterreads.common.Constants.COVER_IMAGE_ROOT;
import static com.njha.betterreads.common.Constants.DEFAULT_NO_BOOK_COVER_IMG;

@Controller
@RequestMapping("book")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserBookRepository userBookRepository;

    @GetMapping("/{bookId}")
    public String getBook(@PathVariable("bookId") String bookId, Model model, @AuthenticationPrincipal OAuth2User principal) {
        Optional<Book> bookOptional = bookRepository.findById(bookId);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();

            String coverImageUrl = DEFAULT_NO_BOOK_COVER_IMG;
            if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-L.jpg";
            }

            model.addAttribute("coverImage", coverImageUrl);
            model.addAttribute("book", book);

            if (principal != null && principal.getAttribute("login") != null) {
                String userId = principal.getAttribute("login");
                model.addAttribute("loginId", userId);
                UserBookComboPrimaryKey userBookPK = UserBookComboPrimaryKey.builder()
                        .userId(userId)
                        .bookId(bookId)
                        .build();
                Optional<BookInfoByUserIdAndBookId> userBookInfo = userBookRepository.findById(userBookPK);

                if (userBookInfo.isPresent()) {
                    model.addAttribute("userBookInfo", userBookInfo.get());
                } else {
                    model.addAttribute("userBookInfo", BookInfoByUserIdAndBookId.builder().build());
                }
            }
            return "book";
        }

        return "book-not-found";
    }
}
