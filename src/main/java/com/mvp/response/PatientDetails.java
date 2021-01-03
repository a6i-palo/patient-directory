package com.mvp.response;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PatientDetails {
  private String firstName;

  private String lastName;

  private String contactNumber;

  private String addressLine1;

  private String addressLine2;

  private String addressLine3;

  private String city;

  private String country;

  private String postalCode;
}
