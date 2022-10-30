/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.UserDB;
import java.util.List;
import models.Role;
import models.User;

/**
 *
 * @author hsp28
 */
public class UserService
{
    public User get(String email) throws Exception {
        UserDB UserDB = new UserDB();
        User user = UserDB.get(email);
        return user;
    }
    
    public String getPassword(String email) throws Exception{
        UserDB UserDB = new UserDB();
        String password = UserDB.getPassword(email);
        return password;
    }
    
    public List<User> getAll() throws Exception {
        UserDB UserDB = new UserDB();
        List<User> users = UserDB.getAll();
        return users;
    }
    
    public void insert(String email, String firstname, String lastname, String password, Role role) throws Exception {
        User user = new User(email, firstname, lastname, password, role);
        UserDB UserDB = new UserDB();
        UserDB.insert(user);
    }
    
    public void update(User user) throws Exception {
        UserDB UserDB = new UserDB();
        UserDB.update(user);
    }
    
    public void delete(String email) throws Exception {
        UserDB UserDB = new UserDB();
        UserDB.delete(email);
    }
}
