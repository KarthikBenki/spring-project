package com.codewithbenki.springproject.controller;

import com.codewithbenki.springproject.model.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1_0")
@CrossOrigin("*")
@Slf4j
public class BookController {
    private  List<Book> books = Arrays.asList(
            new Book(1,"Can true love happen twice","Ravinder"),
            new Book(2,"10 chapters of cricket","Jadeja"),
            new Book(3,"Love what you do","Sachin"),
            new Book(4,"Dont Eat Junk","Rahul")

    );

    @GetMapping("/book")
    public String getBook() {
        log.info("getting the book name");
        return "Can true love happen twice";
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getBooks() {
        log.info("getting all books"+books);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping("/add/book")
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        if(this.books.add(book)){
            log.info("adding book");
            return  new ResponseEntity<>(book,HttpStatus.OK);
        }
        log.error("Error adding book");
        return new ResponseEntity<>(new Book(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
