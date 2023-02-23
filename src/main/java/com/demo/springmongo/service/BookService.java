package com.demo.springmongo.service;

import java.util.List;

import com.demo.springmongo.dto.BookDto;

public interface BookService {

	BookDto addOneBook(BookDto bookDto);

	List<BookDto> getAllBooks();

	BookDto findBookById(int id);

	BookDto deleteBookById(int id);

	BookDto updateBook(BookDto bookDto, int id);

}
