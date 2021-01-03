package com.mvp.response;

import com.mvp.model.Patient;
import java.util.List;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class PatientsResponse {
  private PageInfo pageInfo;

  private List<Patient> patients;
}
