package com.example.demo.repositories;

import com.example.demo.entities.Note;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note,Long> {
    Page<Note> findByUserId(Long id, Pageable pageable);
    Page<Note> findByUserIdAndIsDeleted(Long id,boolean deleted, Pageable pageable);

    Optional<Note> findByUserIdAndIdAndIsDeleted(Long userId, Long noteId, boolean b);
}
