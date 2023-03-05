package com.demo.springmongo.dto;

import org.springframework.data.annotation.Id;

import com.demo.springmongo.model.Author;

public class BookDto {

	@Id
	private int id;
	private String bookName;
	private double price;
	private Author author;


	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}



	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

}
