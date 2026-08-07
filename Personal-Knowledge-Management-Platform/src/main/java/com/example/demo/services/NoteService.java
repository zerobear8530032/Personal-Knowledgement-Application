package com.example.demo.services;

import com.example.demo.dtos.*;
import com.example.demo.entities.Note;
import com.example.demo.entities.User;
import com.example.demo.exceptions.NoteNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    public List<NoteResponse> getAllUserNotes(Long id, Pageable pageable){
        List<NoteResponse> noteResponses= noteRepository.findByUserId(id,pageable).stream().map((p)->p.toDTO()).toList();
        return noteResponses;
    }

    public List<NoteNameResponse> getAllUserNotesNames(Long id, Pageable pageable){
        List<NoteNameResponse> noteResponses= noteRepository.findByUserId(id,pageable).stream().map((p)->new NoteNameResponse(p.getId(),p.getTitle())).toList();
        return noteResponses;
    }

    public NoteService(){}
    @Transactional
    public NoteResponse createNote(CreateNoteRequest newNote, Long id){
        User user= userRepository.findById(id).orElseThrow(()-> new UserNotFoundException("User Id "+id+" does not exists in databae"));
        Note note= newNote.toEntity();
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setUser(user);
        Note savedNote=noteRepository.save(note);
        return NoteResponse.toDTO(savedNote);
    }
    @Transactional(readOnly = true)
    public NoteResponse getNote(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        return NoteResponse.toDTO(note);
    }

    @Transactional(readOnly = true)
    public List<NoteResponse> getAllNotes(PageRequest pageRequest){
        NoteResponse mapper= new NoteResponse();
        List<NoteResponse> notes= noteRepository.findAll(pageRequest).stream().map(note -> mapper.toDTO(note)).toList();
        return  notes;
    }

    @Transactional(readOnly = true)
    public List<NoteNameResponse> getAllNotesName(PageRequest pageRequest){
        List<NoteNameResponse> notes= noteRepository.findAll(pageRequest).stream().map(note -> new NoteNameResponse(note.getId(),note.getTitle())).toList();
        return  notes;
    }

    @Transactional
    public NoteResponse updateNote(Long id, UpdateNoteRequest noteRequest){
        Note note= noteRepository.findById(id).orElseThrow(()->new NoteNotFoundException("Note ID : "+id+" does not exist in Database" ));
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setUpdatedAt(LocalDateTime.now());
        noteRepository.save(note);
        return note.toDTO();
    }


    @Transactional
    public void deleteNote(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        noteRepository.delete(note);
    }
}
