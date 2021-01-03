package com.mvp.repo;

import com.mvp.model.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

  @Query(
      nativeQuery = true,
      value = "SELECT * FROM Patient p WHERE p.status = :status  ORDER BY p.id")
  Page<Patient> getPatients(String status, Pageable pageable);

  // For fetching patients according to search criteria with status as active and inactive
  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM Patient p WHERE (UPPER(p.first_name) LIKE CONCAT('%', UPPER(:keyword), '%')"
              + " OR UPPER(p.last_name) LIKE CONCAT('%', UPPER(:keyword), '%')"
              + " OR UPPER(p.country) LIKE CONCAT('%', UPPER(:keyword), '%'))"
              + " AND p.status = :status  ORDER BY p.first_name")
  Page<Patient> search(String keyword, String status, Pageable pageable);

  // For fetching all patients according to search criteria irrespective of status
  @Query(
      nativeQuery = true,
      value =
          "SELECT * FROM Patient p WHERE UPPER(p.first_name) LIKE CONCAT('%', UPPER(:keyword), '%')"
              + " OR UPPER(p.last_name) LIKE CONCAT('%', UPPER(:keyword), '%')"
              + " OR UPPER(p.country) LIKE CONCAT('%', UPPER(:keyword), '%')"
              + " ORDER BY p.first_name")
  Page<Patient> search(String keyword, Pageable pageable);
}
