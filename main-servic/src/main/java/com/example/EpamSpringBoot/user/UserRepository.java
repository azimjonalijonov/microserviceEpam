package com.example.EpamSpringBoot.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface UserRepository extends JpaRepository<User, Long> {

	User findUserByUsername(String username);

}
