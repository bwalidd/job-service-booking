package com.servicebooking.backend.repository;

import com.servicebooking.backend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findFirstByEmail(String email);
}
