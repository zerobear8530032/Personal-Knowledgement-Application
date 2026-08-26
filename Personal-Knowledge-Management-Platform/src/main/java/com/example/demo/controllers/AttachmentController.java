package com.example.demo.controllers;

import com.example.demo.dtos.AttachmentResponse;
import com.example.demo.enums.NotesEnum;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.text.html.parser.Entity;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

@RestController
@RequestMapping("/users/{userId}/attachments")
public class AttachmentController {
    @Value("${notes.max-pages.size}")
    private int MAXPageSize;

    AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService)  {
        this.attachmentService = attachmentService;
    }


    @GetMapping
    public ResponseEntity<ApiResponse<Page<AttachmentResponse>>> getALlAttachment(@RequestParam(required = false,name="size", defaultValue = "5")int size, @RequestParam(name="page",required = false , defaultValue = "0") int page , @RequestParam(required = false,name = "sortBy", defaultValue = "ID") NotesEnum sortBy, @RequestParam(required = false,name="direction" ,defaultValue = "ASC") Sort.Direction direction, @PathVariable(name = "userId") Long userId){
        if(size>0){
            size= Math.min(MAXPageSize,size);
        }else{
            size=Math.max(size,5);
        }
        if(page<0){
            page=0;
        }
        PageRequest pageRequest= PageRequest.of(page,size,direction,sortBy.getValue());
        Page<AttachmentResponse> response= attachmentService.getAllAttachment(userId,pageRequest);
        return new ResponseEntity<>(ApiResponse.success("data fetched",response), HttpStatus.OK);
    }

    @PostMapping("notes/{noteId}")
    public ResponseEntity<ApiResponse<AttachmentResponse>> uploadFile(@RequestBody MultipartFile file , @PathVariable(name = "userId") Long userId , @PathVariable(name = "noteId") Long noteId) throws IOException {
        AttachmentResponse response=attachmentService.uploadFile(file,userId,noteId);
        return new ResponseEntity<>(ApiResponse.success("attachment fetch",response),HttpStatus.CREATED);
    }

    @GetMapping("{attachmentId}/notes/{noteId}")
    public  ResponseEntity<Resource> getFile(@PathVariable(name = "userId") Long userId, @PathVariable(name = "attachmentId") Long attachmentId,@PathVariable(name = "noteId") Long noteId) throws IOException {
        Resource resource= attachmentService.getAttachmentFile(userId,noteId,attachmentId);
        MediaType mediaType = MediaTypeFactory
                .getMediaType(resource)
                .orElse(MediaType.APPLICATION_OCTET_STREAM);
        System.out.println("Resource: " + resource);
        System.out.println("Filename: " + resource.getFilename());
        System.out.println("Exists: " + resource.exists());
        System.out.println("Readable: " + resource.isReadable());
        System.out.println("Size: " + resource.contentLength());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);
    }
    @DeleteMapping("{attachmentId}/notes/{noteId}")
    public ResponseEntity<?> deleteFile(@PathVariable(name = "userId") Long userId, @PathVariable(name = "attachmentId") Long attachmentId,@PathVariable(name = "noteId") Long noteId) throws IOException {
        attachmentService.deleteFile(attachmentId,noteId,userId);
        return ResponseEntity.noContent().build();
    }



}
