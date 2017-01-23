package com.cheaplist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Where;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * The persistent class for the member database table.
 * 
 */
@Entity
@Table(name="member")
@Where(clause ="is_active = 1")
@NamedQuery(name="Member.findAll", query="SELECT m FROM Member m")
public class Member implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.MemberIdentity.class,View.MemberList.class,View.MemberListFav.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

		@Column(name="created_date")
	private Date createdDate;

	@JsonView(View.MemberIdentity.class)
	@Column(nullable=false, length=45)
	private String email;
	
	@JsonView(View.MemberIdentity.class)
	
	@Column(name="is_active", nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isActive;

	@JsonView(View.MemberIdentity.class)
	@Column(nullable=false, length=45)
	private String login;

	@JsonView(View.MemberIdentity.class)
	@Column(length=45)
	private String name;

	@JsonView(View.MemberIdentity.class)
	@Column(nullable=false, length=45)
	private String password;

	@Column(length=45)
	private String token;

	//bi-directional many-to-one association to Address
	@JsonView(View.MemberIdentity.class)
	@OneToOne(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinColumn(name="address_id")
	private Address address;

	//bi-directional many-to-one association to MemberOption
	@JsonView(View.MemberIdentity.class)
	@OneToMany(mappedBy="member", cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	private Set<MemberOption> memberOptions;

	//bi-directional many-to-many association to Shop
	@JsonView(View.MemberListFav.class)
	@ManyToMany(cascade = CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(
		name="member_shop"
		, joinColumns={
			@JoinColumn(name="member_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="shop_id", nullable=false)
			}
		)

	private Set<Shop> shops;

	//bi-directional many-to-one association to ShoppingSet
	@JsonView(View.MemberList.class)
	@OrderBy("updated_date asc")
	@Where(clause = "is_Actif =1 ")
	@OneToMany(mappedBy="member",fetch=FetchType.EAGER)
	private Set<ShoppingList> shoppingLists;

	public Member() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getCreatedDate() {
		return this.createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return this.token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<MemberOption> getMemberOptions() {
		return this.memberOptions;
	}

	public void setMemberOptions(Set<MemberOption> memberOptions) {
		this.memberOptions = memberOptions;
	}

	public MemberOption addMemberOption(MemberOption memberOption) {
		getMemberOptions().add(memberOption);
		memberOption.setMember(this);

		return memberOption;
	}

	public MemberOption removeMemberOption(MemberOption memberOption) {
		getMemberOptions().remove(memberOption);
		memberOption.setMember(null);

		return memberOption;
	}

	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Set<ShoppingList> getShoppingLists() {
		return this.shoppingLists;
	}

	public void setShoppingLists(Set<ShoppingList> shoppingLists) {
		this.shoppingLists = shoppingLists;
	}

	public ShoppingList addShoppingSet(ShoppingList shoppingList) {
		getShoppingLists().add(shoppingList);
		shoppingList.setMember(this);
		return shoppingList;
	}

	public ShoppingList removeShoppingSet(ShoppingList shoppingList) {
		getShoppingLists().remove(shoppingList);
		shoppingList.setMember(null);

		return shoppingList;
	}

	
}


