package com.laconics.schoolService.service;

import com.laconics.schoolService.DTO.authentification.AuthenticationDTO;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    String authenticate(AuthenticationDTO authenticationDto);

    void save(User user) throws RoleNotFoundException, CustomExceptions.ItemExistsException;

}
