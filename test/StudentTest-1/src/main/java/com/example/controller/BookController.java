package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.model.dto.Book;
import com.example.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("book")
public class BookController {
	
	private final BookService service;
	

	
	@GetMapping("selectAllList")
	public List<Book> selectAllList() {

	return service.selectAllList();

	}
	
	
	
	

}
