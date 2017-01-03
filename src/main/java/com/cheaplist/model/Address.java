package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;


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
	//@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	private String city;

	//@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	private double lag;

//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	private double lng;

//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	@Column(name="street_name")
	private String streetName;

//	@JsonView({View.MemberIdentity.class,View.ShopAddress.class})
	@Column(name="zip_code", nullable=false)
	private int zipCode;


	//bi-directional many-to-one association to Member
	@OneToMany(mappedBy="address",fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	private Set<Member> members;

	//bi-directional many-to-one association to Shop
	@OneToMany(mappedBy="address")
	private Set<Shop> shops;

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

	public Set<Member> getMembers() {
		return this.members;
	}

	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	public Member addMember(Member member) {
		getMembers().add(member);
		member.setAddress(this);

		return member;
	}

	public Member removeMember(Member member) {
		getMembers().remove(member);
		member.setAddress(null);

		return member;
	}

	public Set<Shop> getShops() {
		return this.shops;
	}

	public void setShops(Set<Shop> shops) {
		this.shops = shops;
	}

	public Shop addShop(Shop shop) {
		getShops().add(shop);
		shop.setAddress(this);

		return shop;
	}

	public Shop removeShop(Shop shop) {
		getShops().remove(shop);
		shop.setAddress(null);

		return shop;
	}

}