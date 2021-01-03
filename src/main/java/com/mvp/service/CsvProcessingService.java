package com.mvp.service;

import com.mvp.exception.InvalidDataException;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class CsvProcessingService {

  public List<String[]> processFile(MultipartFile CsvFile) {

    CSVReader csvReader = null;
    List<String[]> patientsData = new ArrayList<>();

    Reader reader = null;

    try {

      reader = new BufferedReader(new InputStreamReader(CsvFile.getInputStream()));

      csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

      patientsData = csvReader.readAll();

      // print Data
      for (String[] row : patientsData) {
        for (String cell : row) {
          System.out.print(cell + "\t");
        }
        System.out.println();
      }
      return patientsData;

    } catch (InvalidDataException ex) {

    } catch (Exception ex) {

    } finally {

      if (csvReader != null) {
        try {
          csvReader.close();
        } catch (IOException e) {

        }
      }

      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {

        }
      }
    }

    return patientsData;
  }
}
