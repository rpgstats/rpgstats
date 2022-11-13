package com.rpgstats.services;

import com.rpgstats.entity.ConstraintType;
import com.rpgstats.exceptions.ItemNotFoundException;
import com.rpgstats.repositories.ConstraintTypeRepository;
import org.springframework.stereotype.Service;

@Service
public class ConstraintTypeService {
    ConstraintTypeRepository constraintTypeRepository;

    public ConstraintTypeService(ConstraintTypeRepository constraintTypeRepository) {
        this.constraintTypeRepository = constraintTypeRepository;
    }

    ConstraintType getById(Integer id) {
        return constraintTypeRepository.findById(id).orElseThrow(()->new ItemNotFoundException(String.format("No constraint type with id - %d",id)));
    }

}
