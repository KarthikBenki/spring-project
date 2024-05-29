package com.codewithbenki.springproject.controller;

import com.codewithbenki.springproject.model.Dog;
import com.codewithbenki.springproject.service.DogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1_0/dogs")
@Slf4j
public class DogController {

    @Autowired
    private DogService dogService;

    @GetMapping("/dog")
    public ResponseEntity<Dog> getDog(){
        Dog dog = dogService.getDog();
        return new ResponseEntity<>(dog, HttpStatus.OK);
    }
}
