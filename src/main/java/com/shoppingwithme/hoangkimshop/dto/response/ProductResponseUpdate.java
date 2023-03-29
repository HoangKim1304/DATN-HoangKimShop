package com.shoppingwithme.hoangkimshop.dto.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductResponseUpdate {

	private int id;
	private String name;
private String shortdescription;
	
	
	private String longdescription;
	
	
	private Double origanalprice;
	
	
	private Double price;
	@Override
	public String toString() {
		return "ProductResponseUpdate [id=" + id + ", name=" + name + ", shortdescription=" + shortdescription
				+ ", longdescription=" + longdescription + ", origanalprice=" + origanalprice + ", price=" + price
				+ ", createDate=" + createDate + ", modifierDate=" + modifierDate + ", status=" + status
				+ ", category_id=" + category_id + ", supplier_id=" + supplier_id + ", listSize=" + listSize + "]";
	}
	private Date createDate;
	private Date modifierDate;
	private int status;
	
	
	private Long category_id;
	
	
	private Long supplier_id;
	private String thumbnail;
	private String image1;
	private String image2;
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
	public Date getCreateDate() {
		return createDate;
	}
	public Date getModifierDate() {
		return modifierDate;
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
	public List<ProductSizeDtOResponse> getListSize() {
		return listSize;
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
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public void setModifierDate(Date modifierDate) {
		this.modifierDate = modifierDate;
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
	public void setListSize(List<ProductSizeDtOResponse> listSize) {
		this.listSize = listSize;
	}
	private String image3;
	private String image4;
	private List<ProductSizeDtOResponse>listSize= new ArrayList<>();
}
