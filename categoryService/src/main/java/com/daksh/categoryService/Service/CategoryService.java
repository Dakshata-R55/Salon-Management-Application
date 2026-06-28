package com.daksh.categoryService.Service;

import com.daksh.categoryService.Model.Category;
import com.daksh.categoryService.dto.SalonDTO;

import java.util.Set;

public interface CategoryService {
    Category saveCategory(Category category, SalonDTO salonDto);
Set<Category> getAllCategoriesBySalon(long iD);

    Set<Category> getAllCategoriesBySalon(Long iD);

    Category getCategoryById(Long iD) throws Exception;

    Category getCategoryById(long iD) throws Exception;

    void deleteCategory(long iD);

    void deleteCategoryById(Long iD,Long salonId) throws Exception;
}
