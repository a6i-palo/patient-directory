package com.mvp.controller;

import com.mvp.exception.InvalidDataException;
import com.mvp.model.Patient;
import com.mvp.response.PatientsResponse;
import com.mvp.service.CsvProcessingService;
import com.mvp.service.PatientService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientDirectoryController implements PatientDirectoryApi {

  private final PatientService patientService;

  private final CsvProcessingService csvProcessingService;

  @PostMapping("/patients")
  public PatientsResponse createPatients(
      @RequestParam(value = "status") String status,
      final @RequestPart(value = "patientsData") MultipartFile patientsData,
      @PageableDefault(size = 10) Pageable pageable) {

    List<String[]> patientsDetails = csvProcessingService.processFile(patientsData);

    patientService.save(patientService.createPatientsList(patientsDetails));

    return patientService.getPatients(status, pageable);
  }

  @PostMapping("/patient")
  public Patient createPatient(@RequestBody Patient patient) {

    return patientService.save(patient);
  }

  @GetMapping("/patients")
  public PatientsResponse getPatients(
      @RequestParam(value = "status") String status,
      @PageableDefault(size = 10) Pageable pageable) {

    return patientService.getPatients(status, pageable);
  }

  @PutMapping("/patients/{id}")
  public Patient updateStudent(@RequestBody Patient patient, @PathVariable Integer id) {

    Optional<Patient> patientOptional = patientService.findPatient(id);

    if (!patientOptional.isPresent()) throw new InvalidDataException("Invalid Id " + id);

    patient.setId(id);

    patientService.save(List.of(patient));

    return patientService.findPatient(id).get();
  }

  @DeleteMapping("/patients/{id}")
  public ResponseEntity<Void> deleteStudent(@PathVariable Integer id) {

    Optional<Patient> patientOptional = patientService.findPatient(id);

    if (!patientOptional.isPresent()) throw new InvalidDataException("Invalid Id " + id);

    patientService.delete(patientOptional.get());

    return ResponseEntity.noContent().build();
  }

  @GetMapping("/search/patient")
  public PatientsResponse searchPatient(
      @RequestParam(value = "query") String query,
      @RequestParam(value = "status") String status,
      @PageableDefault(size = 10) Pageable pageable) {

    return patientService.searchPatient(query, status, pageable);
  }
}
