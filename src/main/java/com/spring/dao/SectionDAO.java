package com.spring.dao;

import com.spring.dto.SectionDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionDAO extends JpaRepository<SectionDTO,Integer> {
}
