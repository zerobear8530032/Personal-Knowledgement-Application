package com.example.demo.services;

import com.example.demo.dtos.CreateNoteDTO;
import com.example.demo.dtos.NoteDTO;
import com.example.demo.entities.Note;
import com.example.demo.entities.User;
import com.example.demo.exceptions.NoteNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import lombok.Data;
import lombok.ToString;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Data
@ToString
public class NoteService {

    private NoteRepository noteRepository;
    private UserRepository userRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository,UserRepository userRepository){
       this.noteRepository=noteRepository;
       this.userRepository=userRepository;
    }

    public NoteService(){}
    @Transactional
    public NoteDTO createNote(CreateNoteDTO newNote,Long id){
        User user= userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Id "+id+" does not exists in databae"));
        Note note= newNote.toEntity(newNote);
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setUser(user);
        Note savedNote=noteRepository.save(note);
        return NoteDTO.toDTO(savedNote);
    }
    @Transactional(readOnly = true)
    public NoteDTO getNote(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        return NoteDTO.toDTO(note);
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> getAllNotes(PageRequest pageRequest){
        NoteDTO mapper= new NoteDTO();
        List<NoteDTO> notes= noteRepository.findAll(pageRequest).stream().map(note -> mapper.toDTO(note)).toList();
        return  notes;
    }

    @Transactional
    public NoteDTO updateNote(Long id,NoteDTO noteDTO){
        Note note= noteRepository.findById(id).orElseThrow(()->new NoteNotFoundException("Note ID : "+id+" does not exists in Database" ));
        note.setTitle(noteDTO.getTitle());
        note.setContent(noteDTO.getContent());
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
        return noteDTO.toDTO(note);
    }


    @Transactional
    public void deleteNote(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        noteRepository.delete(note);
    }
}
