package com.njha.betterreads.userbook;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyClass;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;

/**
 * We are going to use this primary key class for book_by_userid_and_bookid table
 * we are creating this separate primary key class because in spring repository we'll have to
 * mention the primary key object type (see UserBookRepository). And since there are two
 * primary key columns here, so we are exporting that in this new class then going to use it in
 * BookInfoByUserIdAndBookId
 * Also, even without the repository forcing it's way on us, this should be the right way to do it?
 */
@PrimaryKeyClass
@Setter
@Getter
@Builder
public class UserBookComboPrimaryKey {
    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String userId;

    @PrimaryKeyColumn(name = "book_id", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private String bookId;
}
