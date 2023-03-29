package com.shoppingwithme.hoangkimshop.dto.response;

public class SupplierDTOResponse {

	private String name;
	private String address;
	private String phonenumber;
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public Integer getNummberQuantityProduct() {
		return nummberQuantityProduct;
	}
	public Long getId() {
		return id;
	}
	public void setNummberQuantityProduct(Integer nummberQuantityProduct) {
		this.nummberQuantityProduct = nummberQuantityProduct;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	private String url;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	private Integer nummberQuantityProduct;
	private Long id;
}
