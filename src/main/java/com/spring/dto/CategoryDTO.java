package com.spring.dto;

import java.util.List;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="category")
public class CategoryDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="type")
	private String title;
	@Column(name = "status", columnDefinition = "TINYINT")
	private int status ;
	@ManyToMany(mappedBy="category")
	private List<CheatSheetDTO> cheatSheet;
	@PrePersist
    protected void onCreate() {
        if (this.status == 0) {
            this.status = 1;
        }
    }
}
