package com.spring.model;

import java.sql.Date;
import java.util.List;  // Correct import for List

import org.springframework.web.multipart.MultipartFile;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CheatSheetBean {

	private int id;
	private String title;
	private String summary;
	private Date createdAt;
	private List<Integer> category;
	private MultipartFile image;
	private String imagePath;
	private  Integer userId;
}