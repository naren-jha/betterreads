package com.njha.betterreads.userallbooks;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class BookInfoByUserIdDto {
    private BookInfoByUserId userBook;
    private String coverUrl; // @Transient for coverUrl doesn't seem to work correctly on the main object. Using this dto for now
}
