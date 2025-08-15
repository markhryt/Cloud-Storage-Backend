package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.FileChunks;
import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.repository.FileChunkRepository;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileDownloadServiceTest {

    @Mock
    private FilesRepo filesRepo;

    @Mock
    private FileChunkRepository fileChunkRepository;

    @InjectMocks
    private FileDownloadService fileDownloadService;

    @TempDir
    Path tempDir;

    private String chunkId;
    private FileChunks mockChunk;
    private Files mockParentFile;
    private Users mockUser;
    private File expectedFile;

    @BeforeEach
    void setUp() throws IOException {
        chunkId = "test-chunk-id";

        // Create mock objects
        mockUser = new Users();
        mockUser.setId("user123");

        mockParentFile = new Files();
        mockParentFile.setId("file123");
        mockParentFile.setLocalDirectory("testdir");
        mockParentFile.setOwner(mockUser);

        mockChunk = new FileChunks();
        mockChunk.setId(chunkId);
        mockChunk.setOriginFile(mockParentFile);

        // Set up directory structure for test
        String directoryPath = "fileupload/storage/users/user123/testdir/file123/" + chunkId;
        File directory = new File(directoryPath);
        directory.mkdirs();

        // Create a test file in the directory
        expectedFile = new File(directory, "testfile.txt");
        expectedFile.createNewFile();
    }

    @Test
    void getFileChunk_WhenChunkExists_ShouldReturnFile() {
        // Arrange
        when(fileChunkRepository.findById(chunkId)).thenReturn(Optional.of(mockChunk));

        // Act
        File result = fileDownloadService.getFileChunk(chunkId);

        // Assert
        assertNotNull(result);
        verify(fileChunkRepository).findById(chunkId);
    }

    @Test
    void getFileChunk_WhenChunkDoesNotExist_ShouldReturnNull() {
        // Arrange
        when(fileChunkRepository.findById("nonexistent")).thenReturn(Optional.empty());

        // Act
        File result = fileDownloadService.getFileChunk("nonexistent");

        // Assert
        assertNull(result);
        verify(fileChunkRepository).findById("nonexistent");
    }
}