package com.spring.dto;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="rating")
public class RatingDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="pay_rate")
	private int payRate;
	@JoinColumn(name = "user_id")
	@ManyToOne(cascade =CascadeType.ALL)
	private UserDTO user;
	@JoinColumn(name = "cheat_sheet_id")
	@ManyToOne(cascade =CascadeType.ALL)
	private CheatSheetDTO cheatSheeet;
}
