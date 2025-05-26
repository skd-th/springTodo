package com.example.springtodo.service;

import com.example.springtodo.dto.TodoDTO;

import java.util.List;

public interface TodoService {

    void register(TodoDTO todoDTO);

    List<TodoDTO> getAll();

    TodoDTO getOne(Long tno);

    void remove(Long tno);          // p.340

    void modify(TodoDTO todoDTO);
}
