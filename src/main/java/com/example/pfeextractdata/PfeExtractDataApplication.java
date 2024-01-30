package com.example.pfeextractdata;

import com.opencsv.exceptions.CsvException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class PfeExtractDataApplication {

    public static void main(String[] args) throws IOException, CsvException {
        SpringApplication.run(PfeExtractDataApplication.class, args);

        PfeUploadService pfeUploadService=new PfeUploadService();
        pfeUploadService.persistFromFile();
    }

}
