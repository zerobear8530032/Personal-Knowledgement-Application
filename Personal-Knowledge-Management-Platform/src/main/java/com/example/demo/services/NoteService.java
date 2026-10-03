package com.example.demo.services;

import com.example.demo.Utility.Utility;
import com.example.demo.dtos.*;
import com.example.demo.entities.Folder;
import com.example.demo.entities.Note;
import com.example.demo.entities.User;
import com.example.demo.exceptions.FolderDoesNotExistException;
import com.example.demo.exceptions.NoteNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.mappers.NoteMapper;
import com.example.demo.repositories.FolderRepository;
import com.example.demo.repositories.NoteRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;


@Service

public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;
    private final FolderRepository folderRepository;
    private final NoteMapper noteMapper;
    private final Utility utility;



    @Autowired
    public NoteService(NoteRepository noteRepository, UserRepository userRepository, FolderRepository folderRepository, NoteMapper noteMapper, Utility utility){
       this.noteRepository=noteRepository;
       this.userRepository=userRepository;
       this.folderRepository=folderRepository;
       this.noteMapper = noteMapper;
        this.utility = utility;
    }


    public Page<NoteResponse> getAllUserNotes(Long id, Pageable pageable){
        Page<NoteResponse> noteResponses= noteRepository.findByUserIdAndIsDeleted(id,false,pageable).map((note)->noteMapper.noteEntityToNoteResponse(note));
        return noteResponses;
    }

    public Page<NoteNameResponse> getAllUserNotesNames(Long id, Pageable pageable){
        Page<NoteNameResponse> noteResponses= noteRepository.findByUserIdAndIsDeleted(id,false,pageable).map((note)->noteMapper.noteEntityToNoteNameResponse(note));
        return noteResponses;
    }


    @Transactional
    public NoteResponse createNote(CreateNoteRequest newNote, Long userId){
        User user= userRepository.findById(userId).orElseThrow(()-> new UserNotFoundException("User Id "+userId+" does not exists in databae"));
        Note note= noteMapper.createNoteRequestToNoteEntity(newNote);
        if(newNote.getFolderId()!=null){
            Folder folder= folderRepository.findByIdAndUserId(newNote.getFolderId(),userId).orElseThrow(()->new FolderDoesNotExistException("Folder does not exists "));
            note.setFolder(folder);
        }
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        note.setUser(user);
        Note savedNote=noteRepository.save(note);
        return noteMapper.noteEntityToNoteResponse(savedNote);
    }
    @Transactional(readOnly = true)
    public NoteResponse getNote(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        return noteMapper.noteEntityToNoteResponse(note);
    }

    @Transactional(readOnly = true)
    public Page<NoteResponse> getAllNotes(PageRequest pageRequest){

        Page<NoteResponse> notes= noteRepository.findAll(pageRequest).map(note -> noteMapper.noteEntityToNoteResponse(note));
        return  notes;
    }

    @Transactional(readOnly = true)
    public Page<NoteNameResponse> getAllNotesName(PageRequest pageRequest){
        Page<NoteNameResponse> notes= noteRepository.findAll(pageRequest).map(note -> noteMapper.noteEntityToNoteNameResponse(note));
        return  notes;
    }

    @Transactional
    public NoteResponse updateNoteAdmin(Long id, UpdateNoteRequest noteRequest){
        Note note= noteRepository.findById(id).orElseThrow(()->new NoteNotFoundException("Note ID : "+id+" does not exist in Database" ));
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setUpdatedAt(LocalDateTime.now());
        Note updatedNote=noteRepository.save(note);
        return noteMapper.noteEntityToNoteResponse(updatedNote);
    }


    @Transactional
    public void deleteNoteAdmin(Long id){
        Note note=noteRepository.findById(id).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        note.setDeleted(true);
        noteRepository.save(note);
    }

    @Transactional
    public NoteResponse updateNoteUser(Long id, UpdateNoteRequest noteRequest){
        Long userId = utility.getCurrentLoggedInUserId();
        Note note= noteRepository.findByIdAndUserIdAndIsDeleted(id,userId,false).orElseThrow(()->new NoteNotFoundException("Note ID : "+id+" does not exist in Database" ));
        note.setTitle(noteRequest.getTitle());
        note.setContent(noteRequest.getContent());
        note.setUpdatedAt(LocalDateTime.now());
        Note updatedNote=noteRepository.save(note);
        return noteMapper.noteEntityToNoteResponse(updatedNote);
    }


    @Transactional
    public void deleteNoteUser(Long id){
        Long userId = utility.getCurrentLoggedInUserId();
        Note note=noteRepository.findByIdAndUserIdAndIsDeleted(id,userId,false).orElseThrow(()-> new NoteNotFoundException(" Note ID "+id+" Not Found"));
        note.setDeleted(true);
        noteRepository.save(note);
    }

    public NoteResponse getUserNote(Long noteId, Long userId) {
        Note note = noteRepository.findByUserIdAndIdAndIsDeleted(userId,noteId,false).orElseThrow(()-> new NoteNotFoundException(" Note ID "+noteId+" Not Found"));
        return noteMapper.noteEntityToNoteResponse(note);
    }

    public Page<NoteResponse> getAllNotDeletedNotes(PageRequest pageRequest) {
        Page<NoteResponse> noteResponses= noteRepository.findByIsDeleted(false,pageRequest).map((note)->noteMapper.noteEntityToNoteResponse(note));
        return noteResponses;
    }

    public Page<NoteNameResponse> getAllNotDeletedNotesName(PageRequest pageRequest) {
        Page<NoteNameResponse> notes= noteRepository.findByIsDeleted(false,pageRequest).map(note -> noteMapper.noteEntityToNoteNameResponse(note));
        return  notes;
    }
}
