package com.luv2kode.errorhandling.todo.dto.mapper;

import com.luv2kode.errorhandling.todo.dto.CreateToDoRequestDto;
import com.luv2kode.errorhandling.todo.dto.ToDoDetailsResponseDto;
import com.luv2kode.errorhandling.todo.persistence.ToDo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.time.LocalDateTime;
import java.util.UUID;

@Mapper(imports = {LocalDateTime.class, UUID.class})
public interface ToDoMapper {

    @Mappings({
            @Mapping(target = "extRef", expression = "java(UUID.randomUUID().toString())"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "created", expression = "java(LocalDateTime.now())")
    })
    ToDo createToDoRequestDtoToToDo(CreateToDoRequestDto createToDoRequestDto);

    @Mappings({
            @Mapping(target = "extRef", source = "extRef"),
            @Mapping(target = "title", source = "title"),
            @Mapping(target = "created", source = "created")
    })
    ToDoDetailsResponseDto toDoToToDoDetailsResponseDto(ToDo toDo);

}
