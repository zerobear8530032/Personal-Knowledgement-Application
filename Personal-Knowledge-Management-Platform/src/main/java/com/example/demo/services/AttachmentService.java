package com.example.demo.services;

import com.example.demo.dtos.AttachmentResponse;
import com.example.demo.dtos.NoteResponse;
import com.example.demo.entities.Attachment;
import com.example.demo.entities.Note;
import com.example.demo.entities.User;
import com.example.demo.exceptions.FileCannotCreateException;
import com.example.demo.exceptions.NoteNotFoundException;
import com.example.demo.exceptions.UserNotFoundException;
import com.example.demo.repositories.AttachmentRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class AttachmentService {

    AttachmentRepository attachmentRepository;
    UserRepository userRepository;

    @Autowired
    public  AttachmentService(AttachmentRepository attachmentRepository,UserRepository userRepository){
        this.attachmentRepository=attachmentRepository;
        this.userRepository=userRepository;
    }

    public AttachmentResponse getAttachment(Long id) throws FileNotFoundException {
        Attachment attachment= attachmentRepository.findById(id).orElseThrow(()-> new FileNotFoundException(id+ "file does not exists"));
        return attachment.toDTO();
    }
    public Page<AttachmentResponse> getAllAttachment(Long id, PageRequest pageRequest) {
        Page<AttachmentResponse> attachments= attachmentRepository.findAll(pageRequest).map(note -> note.toDTO());
        return attachments;
    }

    public AttachmentResponse uploadFile(MultipartFile file, Long userId, Long notesId) throws IOException {
        Attachment attachment= new Attachment();
        String uuid= UUID.randomUUID().toString();
        attachment.setFileName(uuid);
        attachment.setFileType(file.getContentType());
        attachment.setSize(file.getSize());
        attachment.setOriginalName(file.getName());
        User user= userRepository.findById(userId).orElseThrow(()->new UserNotFoundException("user id "+userId+" does not exists"));
        Note note=user.getUserNotes().stream().filter((n) -> n.getId().equals(notesId)).findFirst().orElseThrow(()->new NoteNotFoundException("Note Id "+notesId+" does not exists"));
        attachment.setNote(note);
        String filePath = upload(file,userId);
        if(filePath!=null || !filePath.isBlank()){
            attachment.setUrl(filePath);
            Attachment savedAttchment= attachment= attachmentRepository.save(attachment);
            return savedAttchment.toDTO();
        }
        throw new FileCannotCreateException("File cannot be created for some reason");
    }
    public String upload(MultipartFile file, Long userId) throws IOException {

        if (!isSafeFileName(file.getOriginalFilename())) {
            throw new SecurityException("File name is invalid");
        }

        Path projectPath = Path.of(System.getProperty("user.dir"))
                .toAbsolutePath()
                .normalize();

        Path userPath = projectPath.resolve("uploads").resolve(userId.toString()).normalize();
        String fileName = UUID.randomUUID().toString();
        String extension = getExtension(file.getOriginalFilename());
        Path filePath = userPath.resolve(fileName + "." + extension).normalize();

        System.out.println("Root: " + projectPath);
        System.out.println("User: " + userPath);
        System.out.println("File: " + filePath);
        Files.createDirectories(userPath);

        file.transferTo(filePath);

        return filePath.toString();
    }

    public boolean isSafeFileName(String name) throws SecurityException{
        if(name==null || name.isBlank()){
            throw new IllegalArgumentException("Invalid filename");
        }
        Path fileName = Path.of(name);
        if(fileName.getNameCount()!=1) {
            return false;
        }
        return true;
    }

    public String getExtension(String fileName){
        int lastDot = fileName.lastIndexOf(".");
        if(lastDot==-1 || lastDot==fileName.length()-1){
            throw new IllegalArgumentException(" file name does not have valid extension");
        }
        return fileName.substring(lastDot+1,fileName.length()).toLowerCase();
    }

    public Resource getAttachmentFile(Long userId, Long noteId, Long attachmentId) throws MalformedURLException {
        Attachment attachment= attachmentRepository.findByIdAndNoteIdAndNoteUserId(attachmentId,noteId,userId).orElseThrow(()-> new RuntimeException("File not found"));
        Path path= Path.of(attachment.getUrl());
        Resource resource= new UrlResource(path.toUri());
        if(!resource.exists() || !resource.isReadable()){
            throw new RuntimeException("File not found");
        }
        return resource;
    }

    public void deleteFile(Long attachmentId, Long noteId, Long userId) {
        Attachment attachment= attachmentRepository.findByIdAndNoteIdAndNoteUserId(attachmentId,noteId,userId).orElseThrow(()-> new RuntimeException("File not found"));
        attachmentRepository.deleteById(attachmentId);

    }
}
