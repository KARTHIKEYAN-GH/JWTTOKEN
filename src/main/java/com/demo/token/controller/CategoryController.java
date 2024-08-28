package com.demo.token.controller;

import java.util.Optional;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;
import com.demo.token.repo.CategoryRepository;
import com.demo.token.service.CategoryService;
import com.demo.token.serviceImpl.CategoryServiceImpl;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	private final CategoryServiceImpl categoryService;
	private final CategoryRepository categoryRepository;

	CategoryController(CategoryServiceImpl categoryService, CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
		this.categoryService = categoryService;
	}

	@PostMapping("/addCategories")
	public ResponseEntity<String> addCategory(@RequestBody Category category) {
		try {
			Category categories = categoryRepository.save(category);
			return ResponseEntity.ok(category.getName() + " saved sucessfully");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error saving category: " + e.getMessage());
		}
	}

	@GetMapping("/getAllCategories")
	public ResponseEntity<Set<CatregoryDTO>> getAllCategories(Category category) {
		Set<CatregoryDTO> categoryList = categoryService.getAllCategories();
		return ResponseEntity.ok(categoryList);
	}

	@GetMapping("/getByUuid/{categoryUuid}")
	public ResponseEntity<CatregoryDTO> getCategoryById(@PathVariable String categoryUuid) {
		Optional<CatregoryDTO> category = categoryService.getCategoryByUuid(categoryUuid);

		if (category.isPresent()) {
			return ResponseEntity.ok(category.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Changed to build() for no body
		}
	}

	@PutMapping("/updateById/{categoryUuid}")
	public ResponseEntity<Category> updateCategoryById(@PathVariable String categoryUuid,
			@RequestBody Category category) {
		Optional<Category> existingCategory = categoryService.updateCategoryByUuid(categoryUuid, category);

		if (existingCategory.isPresent()) {
			return ResponseEntity.ok(existingCategory.get());
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@DeleteMapping("/deleteBy/{categoryUuid}")
	public ResponseEntity<String> deleteCategoryById(@PathVariable String categoryUuid) {
		try {
			boolean deletedCategory = categoryService.deleteCategoryByUuid(categoryUuid);
			if (deletedCategory) {
				return ResponseEntity.ok("Category deleted successfully");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found");
			}
		} catch (Exception e) {
			// Log the exception for debugging purposes
			// e.g., log.error("Error deleting category with UUID {}: {}", categoryUuid,
			// e.getMessage());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while deleting the category");
		}
	}
}
