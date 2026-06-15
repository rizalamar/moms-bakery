package com.rizalamar.momsbakery.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record WebResponse<T>(
        Integer code,
        T data,
        ErrorResponse errors,
        PagingResponse paging
) {
}
