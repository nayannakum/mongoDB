package com.demo.springmongo.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.springmongo.dto.BookDto;
import com.demo.springmongo.model.Book;
import com.demo.springmongo.repository.BookRepository;
import com.demo.springmongo.service.BookService;

@Service
public class BookServiceImpl implements BookService {

	@Autowired
	private BookRepository bookRepository;

	public static Book mapToEntity(BookDto bookDto) {
		Book book = new Book();
		book.setId(bookDto.getId());
		book.setBookName(bookDto.getBookName());
		book.setAuthorName(bookDto.getAuthorName());
		return book;
	}

	public static BookDto mapToDto(Book book) {
		BookDto bookDto = new BookDto();
		bookDto.setId(book.getId());
		bookDto.setBookName(book.getBookName());
		bookDto.setAuthorName(book.getAuthorName());
		return bookDto;
	}

	@Override
	public BookDto addOneBook(BookDto bookDto) {
		Book book = mapToEntity(bookDto);
		bookRepository.save(book);
		return mapToDto(book);
	}

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll().stream().map(BookServiceImpl::mapToDto)
				.collect(Collectors.toList());
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
