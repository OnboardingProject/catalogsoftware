package com.example.Catalog.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.Catalog.Entity.Category;
import com.example.Catalog.Entity.CategoryRequest;
import com.example.Catalog.Entity.CategoryUpdateRequest;
import com.example.Catalog.Entity.SubCategory;
import com.example.Catalog.Exception.HierarchyNotFoundException;
import com.example.Catalog.Repository.CategoryRepository;
import com.example.Catalog.Utility.Constants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImplTrial2 implements CategoryService {
	@Autowired
	CategoryRepository categoryRepository;

	public Category saveCatalog(CategoryRequest categoryRequest) {
		log.info("Enterd into save catalog");
		Category category = null;

		// parent request
		if (Objects.isNull(categoryRequest.getHierarchyLevel())) {
			log.info("Call for parent request");
			category = saveParent(categoryRequest);
		} else {
			log.info("Call for child request");
			category = saveChild(categoryRequest);
		}
		categoryRepository.save(category);
		log.info("Exit from save catalog");
		return category;

	}

	Category saveChild(CategoryRequest categoryRequest) {
		log.info("Enterd into child request method");
		Category category = null;
		SubCategory subCategory = null;
		List<SubCategory> subCategoryRequestList = new ArrayList<>();
		String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
		List<Integer> levels = new ArrayList<Integer>();
		int initialArraySize = 1;
		for (String a : levelsHierarchy) {
			// adding levels to an integer list
			levels.add(Integer.parseInt(a));
		}

		category = categoryRepository.findByLevelId(levels.get(0));
		if (Objects.isNull(category))
			throw new HierarchyNotFoundException(Constants.HIERARCHY_EXCEPTION_MSG, HttpStatus.BAD_REQUEST);
		else {
			if (levels.size() == initialArraySize) {
				subCategoryRequestList = category.getLevels();
				subCategoryRequestList.add(new SubCategory(getNewId(subCategoryRequestList),
						categoryRequest.getLevelName(), new ArrayList<>()));
				category.setLevels(subCategoryRequestList);
				return category;
			} else {
				if (Objects.isNull(category.getLevels()))
					throw new HierarchyNotFoundException(Constants.HIERARCHY_EXCEPTION_MSG, HttpStatus.BAD_REQUEST);
				else {
					subCategory = category.getLevels().stream().filter(x -> x.getLevelId() == levels.get(1)).findFirst()
							.get();
					subCategory = recursiveLevels(1, subCategory, categoryRequest, levels);
				}
			}
		}

		List<SubCategory> parentCatList = category.getLevels().stream().filter(x -> x.getLevelId() != levels.get(1))
				.collect(Collectors.toList());
		parentCatList.add(subCategory);
		category.setLevels(parentCatList);
		log.info("Exit from child request method");
		return category;

	}

	List<SubCategory> catList;
	SubCategory iteratedVal = new SubCategory();
	List<SubCategory> temp = new ArrayList<>();
	List<SubCategory> tempList = new ArrayList<>();
	int counter = 0;

	SubCategory recursiveLevels(int i, SubCategory subCategory, CategoryRequest catgeoryRequest, List<Integer> levels) {
		log.info("Enterd into recursive method for child");
		i = i + 1;
		List<SubCategory> temp = subCategory.getLevels();
		if (i == levels.size()) {
			temp.add(new SubCategory(getNewId(temp), catgeoryRequest.getLevelName(), new ArrayList<>()));
			subCategory.setLevels(temp);
		} else {
			int j = i;
			int levelId = levels.get(i);
			iteratedVal = temp.stream().filter(t -> t.getLevelId() == levelId).findFirst().orElse(null);
			iteratedVal = recursiveLevels(i, iteratedVal, catgeoryRequest, levels);
			temp = temp.stream().filter(x -> x.getLevelId() != levels.get(j)).collect(Collectors.toList());
			temp.add(iteratedVal);
			subCategory.setLevels(temp);
		}
		log.info("Exit from recursive method for child");
		return subCategory;

	}

	Category saveParent(CategoryRequest categoryRequest) {
		log.info("Enterd into parent request method");
		Category category = null;
		List<Category> listCategory = categoryRepository.findAll();
		category = new Category();
		// initially if hierarchy is null
		if (listCategory.isEmpty()) {
			category.setLevelId(1);
		} else {
			int nextLevelId = listCategory.get(listCategory.size() - 1).getLevelId() + 1;
			category.setLevelId(nextLevelId);
		}
		category.setLevelName(categoryRequest.getLevelName());
		category.setCreatedBy(categoryRequest.getCreatedBy());
		category.setCreatedTime(LocalDateTime.now());
		category.setLastUpdatedBy(categoryRequest.getLastUpdatedBy());
		category.setLastUpdatedTime(LocalDateTime.now());
		category.setLevels(new ArrayList<>());
		log.info("Exit from parent request method");
		return category;
	}

	private int getNewId(List<SubCategory> temp) {
		log.info("Generating id ");
		int newId = 1;
		if (temp.size() > 0) {
			List<Integer> idList = temp.stream().map(x -> x.getLevelId()).sorted().collect(Collectors.toList());
			newId = idList.get(idList.size() - 1) + 1;
		}
		return newId;
	}

	// update ---------------------------------------
	@Override
	public Category updateCatalog(CategoryUpdateRequest categoryUpdateRequest) {

		if (categoryUpdateRequest.getHierarchyLevel() == null) {
			Category category = categoryRepository.findByLevelId(categoryUpdateRequest.getLevelId());
			category.setLevelName(categoryUpdateRequest.getLevelName());
			category.setLastUpdatedBy(categoryUpdateRequest.getLastUpdatedBy());
			category.setLastUpdatedTime(LocalDateTime.now());
			return categoryRepository.save(category);
		} else {

			SubCategory subCategory = null;
			List<SubCategory> subCategoryRequestList = new ArrayList<>();
			String[] l = categoryUpdateRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();
			for (String a : l) {
				// adding levels to an integer list
				levels.add(Integer.parseInt(a));
			}
			Category category = categoryRepository.findByLevelId(levels.get(0));
			if (levels.size() == 1) {
				subCategoryRequestList = category.getLevels();
				subCategoryRequestList.stream().filter(a -> a.getLevelId() == levels.get(0))
						.forEach(f -> f.setLevelName(categoryUpdateRequest.getLevelName()));
			} else {
				subCategory = category.getLevels().stream().filter(x -> x.getLevelId() == levels.get(1)).findFirst()
						.get();
				subCategory = recursiveUpdateLevel(1, subCategory, categoryUpdateRequest, levels);
			}
			category.setLastUpdatedBy(categoryUpdateRequest.getLastUpdatedBy());
			category.setLastUpdatedTime(LocalDateTime.now());
			categoryRepository.save(category);
			return category;
		}
	}
	// SubCategory iteratedVal = new SubCategory();
	// List<SubCategory> temp = new ArrayList<>();

	SubCategory recursiveUpdateLevel(Integer i, SubCategory subCategory, CategoryUpdateRequest catgeoryUpdateRequest,
			List<Integer> levels) {
		System.out.println("recursive levels");
		if (i < levels.size()) {
			Integer levelId = levels.get(i);

			iteratedVal = subCategory.getLevels().stream().filter(t -> t.getLevelId() == levelId).findFirst()
					.orElse(null);
			iteratedVal.setLevelName(catgeoryUpdateRequest.getLevelName());
			i++;
			recursiveUpdateLevel(i, iteratedVal, catgeoryUpdateRequest, levels);

		}
		return subCategory;
	}

}