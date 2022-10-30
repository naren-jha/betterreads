package com.njha.betterreads.userbook;

import org.springframework.data.cassandra.repository.CassandraRepository;

public interface UserBookRepository extends CassandraRepository<BookInfoByUserIdAndBookId, UserBookComboPrimaryKey> {

}
