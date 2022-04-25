package gov.iti.jets.service;

import gov.iti.jets.repo.CategoryRepo;
import gov.iti.jets.repo.entity.CategoryEntity;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryService {
    private final CategoryRepo productCategoryRepo = CategoryRepo.getInstance();
    private final static CategoryService categoryService = new CategoryService();
    private CategoryService() {}

    public static CategoryService getInstance() {
        return categoryService;
    }

    public List<String> getCategories(){
        return productCategoryRepo.getCategories().stream().map(CategoryEntity::getCategory)
                .collect(Collectors.toList());
    }
}
