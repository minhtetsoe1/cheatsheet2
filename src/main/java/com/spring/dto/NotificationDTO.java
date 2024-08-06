package com.spring.dto;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="notification")
public class NotificationDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	@Column(name="message")
	private String message;
	@Column(name="create_at")
	private Date createAt;
	@Column(name="read_status",columnDefinition = "TINYINT")
	private int readStatus;
}
