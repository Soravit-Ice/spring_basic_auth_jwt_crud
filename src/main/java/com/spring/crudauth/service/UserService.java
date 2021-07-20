package com.spring.crudauth.service;

import com.spring.crudauth.Dto.UserRequest;
import com.spring.crudauth.Entity.UserEntity;
import com.spring.crudauth.Repository.UsersRepository;
import com.spring.crudauth.exception.UserDupicateException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public UserEntity register(UserRequest userRequest) {
        Optional<UserEntity> result = usersRepository.findByUsername(userRequest.getUsername());
        if (!result.isPresent()) {
            UserEntity userEntity = new UserEntity();
            userEntity.setUsername(userRequest.getUsername());
            userEntity.setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()));
            userEntity.setRole(userRequest.getRole());
            return usersRepository.save(userEntity);
        }
        throw new UserDupicateException(userRequest.getUsername());
    }


    public UserEntity findUserByUserName(String userName){
        Optional<UserEntity> user = usersRepository.findByUsername(userName);
        if(user.isPresent()){
            return user.get();
        }
        return null;
    }
}
