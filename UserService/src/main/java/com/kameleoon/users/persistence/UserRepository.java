package com.kameleoon.users.persistence;

import com.kameleoon.users.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, String> {
    Optional<User> findFirstByNameAndPassword(String name, String password);
}
