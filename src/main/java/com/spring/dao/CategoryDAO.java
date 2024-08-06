package com.spring.dao;

import com.spring.dto.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDAO extends JpaRepository<CategoryDTO,Integer> {

    @Query("select c from CategoryDTO c where c.status = 1")
    public List<CategoryDTO> getActiveCategory();

    @Query("SELECT c FROM CategoryDTO c WHERE c.id IN :categoryIds")
    List<CategoryDTO> findByIdLst(@Param("categoryIds") List<Integer> categoryIds);

    @Query("select c from CategoryDTO c where c.id = :id")
    CategoryDTO findByIdNO(Integer id);
}
