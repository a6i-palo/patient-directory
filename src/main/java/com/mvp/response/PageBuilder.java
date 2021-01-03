package com.mvp.response;

public class PageBuilder {
  public static PageInfo forPageDetails(long totalElements, int totalPages, int pageSize) {

    return PageInfo.builder()
        .totalElements(totalElements)
        .totalPages(totalPages)
        .pageSize(pageSize)
        .build();
  }
}
