package com.demo.PoS.services;

import com.demo.PoS.models.Example;
import com.demo.PoS.repos.ExampleRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ExampleService {
    private final ExampleRepo exampleRepo;

    public List<Example> findAllExamples() {
        return exampleRepo.findAll().stream().map(Example.ExampleModel::getExample).toList();
    }
}
