package com.mh.cloud_storage_backend.service;

import com.mh.cloud_storage_backend.model.entities.Files;
import com.mh.cloud_storage_backend.model.entities.Users;
import com.mh.cloud_storage_backend.model.entities.requests.FileUploadDTO;
import com.mh.cloud_storage_backend.model.repository.FilesRepo;
import com.mh.cloud_storage_backend.model.util.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @Mock
    private FilesRepo fileRepo;

    @Mock
    private UsersService usersService;

    @Mock
    private JWTUtil jwtUtil;

    @Mock
    private FileChunkService fileChunkService;

    @Mock
    private HttpServletRequest request;

    @InjectMocks
    private FileService fileService;

    private FileUploadDTO fileUploadDTO;
    private Users user;
    private String userEmail;

    @BeforeEach
    void setUp() {
        userEmail = "test@example.com";

        user = new Users();
        user.setEmail(userEmail);

        fileUploadDTO = new FileUploadDTO();
        fileUploadDTO.setName("testfile.txt");
        fileUploadDTO.setLocalDirectory("/test");
        fileUploadDTO.setTags(Arrays.asList("tag1", "tag2"));
        fileUploadDTO.setSize(1024L);
        fileUploadDTO.setNumberOfChunks(10L);
    }

    @Test
    void addFile_ShouldSaveFileToRepository() {
        // Arrange
        when(jwtUtil.extractUsernameByRequest(request)).thenReturn(userEmail);
        when(usersService.getUserByEmail(userEmail)).thenReturn(user);

        // Act
        fileService.addFile(fileUploadDTO, request);

        // Assert
        ArgumentCaptor<Files> filesCaptor = ArgumentCaptor.forClass(Files.class);
        verify(fileRepo).save(filesCaptor.capture());

        Files savedFile = filesCaptor.getValue();
        assertEquals(user, savedFile.getOwner());
    }
}