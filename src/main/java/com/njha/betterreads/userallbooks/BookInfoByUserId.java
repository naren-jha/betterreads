package com.njha.betterreads.userallbooks;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.UUID;

import static com.njha.betterreads.common.Constants.COVER_IMAGE_ROOT;
import static com.njha.betterreads.common.Constants.DEFAULT_NO_BOOK_COVER_IMG;

@Table(value = "books_by_userid")
@Setter
@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BookInfoByUserId {

    @PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    @EqualsAndHashCode.Include
    private String id;

    @PrimaryKeyColumn(name = "book_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED)
    @CassandraType(type = CassandraType.Name.TEXT)
    @EqualsAndHashCode.Include
    private String bookId;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CassandraType(type = CassandraType.Name.TIMEUUID)
    private UUID timeUuid;

    @PrimaryKeyColumn(type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    @CassandraType(type = CassandraType.Name.TEXT)
    private String readingStatus;

    @Column("book_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String bookName;

    @Column("author_names")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> authorNames;

    @Column("cover_ids")
    @CassandraType(type = CassandraType.Name.LIST, typeArguments = CassandraType.Name.TEXT)
    private List<String> coverIds;

    @Column("rating")
    @CassandraType(type = CassandraType.Name.INT)
    private int rating;

    public String getCoverUrl() {
        String coverImageUrl = DEFAULT_NO_BOOK_COVER_IMG;
        if (coverIds != null && coverIds.size() > 0) {
            coverImageUrl = COVER_IMAGE_ROOT + coverIds.get(0) + "-L.jpg";
        }
        return coverImageUrl;
    }
}
