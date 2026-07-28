package com.example.demo.controllers;
import com.example.demo.dtos.CreateNoteDTO;
import com.example.demo.dtos.NoteDTO;
import com.example.demo.enums.NotesEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.NoteService;

import lombok.extern.slf4j.Slf4j;
import  org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@Slf4j
@RestController
@RequestMapping(path = "/notes")

public class NoteController {
    @Value("${notes.max-pages.size}")
    private int MAXPageSize;


    private  final  NoteService noteService;
//    private final Logger logger= LoggerFactory.getLogger(NoteController.class);
//    private final AIAgentService aiAgentService;

    @Autowired
    public  NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

//    @Autowired
//    public  NoteController(NoteService noteService,AIAgentService aiAgentService) {
//        this.noteService = noteService;
//        this.aiAgentService=aiAgentService;
//    }
//    @Transactional
    @GetMapping
    public ResponseEntity<ApiResponse<List<NoteDTO>>> getAllNotes(@RequestParam(required = false,name="size", defaultValue = "5")int size,@RequestParam(name="page",required = false , defaultValue = "0") int page ,@RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum  sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction) {
        size= Math.min(MAXPageSize,size);
        Sort sort=Sort.by(direction, sortBy.getValue());
        PageRequest pageRequest = PageRequest.of(page, size,sort);
        List<NoteDTO> notes = noteService.getAllNotes(pageRequest);
        return new ResponseEntity<>(ApiResponse.success("fetching all notes successfully",notes), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteDTO>> getNote(@PathVariable(name = "id") Long id){
        NoteDTO note= noteService.getNote(id);
        return new ResponseEntity<>(ApiResponse.success("Fetch not by Id successfully",note), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public  ResponseEntity<ApiResponse<NoteDTO>> createNote(@RequestBody CreateNoteDTO createNote, @PathVariable(name = "id") Long id){
        NoteDTO note=noteService.createNote(createNote,id);
        return new ResponseEntity<>(ApiResponse.success("New Node added successfully",note), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<ApiResponse<NoteDTO>> updateNote(@RequestBody NoteDTO updatedNote ,@PathVariable(name = "id") Long id){
        NoteDTO note=noteService.updateNote(id,updatedNote);
        return new ResponseEntity<>(ApiResponse.success("Note update successfully",note), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<NoteDTO>>  deleteNote(@PathVariable(name="id") Long id ){
        noteService.deleteNote(id);
        return new ResponseEntity<>(ApiResponse.success("deleted note successfully",null), HttpStatus.OK);
    }

//    @GetMapping("/summary/{text}")
//    public  ResponseEntity<ApiResponse<String>> getSummary(@PathVariable String text){
//        String summary=aiAgentService.getSummary(text);
//        ApiResponse<String> response= new ApiResponse(true, "Summary generated ",summary,LocalDateTime.now());
//        return new ResponseEntity<ApiResponse<String>>(response, HttpStatus.OK);
//    }

}