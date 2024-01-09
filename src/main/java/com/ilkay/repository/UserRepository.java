package com.ilkay.repository;

import com.ilkay.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByUsername(String username);

//    Optional<User> findByUsername(String username); // optional kullanırsak


}
