package com.demo.springmongo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springmongo.dto.BookDto;
import com.demo.springmongo.model.Book;
import com.demo.springmongo.repository.BookRepository;
import com.demo.springmongo.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	private BookRepository bookRepository;
	private final ModelMapper mapper;

	public BookServiceImpl(BookRepository bookRepository, ModelMapper mapper) {
		super();
		this.bookRepository = bookRepository;
		this.mapper = mapper;
	}

	public Book mapToEntity(BookDto bookDto) {
		return  mapper.map(bookDto, Book.class);
//		book.setId(bookDto.getId());
//		book.setBookName(bookDto.getBookName());
//		book.setAuthorName(bookDto.getAuthorName());
		
	}

	public BookDto mapToDto(Book book) {
		return	mapper.map(book, BookDto.class);
//		BookDto bookDto = new BookDto();
//		bookDto.setId(book.getId());
//		bookDto.setBookName(book.getBookName());
//		bookDto.setAuthorName(book.getAuthorName());
	}

	@Override
	public BookDto addOneBook(BookDto bookDto) {
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
		Book book = bookRepository.findById(id).get();
		return mapToDto(book);
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
		book.setAuthorName(bookDto.getAuthorName());
		bookRepository.save(book);
		return mapToDto(book);
	}

}
