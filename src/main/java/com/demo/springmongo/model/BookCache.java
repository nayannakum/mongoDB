package com.demo.springmongo.model;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            bookMap.put(book.getId(), book);
        }
    }
    
    public Book getBookById(int id) {
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        return bookMap.get(id);
    }
    
    public void saveBook(Book book) {
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        bookMap.put(book.getId(), book);
        bookRepository.save(book);
    }
    
    public void deleteBook(int id) {
        IMap<Integer, Book> bookMap = hazelcastInstance.getMap("books");
        bookMap.remove(id);
        bookRepository.deleteById(id);
    }
}