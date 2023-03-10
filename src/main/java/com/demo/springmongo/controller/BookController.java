package com.demo.springmongo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.springmongo.cache.BookCache;
import com.demo.springmongo.dto.BookDto;
import com.demo.springmongo.service.BookService;

import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/books")
public class BookController {

	@Autowired
	private BookService bookService;
	
	

	@ApiIgnore
	@RequestMapping("/")
	public void redirect(HttpServletResponse response) throws IOException {
		response.sendRedirect("/swagger-ui.html");
	}
	
	
	
	
	@PostMapping("/addBook")
	public ResponseEntity<BookDto> saveBoook(@RequestBody BookDto bookDto) {
		return new ResponseEntity<>(bookService.addOneBook(bookDto), HttpStatus.CREATED);
	}

	@GetMapping("/getAllBook")
	public ResponseEntity<List<BookDto>> getBooks() {
		return new ResponseEntity<List<BookDto>>(bookService.getAllBooks(), HttpStatus.OK);
	}

	@GetMapping("/getBookById/{id}")
	public ResponseEntity<BookDto> getOneBook(@PathVariable int id) {
		return new ResponseEntity<BookDto>(bookService.findBookById(id), HttpStatus.OK);
	}

	@DeleteMapping("/deleteBook/{id}")
	public ResponseEntity<BookDto> deleteBook(@PathVariable int id) {
		return new ResponseEntity<BookDto>(bookService.deleteBookById(id), HttpStatus.OK);
	}

	@PutMapping("/updateBook/{id}")
	public ResponseEntity<BookDto> updateBook(@RequestBody BookDto bookDto, @PathVariable int id) {
		return new ResponseEntity<BookDto>(bookService.updateBook(bookDto, id), HttpStatus.OK);
	}
	
}
