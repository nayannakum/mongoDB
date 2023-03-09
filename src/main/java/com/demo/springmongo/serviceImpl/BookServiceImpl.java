package com.demo.springmongo.serviceImpl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springmongo.dto.BookDto;
import com.demo.springmongo.model.Book;
import com.demo.springmongo.model.BookCache;
import com.demo.springmongo.repository.BookRepository;
import com.demo.springmongo.service.BookService;

@Service
public class BookServiceImpl implements BookService {
	@Autowired
	private BookCache bookCache;

	private BookRepository bookRepository;
	private final ModelMapper mapper;

	public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
		super();
		this.bookRepository = bookRepository;
		this.mapper = mapper;
	}

	public Book mapToEntity(BookDto bookDto) {
		return mapper.map(bookDto, Book.class);

	}

	public BookDto mapToDto(Book book) {
		return mapper.map(book, BookDto.class);
	}

	@Override
	public BookDto addOneBook(BookDto bookDto) {
		bookCache.saveBook(mapper.map(bookDto, Book.class));
		Book book = mapToEntity(bookDto);
		bookRepository.save(book);
		return mapToDto(book);
	}

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll().stream().map(book -> mapToDto(book)).collect(Collectors.toList());
	}

	@Override
	public BookDto findBookById(int id) {
		Book book = bookCache.getBookById(id);
		if (book != null) {
			System.out.println("coming from cache");
			return mapper.map(book, BookDto.class);
		} else {

			Optional<Book> optionalBook = bookRepository.findById(id);
			if (optionalBook.isPresent()) {
				book = optionalBook.get();
				bookCache.saveBook(book);
			}
			System.out.println("coming from db");
			return mapper.map(book, BookDto.class);

		}
	}

	@Override
	public BookDto deleteBookById(int id) {
		Book book = bookRepository.findById(id).get();
		bookRepository.deleteById(id);
		return mapToDto(book);
	}

	@Override
	public BookDto updateBook(BookDto bookDto, int id) {
		Book book = bookRepository.findById(id).get();
		book.setBookName(bookDto.getBookName());
		bookRepository.save(book);
		return mapToDto(book);
	}

}
