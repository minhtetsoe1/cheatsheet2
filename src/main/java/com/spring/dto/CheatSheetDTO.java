package com.spring.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name="cheat_sheet")
public class CheatSheetDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "title")
	private String title;
	@Column(name = "content")
	private String summary;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@Column(name = "create_at",updatable = false)
	private Date createdAt;
	@Column(name = "update_at")
	private Date updatedAt;
	@Column(name = "image")
	private String imagePath;
	@Column(name = "status", columnDefinition = "TINYINT")
	private int status = 1;
	@ManyToOne(cascade=CascadeType.MERGE)
	private UserDTO user;
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "cheatsheet_category", joinColumns = {
    @JoinColumn(name = "cheatsheet_id") }, 
	inverseJoinColumns = { 
    @JoinColumn(name = "category_id") })
	private List<CategoryDTO> category;
	
	@PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = new Date();
        }

    }
}
