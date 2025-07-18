package com.mh.cloud_storage_backend;

import com.mh.cloud_storage_backend.model.filesetup.StorageProperties;
import com.mh.cloud_storage_backend.service.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class CloudStorageBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(CloudStorageBackendApplication.class, args);
	}

	@Bean
	CommandLineRunner init(StorageService storageService) {
		return (args) -> {
			storageService.deleteAll();
			storageService.init();
		};
	}
}
