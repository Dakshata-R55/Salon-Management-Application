package com.daksh.categoryService.Controller;

import com.daksh.categoryService.Model.Category;
import com.daksh.categoryService.Service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/catedories")
@RequiredArgsConstructor
public class CaegoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(
            @PathVariable Long id
    ) throws Exception {
        Category category=categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }


    @GetMapping("/salon/{id}")
    public ResponseEntity<Set<Category>> getCategoryBySalon(
            @PathVariable Long id
    )
    {
        Set<Category> categories=categoryService.getAllCategoriesBySalon(id);
        return ResponseEntity.ok(categories);
    }
}
