package veb.seminarska.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import veb.seminarska.model.Role;
import veb.seminarska.model.User;

public interface UserService extends UserDetailsService {

    User register(String username, String password, String repeatPassword, String name, String surname, Role role);
}
