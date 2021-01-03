package com.mvp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

  // @Id annotation specifies the primary key of an entity.
  // @GeneratedValue provides the generation strategy specification for the primary key values.
  @Id @GeneratedValue private Integer id;
  private String firstName;
  private String lastName;
  private String contactNumber;
  private String addressLine1;
  private String addressLine2;
  private String addressLine3;
  private String city;
  private String country;
  private String postalCode;
  private String status;
}
