package com.shoppingwithme.hoangkimshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ProductCategory")
public class ProductCategoryEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "createday")
	private Date createday;
	@Column(name = "modifydate")
	private Date modifydate;
	@Column(name = "deleteflag")
	private boolean deleteflag;
	
	
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Date getCreateday() {
		return createday;
	}
	public Date getModifydate() {
		return modifydate;
	}
	public boolean isDeleteflag() {
		return deleteflag;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setCreateday(Date createday) {
		this.createday = createday;
	}
	public void setModifydate(Date modifydate) {
		this.modifydate = modifydate;
	}
	public void setDeleteflag(boolean deleteflag) {
		this.deleteflag = deleteflag;
	}
	
	
}
