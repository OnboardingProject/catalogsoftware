package com.example.Catalog.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.Catalog.Entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findByLevelId(int levelId);

}
