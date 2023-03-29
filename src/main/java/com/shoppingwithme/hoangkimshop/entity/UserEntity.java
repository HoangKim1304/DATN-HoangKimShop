package com.shoppingwithme.hoangkimshop.entity;

import java.util.*;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;



@Entity
@Table(name = "Users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "firstname", nullable = false)
	private String firstname;
	@Column(name = "lastname", nullable = false)
	private String lastname;
	@Column(name = "email", nullable = false, unique = true)
	private String email;
	public int getId() {
		return id;
	}
	public String getFirstname() {
		return firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public String getEmail() {
		return email;
	}
	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public int getStatus() {
		return status;
	}
	public boolean isIsactive() {
		return isactive;
	}
	public String getAddress() {
		return address;
	}
	public String getAvata() {
		return avata;
	}
	public String getPhonenumber() {
		return phonenumber;
	}
	public Date getBirthday() {
		return birthday;
	}
	public Date getCreateday() {
		return createday;
	}
	public Date getModifierdate() {
		return modifierdate;
	}
	
	public UserEntity() {
		
	}
	
	public UserEntity( String firstname, String lastname, String email, String username, String password,
			int status, boolean isactive, String address, String avata, String phonenumber, Date birthday,
			Date createday, Date modifierdate) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.username = username;
		this.password = password;
		this.status = status;
		this.isactive = isactive;
		this.address = address;
		this.avata = avata;
		this.phonenumber = phonenumber;
		this.birthday = birthday;
		this.createday = createday;
		this.modifierdate = modifierdate;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setAvata(String avata) {
		this.avata = avata;
	}
	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setCreateday(Date createday) {
		this.createday = createday;
	}
	public void setModifierdate(Date modifierdate) {
		this.modifierdate = modifierdate;
	}
	@Column(name = "username",nullable = false, unique = true)
	private String username;
	@Column(name = "password",nullable = false)
	private String password;
	@Column(name = "status")
	private int status;
	@Column(name = "isactive")
	private boolean isactive;
	@Column(name = "address", nullable = false)
	private String address;
	@Column(name = "avata")
	private String avata;
	@Column(name = "phonenumber", unique = true)
	private String phonenumber;
	@Column(name = "birthday")
	private Date birthday;
	@Column(name = "createday")
	private Date createday;
	public Set<RoleEntity> getRoles() {
		return roles;
	}
	public void setRoles(Set<RoleEntity> roles) {
		this.roles = roles;
	}
	@Column(name = "modifierdate")
	private Date modifierdate;
	
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name="id_user"),
			inverseJoinColumns = @JoinColumn(name="id_role")
			)
	private Set<RoleEntity>roles=new HashSet<>();
	
	
	
}
