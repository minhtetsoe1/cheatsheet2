package com.spring.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Date;
import java.util.List;
@Getter
@Setter
public class CheatSheetResponse {
    private int id;
    private String title;
    private String summary;
    private Date createdAt;
    private Date updatedAt;
    private List<CategoryBean> category;
    private String imagePath;
    private  Integer userId;
    private String userName;
}
