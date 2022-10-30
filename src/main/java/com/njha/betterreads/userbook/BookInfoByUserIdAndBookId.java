package com.njha.betterreads.userbook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.LocalDate;

/**
 * This class represents user's interactions with a book.
 * Like, a user will provide rating for a book, or mark a book as 'read' or 'currently-reading',
 * or add a date for when they started reading and when they finished reading a book, etc.
 * So its additional information about a book that a logged-in user will create in our system.
 *
 * This object will have to be partitioned by both user_id and book_id, because when a user visits a book detail page
 * they will be shown the extra information about just that book (book_id) for just that logged-in user (user_id).
 * So when we query this information from db, we would want to be able to query this by logged-in userid and bookid.
 * Each user has their own activity about each book that they interact with.
 * So here Cassandra is again used like a key-value storage with mapping of "(userid-bookid) -> BookInfoByUserIdAndBookIdObject"
 */
@Table(value = "book_by_userid_and_bookid")
@Setter
@Getter
@Builder
public class BookInfoByUserIdAndBookId {

    @PrimaryKey
    private UserBookComboPrimaryKey key;

    @Column("rating")
    @CassandraType(type = CassandraType.Name.INT)
    private int rating;

    @Column("reading_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String readingStatus;

    @Column("started_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate startedDate;

    @Column("completed_date")
    @CassandraType(type = CassandraType.Name.DATE)
    private LocalDate completedDate;
}
