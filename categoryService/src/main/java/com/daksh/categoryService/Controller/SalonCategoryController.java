package com.daksh.categoryService.Controller;


import com.daksh.categoryService.Model.Category;
import com.daksh.categoryService.Service.CategoryService;
import com.daksh.categoryService.dto.SalonDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/categories/salon-owner")
public class SalonCategoryController {

    private final CategoryService categoryService;

    @PostMapping("/salon/{id}")
    public ResponseEntity<Category> createCategory(
            @PathVariable Long id,
            @RequestBody Category category
    )
    {
        SalonDTO salonDto = new SalonDTO();
        salonDto.setId(id);

        Category savedCategory = categoryService.saveCategory(category, salonDto);
        return ResponseEntity.ok(savedCategory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(
            @PathVariable Long id
    ) throws Exception {
        SalonDTO salonDto=new SalonDTO();
        salonDto.setId(1L);
        categoryService.deleteCategoryById(id,salonDto.getId());
        return ResponseEntity.ok("Category deleted successfully");
    }

}
