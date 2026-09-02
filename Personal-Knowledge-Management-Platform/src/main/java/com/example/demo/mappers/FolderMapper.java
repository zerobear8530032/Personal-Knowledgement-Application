package com.example.demo.mappers;

import com.example.demo.dtos.FolderRequest;
import com.example.demo.dtos.FolderResponse;
import com.example.demo.entities.Folder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FolderMapper {
    @Mapping(source = "name",target = "folderName")
    @Mapping(source = "user.id",target = "userId")
    public FolderResponse folderEntityToFolderResponse(Folder folder);
}
