package com.demo.token.serviceImpl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.demo.token.dto.CatregoryDTO;
import com.demo.token.model.Category;
import com.demo.token.repo.CategoryRepository;
import com.demo.token.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	private final CategoryRepository categoryRepository;

	CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public Category addCategory(Category category) {
		return categoryRepository.save(category);
	}

	public CatregoryDTO convertsToDTO(Category category) {
		return new CatregoryDTO(category.getUuid(), category.getName(), category.getCreatedBY());
	}

	public Set<CatregoryDTO> getAllCategories() {
		return categoryRepository.findAll().stream().map(this::convertsToDTO).collect(Collectors.toSet());
	}

	public Optional<CatregoryDTO> getCategoryByUuid(String categoryUuid) {
		return categoryRepository.findByUuid(categoryUuid).map(this::convertsToDTO);
	}

	public Optional<Category> updateCategoryByUuid(String categoryUuid, Category category) {

		Optional<Category> existingcategory = categoryRepository.findByUuid(categoryUuid);

		if (existingcategory.isPresent()) {
			Category updateCategory = existingcategory.get();
			updateCategory.setName(category.getName());
			categoryRepository.save(updateCategory);
			return Optional.of(updateCategory);

		} else {
			return Optional.empty();
		}
	}

	@Transactional
	public boolean deleteCategoryByUuid(String uuid) {

		if (categoryRepository.existsByUuid(uuid)) {
			categoryRepository.deleteByUuid(uuid);
			return true;
		}

		else {
			return false;
		}
	}

}
