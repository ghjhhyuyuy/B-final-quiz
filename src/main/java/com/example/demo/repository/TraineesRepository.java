package com.example.demo.repository;

import com.example.demo.domain.Trainee;
import com.example.demo.domain.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@Repository
public interface TraineesRepository extends CrudRepository<Trainee,Long> {
    List<Trainee> findByGrouped(String grouped);
    @Override
    List<Trainee> findAll();
}
