package com.shoppingwithme.hoangkimshop.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.Transient;

@Entity
@Table(name = "supplier")
public class SupplierEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	public Long getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getLogo() {
		return logo;
	}
	public String getAddress() {
		return address;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	@Column(name = "logo")
	private String logo;
	@Column(name = "address")
	private String address;
	@Column(name = "phonenumber")
	private String phonenumber;
	@Transient
    public String getPhotosImagePath() {
        if (logo == null || id == 0) return null;
         
        return "/user-photos/" + id + "/" + logo;
    }
	
	@Column(name = "url")
	private String url;
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "deleteflag")
	
	private boolean deleteflag;
	public boolean isFlag() {
		return deleteflag;
	}
	public void setFlag(boolean flag) {
		this.deleteflag = flag;
	}
}
