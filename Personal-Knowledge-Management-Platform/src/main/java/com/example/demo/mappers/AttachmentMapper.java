package com.example.demo.mappers;

import com.example.demo.dtos.AttachmentResponse;
import com.example.demo.entities.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AttachmentMapper {
    @Mapping(source ="originalName" ,target = "fileName")
    @Mapping(source = "note.id",target = "noteId")
    public AttachmentResponse attachmentEntityToResponse(Attachment attachment);
}
