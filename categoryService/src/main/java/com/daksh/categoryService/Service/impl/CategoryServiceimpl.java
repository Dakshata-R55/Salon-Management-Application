package com.daksh.categoryService.Service.impl;

import com.daksh.categoryService.Model.Category;
import com.daksh.categoryService.Repository.CategoryRepository;
import com.daksh.categoryService.Service.CategoryService;
import com.daksh.categoryService.dto.SalonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class CategoryServiceimpl implements CategoryService {


    private final CategoryRepository categoryRepository;

    @Override
    public Category saveCategory(Category category, SalonDTO salonDto) {
      Category newCategory = new Category();
      newCategory.setName(category.getName());
      newCategory.setSalonId(category.getSalonId());
      newCategory.setImage(category.getImage());
      newCategory.setSalonId(category.getSalonId());
        return categoryRepository.save(newCategory);
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(long iD) {
        return Set.of();
    }

    @Override
    public Set<Category> getAllCategoriesBySalon(Long iD) {

        return categoryRepository.findBySalonId(iD);
    }

    @Override
    public Category getCategoryById(Long iD) throws Exception {
        return null;
    }

    @Override
    public Category getCategoryById(long iD) throws Exception {
        Category category=categoryRepository.findById(iD).orElse(null);
        if(category==null){
            throw new Exception("Category not found with id: " + iD);
}
        return category;
    }

    @Override
    public void deleteCategory(long iD) {

    }

    @Override
    public void deleteCategoryById(Long iD,Long salonId) throws Exception {
        Category category=getCategoryById(iD);
        if(!category.getSalonId().equals(salonId)){
            throw new Exception("Salon not found with id: " + salonId);
       }
        categoryRepository.deleteById(iD);
    }
}
