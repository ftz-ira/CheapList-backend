package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;



/**
 * The persistent class for the address database table.
 * 
 */
@Entity
@Table(name="address")
@NamedQuery(name="Address.findAll", query="SELECT a FROM Address a")
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	private int id;

	@Column(nullable=false, length=45)
	//@JsonView(View.ShopAddress.class})
	@JsonView({View.MemberIdentity.class,View.GoogleShop.class,View.MemberList.class})
	private String city;

	//@JsonView(View.ShopAddress.class})
	@JsonView({View.MemberIdentity.class,View.GoogleShop.class})
	private double lag;

//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	@JsonView({View.MemberIdentity.class,View.GoogleShop.class})
	private double lng;

//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	@JsonView({View.MemberIdentity.class,View.GoogleShop.class,View.MemberList.class})
	@Column(name="street_name")
	private String streetName;

	@JsonView(View.MemberIdentity.class)
	@Column(name="zip_code", nullable=false)
	private int zipCode;


	//bi-directional many-to-one association to Member
	@OneToOne(mappedBy="address")
	private Member member;

	//bi-directional many-to-one association to Shop
	@OneToOne(mappedBy="address")
	private Shop shop;

	public Address() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public double getLag() {
		return this.lag;
	}

	public void setLag(double lag) {
		this.lag = lag;
	}

	public double getLng() {
		return this.lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public String getStreetName() {
		return this.streetName;
	}

	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	public int getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(int zipCode) {
		this.zipCode = zipCode;
	}

	public Member getMembers() {
		return this.member;
	}

	public void setMembers(Member member) {
		this.member = member;
	}

	public Shop getShops() {
		return this.shop;
	}

	public void setShops(Shop shops) {
		this.shop = shops;
	}
}