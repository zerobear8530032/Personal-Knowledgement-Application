package com.example.demo.controllers;
import com.example.demo.dtos.*;
import com.example.demo.entities.User;
import com.example.demo.enums.NotesEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.NoteService;

import com.example.demo.services.UserService;
import lombok.extern.slf4j.Slf4j;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping(path = "/notes")

public class NoteController {
    @Value("${notes.max-pages.size}")
    private int MAXPageSize;


    private  final  NoteService noteService;
    private  final  UserService userService;

    @Autowired
    public  NoteController(NoteService noteService, UserService userService) {

        this.noteService = noteService;
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<NoteResponse>>> getAllNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
        Sort sort=Sort.by(direction, sortBy.getValue());
        PageRequest pageRequest = PageRequest.of(page, size,sort);
        Page<NoteResponse> notes = noteService.getAllNotes(pageRequest);
        return new ResponseEntity<>(ApiResponse.success("fetching all notes successfully",notes), HttpStatus.OK);
    }
    @GetMapping("/names")
    public ResponseEntity<ApiResponse<Page<NoteNameResponse>>> getAllNotesNames(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
        Sort sort=Sort.by(direction, sortBy.getValue());
        PageRequest pageRequest = PageRequest.of(page, size,sort);
        Page<NoteNameResponse> notes = noteService.getAllNotesName(pageRequest);
        return new ResponseEntity<>(ApiResponse.success("fetching all notes successfully",notes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponse>> getNote(@PathVariable(name = "id") Long id){
        NoteResponse note= noteService.getNote(id);
        return new ResponseEntity<>(ApiResponse.success("Fetch note by Id successfully",note), HttpStatus.OK);
    }
    @GetMapping("users/{id}")
    public ResponseEntity<ApiResponse<Page<NoteResponse>>> getUserNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction,@PathVariable(name = "id") Long id){
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
       PageRequest pageRequest= PageRequest.of(page,size,direction,sortBy.getValue());
        Page<NoteResponse> noteResponses = noteService.getAllUserNotes(id,pageRequest);
        return new ResponseEntity<>(ApiResponse.success("Fetch note by Id successfully",noteResponses), HttpStatus.OK);
    }

    @GetMapping("users/{id}/names")
    public ResponseEntity<ApiResponse<Page<NoteNameResponse>>> getUserNotesNames(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction,@PathVariable(name = "id") Long id){
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
       PageRequest pageRequest= PageRequest.of(page,size,direction,sortBy.getValue());
        Page<NoteNameResponse> noteResponses = noteService.getAllUserNotesNames(id,pageRequest);
        return new ResponseEntity<>(ApiResponse.success("Fetch note by Id successfully",noteResponses), HttpStatus.OK);
    }

    @PostMapping("/users/{id}")
    public  ResponseEntity<ApiResponse<NoteResponse>> createNote(@RequestBody CreateNoteRequest createNote, @PathVariable(name = "id") Long id){
        NoteResponse note=noteService.createNote(createNote,id);
        return new ResponseEntity<>(ApiResponse.success("New Node added successfully",note), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ApiResponse<NoteResponse>> updateNote(@RequestBody UpdateNoteRequest updateNoteRequest , @PathVariable(name = "id") Long id){
        NoteResponse note=noteService.updateNote(id,updateNoteRequest);
        return new ResponseEntity<>(ApiResponse.success("Note update successfully",note), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteResponse>>  deleteNote(@PathVariable(name="id") Long id ){
        noteService.deleteNote(id);
        return new ResponseEntity<>(ApiResponse.success("deleted note successfully",null), HttpStatus.OK);
    }
}