package com.mh.cloud_storage_backend.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

    void init();

    void store(MultipartFile file, String username, String localDirectory, String fileId, String chunkId);

    Stream<Path> loadAll();

    Path load(String filename);

    Resource loadAsResource(String filename);

    void deleteAll();

}
