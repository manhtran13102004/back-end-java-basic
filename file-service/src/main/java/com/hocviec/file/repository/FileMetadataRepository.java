package com.hocviec.file.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hocviec.file.entity.File_Metadata;

public interface FileMetadataRepository extends JpaRepository<File_Metadata, Long> {
    // Các phương thức truy vấn tùy chỉnh có thể được định nghĩa ở đây nếu cần
    Optional<File_Metadata> findByFileName(String fileName);
    
}
