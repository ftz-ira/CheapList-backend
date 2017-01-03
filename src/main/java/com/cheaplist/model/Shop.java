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
//	@JsonView({View.ProductShop.class,View.ShopAddress.class,View.MemberIdentity.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@Column(length=45)
	private String emblem;

	@Column(name="is_active")
	private byte isActive;

//	@JsonView({View.ProductShop.class,View.ShopAddress.class,View.MemberIdentity.class})
	@Column(nullable=false, length=45)
	private String name;

	//bi-directional many-to-many association to Member
	@ManyToMany(mappedBy="shops",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Member> members;

	//bi-directional many-to-one association to Address
//	@JsonView(View.ShopAddress.class)
	@ManyToOne(fetch=FetchType.EAGER, cascade={CascadeType.ALL})
	@JoinColumn(name="address_id", nullable=false)
	private Address address;

	//bi-directional many-to-one association to ShopProduct
	@OneToMany(mappedBy="shop")
	private Set<ShopProduct> shopProducts;
	
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

	public byte getIsActive() {
		return this.isActive;
	}

	public void setIsActive(byte isActive) {
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

	public Set<ShopProduct> getShopProducts() {
		return this.shopProducts;
	}

	public void setShopProducts(Set<ShopProduct> shopProducts) {
		this.shopProducts = shopProducts;
	}

	public ShopProduct addShopProduct(ShopProduct shopProduct) {
		getShopProducts().add(shopProduct);
		shopProduct.setShop(this);

		return shopProduct;
	}

	public ShopProduct removeShopProduct(ShopProduct shopProduct) {
		getShopProducts().remove(shopProduct);
		shopProduct.setShop(null);

		return shopProduct;
	}

}