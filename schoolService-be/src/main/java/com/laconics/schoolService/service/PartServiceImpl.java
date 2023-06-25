package com.laconics.schoolService.service;

import com.laconics.schoolService.entity.Part;
import com.laconics.schoolService.exception.CustomExceptions;
import com.laconics.schoolService.repository.PartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartServiceImpl implements PartService {

    private final PartRepository partRepository;

    public PartServiceImpl(PartRepository partRepository) {
        this.partRepository = partRepository;
    }

    @Override
    public void save(Part part) throws CustomExceptions.ItemExistsException {
        if (partRepository.findByName(part.getName()) != null) {
            throw new CustomExceptions.ItemExistsException("Part already exists");
        }
        partRepository.save(part);
    }

    @Override
    public void update(Part updatedPart) {
        Part existingPart = partRepository.findByName(updatedPart.getName());

        if (existingPart == null) {
            throw new CustomExceptions.ItemNotFoundException("Part not found");
        }

        updatedPart.setId(existingPart.getId());

        existingPart = updatedPart;

        partRepository.save(existingPart);
    }

    @Override
    public void deleteById(Long id) throws CustomExceptions.ItemNotFoundException {
        if (!partRepository.existsById(id)) {
            throw new CustomExceptions.ItemNotFoundException("Part not found");
        }
        partRepository.deleteById(id);
    }


    @Override
    public Part getById(Long id) throws CustomExceptions.ItemNotFoundException {
        return partRepository.findById(id)
                .orElseThrow(() -> new CustomExceptions.ItemNotFoundException("Part not found"));
    }


    @Override
    public List<Part> getAllParts() {
        return partRepository.findAll();
    }


}
