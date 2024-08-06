package com.spring.dto;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="comment")
public class CommentDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="content")
	private String content;
	@Column(name="create_at")
	private Date createAt;
	@Column(name="status")
	private int status;
	@JoinColumn(name = "user_id")
	@ManyToOne(cascade =CascadeType.ALL)
	private UserDTO user;
	@JoinColumn(name = "cheat_sheet_id")
	@ManyToOne(cascade =CascadeType.ALL)
	private CheatSheetDTO cheatSheeet;
	
	@PrePersist
	protected void onCreate() {
		if(this.status==0) {
			this.status =1;
		}
	}
}
