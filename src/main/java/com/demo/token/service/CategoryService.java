package com.demo.token.service;

import java.util.Optional;
import java.util.Set;

import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;

public interface CategoryService {
	 Category addCategory(Category category);
	
	 Set<CatregoryDTO> getAllCategories();
	
	 Optional<CatregoryDTO> getCategoryByUuid(String categoryUuid);
	
	 Optional<Category> updateCategoryByUuid(String categoryUuid, Category category);
	
	 boolean deleteCategoryByUuid(String uuid);
}
