package veb.seminarska.service;


import veb.seminarska.model.User;

public interface AuthService {
    User login(String username, String password);
}
