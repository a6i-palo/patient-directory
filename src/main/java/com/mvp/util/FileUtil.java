package com.mvp.util;

import com.mvp.exception.InvalidDataException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import org.springframework.web.multipart.MultipartFile;

public class FileUtil {

  public static String copyFile(MultipartFile file, String copyToDir, String fileName) {

    if (file.isEmpty()) {
      throw new InvalidDataException("Invalid file " + file.getOriginalFilename());
    } else {
      try {

        File directory = new File(copyToDir);

        if (!directory.exists()) {
          directory.mkdirs();
        }

        // Copy the contents of uploaded file
        Path path = Paths.get(copyToDir + fileName);
        Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      } catch (Exception ex) {
        throw new InvalidDataException("Error saving file: " + ex.getMessage());
      }
    }
    return fileName;
  }
}
