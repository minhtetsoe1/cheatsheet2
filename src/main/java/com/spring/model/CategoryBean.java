package com.spring.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class CategoryBean {
    private Integer id;
    private String title;
    private Integer status;
}
