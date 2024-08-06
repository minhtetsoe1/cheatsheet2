package com.spring.controller;

import com.spring.dto.SectionDTO;
import com.spring.model.CheatSheetBean;
import com.spring.model.SectionBean;
import com.spring.service.SectionService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/section")
public class SectionController {
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private SectionService sectionService;

    @GetMapping(value="/show-by-id/{id}")
    public SectionBean getSectionById(@PathVariable Integer id){
        Optional<SectionDTO> dto = sectionService.getSectionById(id);
        SectionBean bean  = mapper.map(dto,SectionBean.class);
        return bean;
    }

    @PostMapping(value="/add-section")
    public SectionDTO addSection(@RequestBody SectionBean bean){
        SectionDTO dto = mapper.map(bean,SectionDTO.class);
        return sectionService.addSection(dto);
    }

    @GetMapping(value="/section-list")
    public List<SectionBean> showAllSection(){
        List<SectionDTO> dtoList = sectionService.getAllSections();
        List<SectionBean> beanList = dtoList.stream().map(dto->mapper.map(dto,SectionBean.class))
                .collect(Collectors.toList());
        return beanList;
    }
}
