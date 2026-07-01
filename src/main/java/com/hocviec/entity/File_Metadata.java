package com.hocviec.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "file_metadata")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class File_Metadata {
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(name = "file_name", nullable = false, unique = true)
    private String fileName;

    @Column(name = "original_file_name")
    private String originalFileName;

    @Column(name = "content type")
    private String contentType;

    private Long size;

    @Column(name = "upload_at")
    private LocalDateTime uploadAt;


    
}
