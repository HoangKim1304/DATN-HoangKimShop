package com.shoppingwithme.hoangkimshop.entity;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "productsize")
public class ProductSizeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;
	@Column(name = "size_id")
	private Long size_id;
	@Column(name = "quantity")
	private int quantity;
	public int getId() {
		return id;
	}
	public Long getSize_id() {
		return size_id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setSize_id(Long size_id) {
		this.size_id = size_id;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	@Column(name = "productid")
	private int productid;
	public int getProductid() {
		return productid;
	}
	public void setProductid(int productid) {
		this.productid = productid;
	}
	
	@Column(name = "quantitysold" )
	private int quantitysold;
	
	@Column(name = "datesale" )
	private Date datesale;
	public int getquantitysold() {
		return quantitysold;
	}
	public Date getDatesale() {
		return datesale;
	}
	public void setquantitysold(int quantitysold) {
		this.quantitysold = quantitysold;
	}
	public void setDatesale(Date datesale) {
		this.datesale = datesale;
	}
	@Override
	public int hashCode() {
		return Objects.hash(size_id);
	}
	@Override
	public boolean equals(Object obj) {
		ProductSizeEntity other = (ProductSizeEntity) obj;
		return Objects.equals(size_id, other.size_id);
	}
	
	
}
