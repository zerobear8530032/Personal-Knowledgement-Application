package com.example.demo.repositories;

import com.example.demo.entities.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

    Optional<Attachment> findByIdAndNoteIdAndNoteUserId(Long attachmentId, Long noteId, Long userId);
}
