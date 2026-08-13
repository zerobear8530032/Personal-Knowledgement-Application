package com.example.demo.controllers;

import com.example.demo.dtos.FolderRequest;
import com.example.demo.dtos.FolderResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.FolderService;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/folders")
public class FolderController {

    FolderService folderService;

    @Autowired
    public FolderController(FolderService folderService) {
        this.folderService = folderService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FolderResponse>> createFolder(@PathVariable(name = "userId") Long userId, @RequestBody FolderRequest folderRequest){
        FolderResponse folderResponse= folderService.createFolder(folderRequest,userId);
        return new ResponseEntity<>(ApiResponse.success("folder "+folderResponse.getFolderName()+" created ",folderResponse), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getAllFolders(@PathVariable(name = "userId") Long userId){
        List<FolderResponse> folderResponse= folderService.getUserFolders(userId);
        return new ResponseEntity<>(ApiResponse.success("fetch all folder names",folderResponse), HttpStatus.OK);
    }
    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> deleteFolder(@PathVariable(name = "userId") Long userId,@PathVariable(name = "folderId") Long folderId){
        folderService.deleteFolder(folderId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PutMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> renameFolder(@PathVariable(name = "userId") Long userId,@PathVariable(name = "folderId") Long folderId,@RequestBody FolderRequest folderRequest){
        FolderResponse  folderResponse=folderService.renameFolder(folderId,folderRequest,userId);
        return new ResponseEntity<>(ApiResponse.success("Folder Updated successfully",folderResponse), HttpStatus.OK);
    }




}
