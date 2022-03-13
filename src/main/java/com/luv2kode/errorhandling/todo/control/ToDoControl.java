package com.luv2kode.errorhandling.todo.control;

import com.luv2kode.errorhandling.exception.BusinessException;
import com.luv2kode.errorhandling.todo.dto.CreateToDoRequestDto;
import com.luv2kode.errorhandling.todo.dto.CreateToDoResponseDto;
import com.luv2kode.errorhandling.todo.dto.ToDoDetailsResponseDto;
import com.luv2kode.errorhandling.todo.dto.mapper.ToDoMapper;
import com.luv2kode.errorhandling.todo.persistence.ToDo;
import com.luv2kode.errorhandling.exception.BusinessExceptionReason;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ToDoControl {

    private final static Set<ToDo> todos = new HashSet<>(); // our in-memory db
    private final ToDoMapper toDoMapper;

    public CreateToDoResponseDto create(final CreateToDoRequestDto createToDoRequestDto) {
        final ToDo newToDo = this.toDoMapper.createToDoRequestDtoToToDo(createToDoRequestDto);
        todos.add(newToDo);
        return CreateToDoResponseDto.builder().extRef(newToDo.getExtRef()).build();
    }

    public ToDoDetailsResponseDto findByExtRef(final String extRef) {
        final ToDoDetailsResponseDto todoDetails;
        final Optional<ToDo> existingToDo = todos.stream().filter(todo -> extRef.equals(todo.getExtRef())).findFirst();
        if (existingToDo.isPresent()) {
            todoDetails = this.toDoMapper.toDoToToDoDetailsResponseDto(existingToDo.get());
        } else {
            throw new BusinessException(BusinessExceptionReason.TODO_NOT_FOUND_BY_EXT_REF);
        }
        return todoDetails;
    }

    public void deleteByExtRef(final String extRef) {
        if (!todos.removeIf(todo -> extRef.equals(todo.getExtRef()))) {
            throw new BusinessException(BusinessExceptionReason.TODO_NOT_FOUND_BY_EXT_REF);
        }
    }

    public List<ToDoDetailsResponseDto> findAll() {
        return todos.stream().map(this.toDoMapper::toDoToToDoDetailsResponseDto).collect(Collectors.toList());
    }

}
