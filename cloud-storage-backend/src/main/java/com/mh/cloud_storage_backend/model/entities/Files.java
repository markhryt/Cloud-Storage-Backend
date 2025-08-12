package com.mh.cloud_storage_backend.model.entities;

import com.mh.cloud_storage_backend.model.entities.requests.FileUploadDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "files")
public class Files {

    @Id
    @Column(name = "file_id", columnDefinition = "char(36)")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Users owner;

    @Column(name = "file_name", nullable = false, length = 255)
    private String name;

    @Column(name = "file_size", nullable = false)
    private Long size;

    @Column(name = "number_of_chunks", nullable = false)
    private Long numberOfChunks;

    @Column(name = "downloaded_count", nullable = false, columnDefinition = "int default 0")
    private Integer downloadedCount = 0;

    @ElementCollection
    @CollectionTable(name = "file_tags", joinColumns = @JoinColumn(name = "file_id"))
    @Column(name = "tag", length = 48)
    private List<String> tags;

    @Column(name = "created_at", columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;

    @OneToMany(
        mappedBy = "originFile",
        cascade = CascadeType.ALL,
        orphanRemoval = true,
        fetch = FetchType.LAZY
    )
    private List<FileChunks> chunks;

    @Column(name = "local_directory", length = 255, columnDefinition = "varchar(255) default ''")
    private String localDirectory;

    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        this.createdAt = LocalDateTime.now();
    }

    public Files(FileUploadDTO fileUploadDTO, Users owner) {
        this.owner = owner;
        this.name = fileUploadDTO.getName();
        this.size = fileUploadDTO.getSize(); // Add this line
        this.numberOfChunks = fileUploadDTO.getNumberOfChunks();
        this.tags = fileUploadDTO.getTags();
        this.createdAt = fileUploadDTO.getCreatedAt();
        this.localDirectory = fileUploadDTO.getLocalDirectory();
    }

}
