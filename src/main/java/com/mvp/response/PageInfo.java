package com.mvp.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PageInfo {

  private Long totalElements;

  private int totalPages;

  private int pageSize;
}
