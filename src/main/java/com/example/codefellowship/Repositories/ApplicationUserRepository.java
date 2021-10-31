package com.example.codefellowship.Repositories;

import com.example.codefellowship.Models.ApplicationUser;
import org.springframework.data.repository.CrudRepository;

public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {
    ApplicationUser findUserById(Long id);

    ApplicationUser findUserByUsername(String username);

}