package com.airline.ticketing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.airline.ticketing.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

  User findByEmail(String email);

}
