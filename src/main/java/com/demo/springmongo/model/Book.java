package com.demo.springmongo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "book")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
	@Id
	private int id;
	private String bookName;
	private double price;
	private Author author;

}
