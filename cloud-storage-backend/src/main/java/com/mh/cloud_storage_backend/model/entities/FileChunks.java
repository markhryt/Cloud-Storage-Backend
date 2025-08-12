package com.mh.cloud_storage_backend.model.entities;

import com.mh.cloud_storage_backend.model.entities.dto.FileChunkDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;


@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "file_chunks")
public class FileChunks {
    @Id
    @Column(name = "chunk_id", columnDefinition = "char(36)")
    private String id;

    @Column(name = "chunk_number")
    private Integer chunkNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "originFile", nullable = false)
    private Files originFile;


    @PrePersist
    private void prePersist() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
    }

    public FileChunks(FileChunkDTO fileChunkDTO, Files originFile) {
        this.id = UUID.randomUUID().toString();
        this.originFile = originFile;
        this.chunkNumber = fileChunkDTO.getOrderNumber();
    }


}
