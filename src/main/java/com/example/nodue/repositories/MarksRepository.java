package com.example.nodue.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.nodue.model.Marks;
import com.example.nodue.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MarksRepository extends JpaRepository<Marks, Long> {
    Optional<Marks> findByUserEmail(String email);
    Optional<Marks> findByUser(User user);
    List<Marks> findByStatus(String status);
}
