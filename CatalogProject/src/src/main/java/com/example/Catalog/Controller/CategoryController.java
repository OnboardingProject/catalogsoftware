package com.example.Catalog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Catalog.Entity.Category;
import com.example.Catalog.Entity.CategoryRequest;
import com.example.Catalog.Entity.CategoryUpdateRequest;
import com.example.Catalog.Service.CategoryService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/catalog")
@Slf4j
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	@PostMapping("/add")
	public ResponseEntity<Category> saveCatalog(@RequestBody CategoryRequest categoryRequest) {
		log.info("Save Controller Entry");
		Category categories = categoryService.saveCatalog(categoryRequest);
		log.info("Save Controller Exit");
		return new ResponseEntity<Category>(categories, HttpStatus.CREATED);
	}
	@PutMapping("/update")
	public ResponseEntity<Category> upcateCatalog(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
		log.info("Save Controller Entry");
		Category categories = categoryService.updateCatalog(categoryUpdateRequest);
		log.info("Save Controller Exit");
		return new ResponseEntity<Category>(categories, HttpStatus.CREATED);
	}
	
}
