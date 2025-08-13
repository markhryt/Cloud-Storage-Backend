package com.mh.cloud_storage_backend.controller;

import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.requests.FileDownloadRequest;
import com.mh.cloud_storage_backend.service.FileDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;

@RequestMapping("/download")
@Controller
public class DonwloadController {

    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping("/file")
    public ResponseEntity<?> downloadFile(@RequestBody FileDownloadRequest fileDownloadRequest){
        Files file = fileDownloadService.getFileByDownloadRequest(fileDownloadRequest);

        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping("/file/chunk/{file_id}/{chunk_id}")
    public ResponseEntity<?> downloadFileChunk(@PathVariable("chunk_id") String chunkId) {
        File file_chunk = fileDownloadService.getFileChunk(chunkId);
        if (file_chunk == null) {
            return new ResponseEntity<>("File chunk not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(file_chunk, HttpStatus.OK);
    }
}

