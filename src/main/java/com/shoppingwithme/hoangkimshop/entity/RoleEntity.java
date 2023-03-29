package com.shoppingwithme.hoangkimshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Roles")
public class RoleEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private int id;
	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "name")
	private String name;

	public int getId() {
		return id;
	}

	public RoleEntity(String name) {

		this.name = name;
	}

	public RoleEntity() {

	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
