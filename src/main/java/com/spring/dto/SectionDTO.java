package com.spring.dto;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="section")
public class SectionDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="title")
	private String title;
	@Column(name="content")
	private String content;
	@Column(name = "status", columnDefinition = "TINYINT")
	private int status ;
	@JoinColumn(name = "cheat_sheet_id")
	@ManyToOne(cascade =CascadeType.MERGE)
	private CheatSheetDTO cheatSheet;
	
	@PrePersist
    protected void onCreate() {
        if (this.status == 0) {
            this.status = 1;
        }
    }
}
