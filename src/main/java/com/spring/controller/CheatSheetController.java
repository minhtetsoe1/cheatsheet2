package com.spring.controller;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.config.JwtService;
import com.spring.dto.CategoryDTO;
import com.spring.dto.CheatSheetDTO;
import com.spring.dto.UserDTO;
import com.spring.model.CategoryBean;
import com.spring.model.CheatSheetBean;
import com.spring.model.CheatSheetResponse;
import com.spring.service.CategoryService;
import com.spring.service.CheatSheetService;
import com.spring.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/cheat-sheet")
public class CheatSheetController {

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CheatSheetService cheatSheetService;
    @Autowired
    private UserService userService;
    private final JwtService jwtService;
    @Autowired
    private CategoryService categoryService;
    @PostMapping(value = "/add")
    public ResponseEntity<CheatSheetDTO> addCheatSheet(@RequestParam("title")String title, @RequestParam("summary")String summary,
                                                      @RequestParam("category")String categoryJson, @RequestParam("userId")String  userId,
                                                      @RequestParam("image") MultipartFile file) {

        CheatSheetBean bean = new CheatSheetBean();
        bean.setTitle(title);
        bean.setSummary(summary);
        UserDTO userDTO = userService.getUserById(Integer.parseInt(userId))
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> categoryIds;
        try {
            categoryIds = objectMapper.readValue(categoryJson, new TypeReference<List<Integer>>(){});
            bean.setCategory(categoryIds);

        } catch (IOException e) {
            e.printStackTrace();
        }
        bean.setImagePath(file.getOriginalFilename());

        CheatSheetDTO dto = mapper.map(bean,CheatSheetDTO.class);
        dto.setUser(userDTO);
        List<CategoryDTO> categoryList = categoryService.findByIdLst(bean.getCategory());
        dto.setCategory(categoryList);
        System.out.println(cheatSheetService.insertCheatSheet(dto));
        return ResponseEntity.ok(cheatSheetService.insertCheatSheet(dto));
    }

    @GetMapping(value = "/get-list")
    public List<CheatSheetResponse> showAllCheatSheet() {
        List<CheatSheetDTO> list = cheatSheetService.showAllCheatSheet();
        List<CheatSheetResponse> beanList = list.stream().map(dto -> mapper.map(dto, CheatSheetResponse.class))
                .collect(Collectors.toList());
        return beanList;
    }

    @GetMapping(value = "/show-by-id/{id}")
    public CheatSheetResponse getCheatSheetById(@PathVariable Integer id) {
        Optional<CheatSheetDTO> dto = cheatSheetService.showCheatSheetById(id);
        CheatSheetResponse bean = mapper.map(
                dto, CheatSheetResponse.class);
        return bean;
    }

    @PutMapping(value = "/update")
    public CheatSheetDTO updateCheatSheet(@RequestParam("title")String title,@RequestParam("summary")String summary,
                                          @RequestParam("category")String categoryJson,@RequestParam("id")String  id,
                                          @RequestParam("image") MultipartFile file) {
        CheatSheetDTO dto2 = cheatSheetService.showCheatSheetById(Integer.parseInt(id))
                .orElseThrow(() -> new IllegalArgumentException("CheatSheet not found"));
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> categoryIds = new ArrayList<>();

        try {
            // Parse the JSON string into a JsonNode
            JsonNode jsonArrayNode = objectMapper.readTree(categoryJson);

            // Iterate over the array elements
            for (JsonNode node : jsonArrayNode) {
                int categoryId = node.get("id").asInt();
                categoryIds.add(categoryId);
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
        List<CategoryDTO> categoryList = categoryService.findByIdLst(categoryIds);
        dto2.setCategory(categoryList);
        dto2.setSummary(summary);
        dto2.setTitle(title);
        dto2.setImagePath(file.getOriginalFilename());
        dto2.setUpdatedAt( new Date());
        return cheatSheetService.updateCheatSheet(dto2);
    }

}
