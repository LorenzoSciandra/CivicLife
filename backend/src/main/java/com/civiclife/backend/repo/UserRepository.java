package com.civiclife.backend.repo;
import com.civiclife.backend.model.User;
import org.springframework.data.repository.CrudRepository;
import java.util.List;


public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByAge(int age);
}

