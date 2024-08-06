package com.spring.controller;

import com.spring.dto.CategoryDTO;
import com.spring.model.CategoryBean;
import com.spring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/admin")
public class AdminController {

    public final ModelMapper mapper;
    public final CategoryService categoryService;

    @PostMapping(value = "/upload-category")
    public ResponseEntity<?> uploadCategory(@RequestBody CategoryBean bean) {

        try {
            CategoryDTO addDTO = mapper.map(bean,CategoryDTO.class);
            CategoryDTO dto = categoryService.addCategory(addDTO);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while uploading the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update-category")
    public CategoryDTO updateCategory(@RequestBody CategoryBean bean) {
        CategoryDTO dto = mapper.map(bean, CategoryDTO.class);
        CategoryDTO dto2 = categoryService.findByIdNO(bean.getId());
        dto2.setTitle(dto.getTitle());
        dto2.setStatus(1);
        return  categoryService.addCategory(dto2);
    }

    @PostMapping(value = "/delete-category/{id}")
    public CategoryDTO deleteCategory(@PathVariable Integer id) {
        CategoryDTO dto2 = categoryService.findByIdNO(id);
        dto2.setStatus(0);
        return  categoryService.addCategory(dto2);
    }

    @PostMapping(value = "/active-category/{id}")
    public CategoryDTO activeCategory(@PathVariable Integer id) {
        CategoryDTO dto2 = categoryService.findByIdNO(id);
        dto2.setStatus(1);
        return  categoryService.addCategory(dto2);
    }
}
