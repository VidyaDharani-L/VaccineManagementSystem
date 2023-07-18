package com.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.model.User;

public class UserDaoImpl implements UserDao {
    private static List<User> users = new ArrayList<>();

    static {
        User[] endusers = {
            new User("Ram", 13, 123456789098L, LocalDate.parse("2021-01-01"), null),
            new User("Sam", 25, 123456789097L, LocalDate.parse("2021-02-15"), LocalDate.parse("2021-07-01")),
            new User("Doremon", 33, 123456789096L, null, null)
        };
        users.addAll(Arrays.asList(endusers));
    }
	
    @Override
	public List<User> getUser() {
        return users;
    }
	
    @Override
    public void addUser(User user) {
        users.add(user);
    }


}
