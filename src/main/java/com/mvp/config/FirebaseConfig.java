package com.mvp.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
@Slf4j
public class FirebaseConfig {
  @Primary
  @Bean
  public void firebaseInit() {
    InputStream inputStream = null;
    try {
      inputStream = new ClassPathResource("firebase_config.json").getInputStream();
    } catch (IOException e3) {
      e3.printStackTrace();
    }
    try {

      FirebaseOptions options =
          new FirebaseOptions.Builder()
              .setCredentials(GoogleCredentials.fromStream(inputStream))
              .build();

      if (FirebaseApp.getApps().isEmpty()) {
        FirebaseApp.initializeApp(options);
      }
      log.debug("Firebase Initialize");

    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
