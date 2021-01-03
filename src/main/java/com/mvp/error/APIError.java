package com.mvp.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class APIError {

  private final String code;

  private final String message;
}
