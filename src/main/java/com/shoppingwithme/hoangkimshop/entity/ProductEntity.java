package com.shoppingwithme.hoangkimshop.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "shortdescription", nullable = false)
	private String shortdescription;
	
	@Column(name = "longdescription", nullable = false)
	private String longdescription;
	
	@Column(name = "origanalprice", nullable = false)
	private Double origanalprice;
	
	@Column(name = "price", nullable = false)
	private Double price;
	
	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortdescription() {
		return shortdescription;
	}

	public String getLongdescription() {
		return longdescription;
	}

	public Double getOriganalprice() {
		return origanalprice;
	}

	public Double getPrice() {
		return price;
	}

	public int getStatus() {
		return status;
	}

	public Long getCategory_id() {
		return category_id;
	}

	public Long getSupplier_id() {
		return supplier_id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setShortdescription(String shortdescription) {
		this.shortdescription = shortdescription;
	}

	public void setLongdescription(String longdescription) {
		this.longdescription = longdescription;
	}

	public void setOriganalprice(Double origanalprice) {
		this.origanalprice = origanalprice;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCategory_id(Long category_id) {
		this.category_id = category_id;
	}

	public void setSupplier_id(Long supplier_id) {
		this.supplier_id = supplier_id;
	}

	@Column(name = "status")
	private int status;
	
	@Column(name = "category_id", nullable = false)
	private Long category_id;
	
	public Date getCreateDate() {
		return createDate;
	}

	public Date getModifierDate() {
		return modifierDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setModifierDate(Date modifierDate) {
		this.modifierDate = modifierDate;
	}

	@Column(name = "supplier_id", nullable = false)
	private Long supplier_id;
	private Date createDate;
	private Date modifierDate;
	private String thumbnail;
	private String image1;
	private String image2;
	public String getThumbnail() {
		return thumbnail;
	}

	public String getImage1() {
		return image1;
	}

	public String getImage2() {
		return image2;
	}

	public String getImage3() {
		return image3;
	}

	public String getImage4() {
		return image4;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public void setImage1(String image1) {
		this.image1 = image1;
	}

	public void setImage2(String image2) {
		this.image2 = image2;
	}

	public void setImage3(String image3) {
		this.image3 = image3;
	}

	public void setImage4(String image4) {
		this.image4 = image4;
	}

	private String image3;
	private String image4;
}
