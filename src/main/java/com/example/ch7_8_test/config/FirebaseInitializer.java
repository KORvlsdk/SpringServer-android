package com.example.ch7_8_test.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

@Configuration
public class FirebaseInitializer {

    private static boolean initialized = false;

    @Bean
    public FirebaseApp initializeFirebaseApp() throws IOException {
        System.out.println("파이어 베이스 초기화");
        FileInputStream serviceAccount = new FileInputStream("firebase.json");
        System.out.println(serviceAccount);
        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .build();

        FirebaseApp app = FirebaseApp.initializeApp(options);
        System.out.println("파이어 베이스 초기화" + app.getName());
        return app;
    }

    @Bean
    public FirebaseAuth getFirebaseAuth() throws IOException {
        return FirebaseAuth.getInstance(initializeFirebaseApp());
    }
}
