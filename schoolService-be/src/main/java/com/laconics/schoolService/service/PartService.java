package com.laconics.schoolService.service;

import com.laconics.schoolService.entity.Part;
import com.laconics.schoolService.exception.CustomExceptions;

import java.util.List;

public interface PartService {

    void save(Part part) throws CustomExceptions.ItemExistsException;

    void update(Part part) throws CustomExceptions.ItemNotFoundException;

    void deleteById(Long id) throws CustomExceptions.ItemNotFoundException;

    Part getById(Long id) throws CustomExceptions.ItemNotFoundException;

    List<Part> getAllParts();
}
