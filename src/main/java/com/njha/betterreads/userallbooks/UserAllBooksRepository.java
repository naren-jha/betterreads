package com.njha.betterreads.userallbooks;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface UserAllBooksRepository extends CassandraRepository<BookInfoByUserId, String> {
    Slice<BookInfoByUserId> findAllById(String userId, Pageable pageable);
}
