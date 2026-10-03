package com.example.demo.controllers;
import com.example.demo.Utility.Utility;
import com.example.demo.dtos.*;
import com.example.demo.enums.NotesEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.NoteService;

import lombok.extern.slf4j.Slf4j;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/notes")
public class NoteController {

    private  final  NoteService noteService;
    private final Utility utility;
    @Autowired
    public  NoteController(NoteService noteService,Utility utility) {

        this.noteService = noteService;
        this.utility = utility;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<NoteResponse>>> getAllNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteResponse> notes = noteService.getAllNotes(pageRequest);
        PageResponse<NoteResponse> response = new PageResponse<>(
                notes.getContent(),
                notes.getNumber(),
                notes.getSize(),
                notes.getTotalElements(),
                notes.getTotalPages(),
                notes.isFirst(),
                notes.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));
    }
    @GetMapping("/names")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<NoteNameResponse>>> getAllNotesNames(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteNameResponse> notes = noteService.getAllNotesName(pageRequest);
        PageResponse<NoteNameResponse> response = new PageResponse<>(
                notes.getContent(),
                notes.getNumber(),
                notes.getSize(),
                notes.getTotalElements(),
                notes.getTotalPages(),
                notes.isFirst(),
                notes.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));
    }
    @GetMapping("/notDeleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<NoteResponse>>> getAllNonDeletedNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteResponse> notes = noteService.getAllNotDeletedNotes(pageRequest);
        PageResponse<NoteResponse> response = new PageResponse<>(
                notes.getContent(),
                notes.getNumber(),
                notes.getSize(),
                notes.getTotalElements(),
                notes.getTotalPages(),
                notes.isFirst(),
                notes.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));
    }
    @GetMapping("/names/notDeleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<PageResponse<NoteNameResponse>>> getAllNonDeletedNotesNames(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteNameResponse> notes = noteService.getAllNotDeletedNotesName(pageRequest);
        PageResponse<NoteNameResponse> response = new PageResponse<>(
                notes.getContent(),
                notes.getNumber(),
                notes.getSize(),
                notes.getTotalElements(),
                notes.getTotalPages(),
                notes.isFirst(),
                notes.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));
    }

    @GetMapping("/{noteId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NoteResponse>> getNote(@PathVariable(name = "noteId") Long noteId){
        NoteResponse note= noteService.getNote(noteId);
        return new ResponseEntity<>(ApiResponse.success("Fetch note by Id successfully",note), HttpStatus.OK);
    }

    @GetMapping("/user/{noteId}")
    public ResponseEntity<ApiResponse<NoteResponse>> getUserNote(@PathVariable(name = "noteId") Long noteId){
        Long userId = utility.getCurrentLoggedInUserId();
        NoteResponse note= noteService.getUserNote(noteId,userId);
        return new ResponseEntity<>(ApiResponse.success("Fetch note by Id successfully",note), HttpStatus.OK);
    }


    @GetMapping("/user")
    public ResponseEntity<ApiResponse<PageResponse<NoteResponse>>> getUserNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction){
        Long id = utility.getCurrentLoggedInUserId();
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteResponse> noteResponses = noteService.getAllUserNotes(id,pageRequest);
        PageResponse<NoteResponse> response = new PageResponse<>(
                noteResponses.getContent(),
                noteResponses.getNumber(),
                noteResponses.getSize(),
                noteResponses.getTotalElements(),
                noteResponses.getTotalPages(),
                noteResponses.isFirst(),
                noteResponses.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));
    }

    @GetMapping("users/names")
    public ResponseEntity<ApiResponse<PageResponse<NoteNameResponse>>> getUserNotesNames(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction){
        Long id = utility.getCurrentLoggedInUserId();
        PageRequest pageRequest = utility.getPagination(size,page,sortBy,direction);
        Page<NoteNameResponse> noteResponses = noteService.getAllUserNotesNames(id,pageRequest);
        PageResponse<NoteNameResponse> response = new PageResponse<>(
                noteResponses.getContent(),
                noteResponses.getNumber(),
                noteResponses.getSize(),
                noteResponses.getTotalElements(),
                noteResponses.getTotalPages(),
                noteResponses.isFirst(),
                noteResponses.isLast()
        );
        return ResponseEntity.ok(ApiResponse.success("fetching all notes successfully",response));

    }

    @PostMapping
    public  ResponseEntity<ApiResponse<NoteResponse>> createNote(@RequestBody CreateNoteRequest createNote){
        Long id = utility.getCurrentLoggedInUserId();
        NoteResponse note=noteService.createNote(createNote,id);
        return new ResponseEntity<>(ApiResponse.success("New Node added successfully",note), HttpStatus.CREATED);
    }

    @PutMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<ApiResponse<NoteResponse>> updateNoteByAdmin(@RequestBody UpdateNoteRequest updateNoteRequest , @PathVariable(name = "id") Long id){
        NoteResponse note=noteService.updateNoteAdmin(id,updateNoteRequest);
        return new ResponseEntity<>(ApiResponse.success("Note update successfully",note), HttpStatus.OK);
    }

    @DeleteMapping("/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<NoteResponse>>  deleteNoteByAdmin(@PathVariable(name="id") Long id ){
        noteService.deleteNoteAdmin(id);
        return new ResponseEntity<>(ApiResponse.success("deleted note successfully",null), HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public  ResponseEntity<ApiResponse<NoteResponse>> updateNoteByUser(@RequestBody UpdateNoteRequest updateNoteRequest , @PathVariable(name = "id") Long id){
        NoteResponse note=noteService.updateNoteUser(id,updateNoteRequest);
        return new ResponseEntity<>(ApiResponse.success("Note update successfully",note), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponse>>  deleteNoteByUser(@PathVariable(name="id") Long id ){
        noteService.deleteNoteUser(id);
        return new ResponseEntity<>(ApiResponse.success("deleted note successfully",null), HttpStatus.OK);
    }
}