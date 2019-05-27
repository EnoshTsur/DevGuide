package com.devguide.jfx.model.dao;

import com.devguide.jfx.model.beans.User;
import com.devguide.jfx.model.repositories.UserRepository;
import io.vavr.Function1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDAO {

    @Autowired
    private UserRepository repository;

    /***
     * Creating new User
     */
    public Function1<String, User> createUser = name -> {
        User user = new User(name);
        User afterSave = repository.save(user);
        return afterSave;
    };

    public void createUserMethod(String name) { ;
        repository.save(new User(name));
    }


}