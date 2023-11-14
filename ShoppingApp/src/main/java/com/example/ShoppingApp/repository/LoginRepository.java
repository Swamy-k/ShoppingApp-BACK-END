package com.example.ShoppingApp.repository;

import com.example.ShoppingApp.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Long > {

     Optional<Login> findByLoginId(Integer loginId);

     Optional<Login> findByApiKey(String apiKey);
}
