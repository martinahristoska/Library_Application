package veb.seminarska.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import veb.seminarska.model.User;

import java.util.Optional;


/*public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsernameAndPassword(String username, String password);

    Optional<User> findByUsername(String username);*/
@Repository
public interface UserRepository extends CrudRepository<User, Long> {
        Optional<User> findByUsernameAndPassword(String username, String password);

        @Query("SELECT u FROM User u WHERE u.username = :username")
        public User getUserByUsername(@Param("username") String username);
    }
