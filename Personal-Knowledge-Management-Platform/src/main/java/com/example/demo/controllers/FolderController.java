package com.example.demo.controllers;

import com.example.demo.Utility.Utility;
import com.example.demo.dtos.FolderRequest;
import com.example.demo.dtos.FolderResponse;
import com.example.demo.response.ApiResponse;
import com.example.demo.services.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/folders")
public class FolderController {

    private final FolderService folderService;
    private  final Utility utility;

    @Autowired
    public FolderController(FolderService folderService, Utility utility) {
        this.folderService = folderService;
        this.utility = utility;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<FolderResponse>> createFolder( @RequestBody FolderRequest folderRequest){
        Long userId= utility.getCurrentLoggedInUserId();
        FolderResponse folderResponse= folderService.createFolder(folderRequest,userId);
        return new ResponseEntity<>(ApiResponse.success("folder "+folderResponse.getFolderName()+" created ",folderResponse), HttpStatus.CREATED);
    }



    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getAllFolders(){
        List<FolderResponse> folderResponse= folderService.getAllFolders();
        return new ResponseEntity<>(ApiResponse.success("fetch all folder names",folderResponse), HttpStatus.OK);
    }
    @GetMapping("/admin/notDeleted")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getAllNonDeletedFolders(){
        List<FolderResponse> folderResponse= folderService.getAllNotDeletedFolders();
        return new ResponseEntity<>(ApiResponse.success("fetch all folder names",folderResponse), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<FolderResponse>>> getAllUserFolders(){
        Long userId= utility.getCurrentLoggedInUserId();
        List<FolderResponse> folderResponse= folderService.getUserFolders(userId);
        return new ResponseEntity<>(ApiResponse.success("fetch all folder names",folderResponse), HttpStatus.OK);
    }

    @DeleteMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> deleteFolder(@PathVariable(name = "folderId") Long folderId){
        Long userId= utility.getCurrentLoggedInUserId();
        folderService.deleteFolder(folderId,userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @DeleteMapping("/admin/{folderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<FolderResponse>> deleteAnyFolder(@PathVariable(name = "folderId") Long folderId){
        folderService.deleteAnyFolder(folderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{folderId}")
    public ResponseEntity<ApiResponse<FolderResponse>> renameFolder(@PathVariable(name = "folderId") Long folderId,@RequestBody FolderRequest folderRequest){
        Long userId= utility.getCurrentLoggedInUserId();
        FolderResponse  folderResponse=folderService.renameFolder(folderId,folderRequest,userId);
        return new ResponseEntity<>(ApiResponse.success("Folder Updated successfully",folderResponse), HttpStatus.OK);
    }

}
