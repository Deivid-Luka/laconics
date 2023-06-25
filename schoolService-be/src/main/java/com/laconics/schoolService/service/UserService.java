package com.laconics.schoolService.service;

import com.laconics.schoolService.DTO.Authentification.AuthenticationDTO;
import com.laconics.schoolService.entity.User;
import com.laconics.schoolService.exception.CustomExceptions;

import javax.management.relation.RoleNotFoundException;

public interface UserService {
    String authenticate(AuthenticationDTO authenticationDto);

    User save(User user) throws RoleNotFoundException, CustomExceptions.UserExistsException;

}
