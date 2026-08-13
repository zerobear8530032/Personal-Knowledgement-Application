package com.example.demo.services;

import com.example.demo.dtos.FolderRequest;
import com.example.demo.dtos.FolderResponse;
import com.example.demo.entities.Folder;
import com.example.demo.entities.User;
import com.example.demo.exceptions.FolderDoesNotExistException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.FolderRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class FolderService {
    FolderRepository folderRepository;
    UserRepository userRepository;

    @Autowired
    public FolderService(FolderRepository folderRepository, UserRepository userRepository) {
        this.folderRepository = folderRepository;
        this.userRepository = userRepository;
    }


    public FolderResponse createFolder(FolderRequest folderRequest, Long userId){
        User user = userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("User Id "+userId+" Not Found"));
        Folder folder= new Folder();
        folder.setName(folderRequest.getFolderName());
        folder.setCreateAt(LocalDateTime.now());
        folder.setDeleted(false);
        folder.setUser(user);
        Folder newFolder=folderRepository.save(folder);
        return newFolder.toDTO();
    }
    public void deleteFolder(Long id,Long userId){
        Folder folder = folderRepository.findByIdAndUserId(id,userId).orElseThrow(()->new FolderDoesNotExistException("Folder does not exists "));
        folder.setDeleted(true);
        folderRepository.save(folder);
    }

    public List<FolderResponse> getUserFolders(Long userId){
        List<Folder> folders= folderRepository.findByUserIdAndIsDeletedFalse(userId);
        return folders.stream().map(folder -> folder.toDTO()).toList();
    }

    public FolderResponse renameFolder(Long folderId, FolderRequest folderRequest,Long userId) {
        Folder folder = folderRepository.findByIdAndUserIdAndIsDeletedFalse(folderId,userId).orElseThrow(()->new FolderDoesNotExistException("Folder does not exists "));
        folder.setName(folderRequest.getFolderName());
        Folder saveFolder =folderRepository.save(folder);
        return saveFolder.toDTO();
    }
}
