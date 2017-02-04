package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the shop database table.
 * 
 */
@Entity
@Table(name="shop")
@NamedQuery(name="Shop.findAll", query="SELECT s FROM Shop s")
public class Shop implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.PriceProduct.class,View.MemberListFav.class,View.List.class,View.MemberList.class,View.ShopTime.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@Column(length=45)
	private String emblem;

	@Column(name="is_active", nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isActive;

	@JsonView({View.PriceProduct.class,View.GoogleShop.class,View.MemberListFav.class,View.List.class,View.MemberList.class})
	@Column(nullable=false, length=45)
	private String name;

	//bi-directional many-to-many association to Member
	@ManyToMany(mappedBy="shops",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Member> members;

	//bi-directional many-to-one association to Address
	@JsonView({View.GoogleShop.class,View.MemberList.class})
	@OneToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="address_id", nullable=false)
	private Address address;
	
	//bi-directional many-to-one association to ShoppingList
	@OneToMany(mappedBy="shop")
	private Set<ShoppingList> shoppingLists;

	//bi-directional many-to-one association to ShopProduct
	/*@OneToMany(mappedBy="shop")
	private Set<ShopProduct> shopProducts;
	*/
	@Column(nullable=false, length=45)
	private String idgoogle;
	

	public Shop() {
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

	public String getEmblem() {
		return this.emblem;
	}

	public void setEmblem(String emblem) {
		this.emblem = emblem;
	}
	
	public String getIdgoogle() {
		return this.idgoogle;
	}

	public void setIdgoogle(String idgoogle) {
		this.idgoogle = idgoogle;
	}

	public Boolean getIsActive() {
		return this.isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public Address getAddress() {
		return this.address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<ShoppingList> getShoppingLists() {
		return shoppingLists;
	}

	public void setShoppingLists(Set<ShoppingList> shoppingLists) {
		this.shoppingLists = shoppingLists;
	}

}