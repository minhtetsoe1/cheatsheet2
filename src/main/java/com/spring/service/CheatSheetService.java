package com.spring.service;

import com.spring.dao.CheatSheetDAO;
import com.spring.dto.CheatSheetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CheatSheetService {
    @Autowired
    private CheatSheetDAO cheatRepo;

    public CheatSheetDTO insertCheatSheet(CheatSheetDTO dto){
        return cheatRepo.save(dto);
    }

    public List<CheatSheetDTO> showAllCheatSheet(){
       return  cheatRepo.findAll();
    }

    public Optional<CheatSheetDTO> showCheatSheetById(Integer id){
        return cheatRepo.findById(id);
    }

    public CheatSheetDTO updateCheatSheet(CheatSheetDTO dto){
        return cheatRepo.save(dto);
    }
}
