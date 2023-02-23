package com.demo.springmongo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.demo.springmongo.model.Book;
@Repository
public interface BookRepository extends MongoRepository<Book, Integer> {

}
