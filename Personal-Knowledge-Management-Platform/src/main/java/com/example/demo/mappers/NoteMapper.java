package com.example.demo.mappers;

import com.example.demo.dtos.CreateNoteRequest;
import com.example.demo.dtos.NoteNameResponse;
import com.example.demo.dtos.NoteResponse;
import com.example.demo.entities.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",    uses = AttachmentMapper.class
)
public interface NoteMapper {

    public Note createNoteRequestToNoteEntity(CreateNoteRequest createNoteRequest);
    @Mapping(source = "user.id",target = "userId")
    @Mapping(source = "folder.id",target = "folderId")
    public NoteResponse noteEntityToNoteResponse(Note note);

    NoteNameResponse noteEntityToNoteNameResponse(Note note);
}
