package com.spring.controller;

import com.spring.dto.CategoryDTO;
import com.spring.model.CategoryBean;
import com.spring.service.CategoryService;
import com.spring.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private UserService userService;

    @GetMapping(value = "/get-list")
    public List<CategoryBean> showActiveCategory() {
        List<CategoryDTO> dtoList = categoryService.getActiveCategory();
        List<CategoryBean> beanList = dtoList.stream().map(dto -> mapper.map(dto, CategoryBean.class))
                .collect(Collectors.toList());
        return beanList;
    }

    @GetMapping(value = "/get-all-list")
    public List<CategoryBean> showActiveCategoryList() {
        List<CategoryDTO> dtoList = categoryService.getCategoryList();
        List<CategoryBean> beanList = dtoList.stream().map(dto -> mapper.map(dto, CategoryBean.class))
                .collect(Collectors.toList());
        return beanList;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<?> uploadCategory(@RequestBody CategoryBean bean) {

        try {
            CategoryDTO addDTO = mapper.map(bean,CategoryDTO.class);
            CategoryDTO dto = categoryService.addCategory(addDTO);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>("An error occurred while uploading the image.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/category-by-id/{id}")
    public CategoryBean getCategoryById(@PathVariable Integer id){
       Optional<CategoryDTO >  dto = categoryService.findById(id);
       CategoryBean bean = mapper.map(dto,CategoryBean.class);
        return bean;
    }

}
