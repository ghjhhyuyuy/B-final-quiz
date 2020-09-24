package com.example.demo.repository;

import com.example.demo.domain.Group;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by wzw on 2020/9/24.
 */
@Repository
public interface GroupsRepository extends CrudRepository<Group,Long> {
    @Override
    List<Group> findAll();
}
