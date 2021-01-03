package com.mvp.service;

import com.mvp.model.Patient;
import com.mvp.repo.PatientRepository;
import com.mvp.response.PageBuilder;
import com.mvp.response.PageInfo;
import com.mvp.response.PatientsResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientService {

  private final PatientRepository patientRepository;

  public List<Patient> createPatientsList(List<String[]> patientsDetails) {

    List<Patient> patients = new ArrayList<>();

    for (String[] patientDetail : patientsDetails) {

      Patient patient = new Patient();

      patient.setFirstName(patientDetail[0]);
      patient.setLastName(patientDetail[1]);
      patient.setContactNumber(patientDetail[2]);
      patient.setAddressLine1(patientDetail[3]);
      patient.setAddressLine2(patientDetail[4]);
      patient.setAddressLine3(patientDetail[5]);
      patient.setCity(patientDetail[6]);
      patient.setCountry(patientDetail[7]);
      patient.setPostalCode(patientDetail[8]);
      patient.setStatus("ACTIVE");
      patients.add(patient);
    }
    return patients;
  }

  PatientsResponse getPaginatedPatients(Page<Patient> paginatedPatients, Pageable pageable) {

    PageInfo pageInfo = null;
    List<Patient> patientsInfo = new ArrayList<>();

    if (paginatedPatients != null
        && paginatedPatients.getTotalElements() > 0
        && pageable.getPageNumber() <= paginatedPatients.getTotalPages() - 1) {

      pageInfo =
          PageBuilder.forPageDetails(
              paginatedPatients.getTotalElements(),
              paginatedPatients.getTotalPages(),
              paginatedPatients.getSize());

      patientsInfo = paginatedPatients.toList();
    }

    return PatientsResponse.builder().pageInfo(pageInfo).patients(patientsInfo).build();
  }

  public PatientsResponse getPatients(String status, Pageable pageable) {
    Page<Patient> paginatedPatients = null;
    if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("INACTIVE")) {
      paginatedPatients = patientRepository.findAll(pageable);
    } else {
      paginatedPatients = patientRepository.getPatients(status, pageable);
    }
    return getPaginatedPatients(paginatedPatients, pageable);
  }

  public PatientsResponse searchPatient(String keyword, String status, Pageable pageable) {
    Page<Patient> paginatedSearchPatients = null;
    if (!status.equalsIgnoreCase("ACTIVE") && !status.equalsIgnoreCase("INACTIVE")) {
      paginatedSearchPatients = patientRepository.search(keyword, pageable);
    } else {
      paginatedSearchPatients = patientRepository.search(keyword, status, pageable);
    }
    return getPaginatedPatients(paginatedSearchPatients, pageable);
  }

  public Optional<Patient> findPatient(Integer id) {
    return patientRepository.findById(id);
  }

  public void save(List<Patient> patientsInfo) {
    patientRepository.saveAll(patientsInfo);
  }

  public Patient save(Patient patient) {
    return patientRepository.save(patient);
  }

  public void delete(Patient patient) {
    patientRepository.delete(patient);
  }
}
