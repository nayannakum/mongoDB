package com.demo.springmongo.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springmongo.cache.BookCache;
import com.demo.springmongo.dto.BookDto;
import com.demo.springmongo.model.Book;
import com.demo.springmongo.repository.BookRepository;
import com.demo.springmongo.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookCache bookCache; // Cache object to store books

	private BookRepository bookRepository; // Repository object to interact with the database
	private final ModelMapper mapper; // Object for mapping between DTO and entity objects

	public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
		super();
		this.bookRepository = bookRepository;
		this.mapper = mapper;
	}

	// Maps a DTO object to an entity object
	public Book mapToEntity(BookDto bookDto) {
		return mapper.map(bookDto, Book.class);
	}

	// Maps an entity object to a DTO object
	public BookDto mapToDto(Book book) {
		return mapper.map(book, BookDto.class);
	}

	// Adds a new book to the system
	@Override
	public BookDto addOneBook(BookDto bookDto) {
		
		bookCache.saveBook(mapper.map(bookDto, Book.class)); // Save the book to cache
		Book book = mapToEntity(bookDto);
		bookRepository.save(book); // Save the book to the database
		return mapToDto(book);
	}

	// Gets all books from the system
	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll().stream().map(book -> mapToDto(book)).collect(Collectors.toList());
	}

	// Finds a book by its ID
	@Override
	public BookDto findBookById(int id) {
		Book book = bookCache.getBookById(id); // Try to find the book in cache first
		if (book != null) {
			return mapper.map(book, BookDto.class);
		} else {
			Optional<Book> optionalBook = bookRepository.findById(id); // Otherwise, find the book in the database
			if (optionalBook.isPresent()) {
				book = optionalBook.get();
				bookCache.saveBook(book); // Save the book to cache for future use
			}
			return mapper.map(book, BookDto.class);
		}
	}

	// Deletes a book by its ID
	@Override
	public BookDto deleteBookById(int id) {
		Book bookFromCache = bookCache.getBookById(id); // Try to find the book in cache first
		if (bookFromCache != null) {
			bookCache.deleteBook(id); // Delete the book from cache
		}
		Book book = bookRepository.findById(id).get(); // after, find the book in the database
		bookRepository.deleteById(id); // Delete the book from the database
		return mapToDto(book);
	}

	// Updates a book by its ID
	@Override
	public BookDto updateBook(BookDto bookDto, int id) {
		Book book = bookRepository.findById(id).get(); // Find the book in the database
		book.setBookName(bookDto.getBookName()); // Update the book's name
		bookRepository.save(book); // Save the updated book to the database
		return mapToDto(book);
	}
}
