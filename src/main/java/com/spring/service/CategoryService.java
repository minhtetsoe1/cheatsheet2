package com.spring.service;

import com.spring.dao.CategoryDAO;
import com.spring.dto.CategoryDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryDAO repo;
    @Autowired
    private ModelMapper mapper;

    public CategoryDTO addCategory(CategoryDTO dto) {
        return repo.save(dto);
    }

    public List<CategoryDTO> getActiveCategory() {
        return repo.getActiveCategory();
    }

    public List<CategoryDTO> getCategoryList() {
        return repo.findAll();
    }

    public List<CategoryDTO> findByIdLst(List<Integer> categoryIds) {
        List<CategoryDTO> categories = repo.findByIdLst(categoryIds);
        // Assuming you have a mapper to convert entities to DTOs
        return categories.stream()
                .map(category -> mapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    public CategoryDTO findByIdNO(Integer id ){
        return repo.findByIdNO(id);
    }

    public Optional<CategoryDTO> findById(Integer id ){
        return repo.findById(id);
    }
}
