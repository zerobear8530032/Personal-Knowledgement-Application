package com.example.demo.repositories;

import com.example.demo.entities.Attachment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,Long> {

    Optional<Attachment> findByIdAndNoteIdAndNoteUserId(Long attachmentId, Long noteId, Long userId);

    Page<Attachment> findByIsDeleted(boolean b, PageRequest pageRequest);
    List<Attachment> findByIsDeleted(boolean b);
}
