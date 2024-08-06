package com.spring.dao;

import com.spring.dto.CheatSheetDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheatSheetDAO extends JpaRepository<CheatSheetDTO,Integer> {

}
