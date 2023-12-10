package com.demo.PoS.repos;

import com.demo.PoS.models.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExampleRepo extends JpaRepository<Example.ExampleModel, Long> {
    @Override
    List<Example.ExampleModel> findAll();
}
