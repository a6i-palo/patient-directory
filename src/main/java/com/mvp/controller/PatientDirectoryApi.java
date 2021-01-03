package com.mvp.controller;

import com.mvp.model.Patient;
import com.mvp.response.PatientsResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Api(
    value = "PatientAccessInfo",
    description = "API for PatientAccessInfo",
    tags = {"PatientAccessInfo"})
public interface PatientDirectoryApi {

  @ApiOperation(value = "For adding patients from csv file")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "patients list info"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  PatientsResponse createPatients(
      @RequestParam(value = "status") String status,
      final @RequestPart(value = "patientsData") MultipartFile patientsData,
      @PageableDefault(size = 10) Pageable pageable);

  @ApiOperation(value = "For adding a patient")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "patient info"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  Patient createPatient(@RequestBody Patient patient);

  @ApiOperation(value = "get patients list")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "patients list info"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  PatientsResponse getPatients(
      @RequestParam(value = "status") String status, @PageableDefault(size = 10) Pageable pageable);

  @ApiOperation(value = "For updating patients info")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "get updated patient info"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  Patient updateStudent(@RequestBody Patient patient, @PathVariable Integer id);

  @ApiOperation(value = "For deleting patient info")
  @ApiResponses(
      value = {
        @ApiResponse(code = 204, message = "No response since its deleting a patient"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  ResponseEntity<Void> deleteStudent(@PathVariable Integer id);

  @ApiOperation(value = "For searching patient by giving keyword")
  @ApiResponses(
      value = {
        @ApiResponse(code = 200, message = "get patients matched with keyword"),
        @ApiResponse(code = 404, message = "Resource not found for given URL"),
        @ApiResponse(code = 500, message = "Internal server error occurred..")
      })
  PatientsResponse searchPatient(
      @RequestParam(value = "query", required = false) String query,
      @RequestParam(value = "status", required = true) String status,
      @PageableDefault(size = 10) Pageable pageable);
}
