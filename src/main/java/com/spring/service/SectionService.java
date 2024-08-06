package com.spring.service;

import com.spring.dao.SectionDAO;
import com.spring.dto.SectionDTO;
import com.spring.model.SectionBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

@Service
public class SectionService {
    @Autowired
    private SectionDAO sectionRepo;

    public Optional<SectionDTO> getSectionById(Integer id) {
        return sectionRepo.findById(id);
    }

    public List<SectionDTO> getAllSections() {
        return sectionRepo.findAll();
    }

    public SectionDTO addSection(SectionDTO dto) {
        return sectionRepo.save(dto);
    }
}
