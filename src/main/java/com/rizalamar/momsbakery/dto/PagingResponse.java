package com.rizalamar.momsbakery.dto;

public record PagingResponse(
        Integer currentPage,
        Integer totalPage,
        Integer size
) {
}
