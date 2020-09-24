package com.example.demo.repository;

import com.example.demo.domain.Trainer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@Repository
public interface TrainersRepository extends CrudRepository<Trainer,Long> {
    @Query(value = "SELECT * FROM trainer WHERE grouped=false",nativeQuery = true)
    List<Trainer> findByGrouped(String grouped);
    @Override
    List<Trainer> findAll();
}
