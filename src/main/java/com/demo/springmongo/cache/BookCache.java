package com.demo.springmongo.cache;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.springmongo.model.Book;
import com.demo.springmongo.repository.BookRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;

@Component
public class BookCache {

    @Autowired
    private BookRepository bookRepository;
    
    @Autowired
    private HazelcastInstance hazelcastInstance;
    
    @PostConstruct
    public void init() {
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
       bookRepository.findAll().forEach(book -> bookMap.put(book.getId(), book));
   
    }
    
    public Book getBookById(int id) {
    	long startTime = System.currentTimeMillis();
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Request took " + duration + " ms");
        return bookMap.get(id);
    }
    
    public void saveBook(Book book) {
    	long startTime = System.currentTimeMillis();
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        bookMap.set(book.getId(), book);
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("Request took " + duration + " ms");
        bookRepository.save(book);
    }
    
    public void deleteBook(int id) {
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        bookMap.remove(id);
        bookRepository.deleteById(id);
    }
    
}