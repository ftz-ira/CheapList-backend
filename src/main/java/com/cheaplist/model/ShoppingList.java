package com.cheaplist.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * The persistent class for the shopping_list database table.
 * 
 */
@Entity
@Table(name="shopping_list")
@NamedQuery(name="ShoppingList.findAll", query="SELECT s FROM ShoppingList s")
public class ShoppingList implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.ListProduct.class,View.MemberList.class})
	@OrderBy("id asc")
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="created_date")
	private Date createdDate;

	@Column(name="is_actif", nullable=false)
	private byte isActif;

	@JsonView(View.MemberList.class)
	@Column(name="is_close", nullable=false)
	private byte isClose;
	
	@JsonView(View.MemberList.class)
	@Column(name="is_done", nullable=false)
	private byte isDone;

	@JsonView(View.MemberList.class)
	@Column(nullable=false, length=45)
	private String name;

	//bi-directional many-to-one association to ListProduct
	@JsonView(View.ListProduct.class)
	@OneToMany(mappedBy="shoppingList",fetch=FetchType.EAGER)
	private Set<ListProduct> listProducts;

	//bi-directional many-to-one association to Member
	@JsonView(View.MemberIdentity.class)
	@ManyToOne (fetch=FetchType.EAGER)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	public ShoppingList() {
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

	public byte getIsActif() {
		return this.isActif;
	}

	public void setIsActif(byte isActif) {
		this.isActif = isActif;
	}

	public byte getIsClose() {
		return this.isClose;
	}

	public void setIsClose(byte isClose) {
		this.isClose = isClose;
	}
	
	public byte getIsDone() {
		return this.isDone;
	}

	public void setIsDone(byte isDone) {
		this.isDone = isDone;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<ListProduct> getListProducts() {
		return this.listProducts;
	}

	public void setListProducts(Set<ListProduct> listProducts) {
		this.listProducts = listProducts;
	}

	public ListProduct addListProduct(ListProduct listProduct) {
		getListProducts().add(listProduct);
		listProduct.setShoppingList(this);

		return listProduct;
	}

	public ListProduct removeListProduct(ListProduct listProduct) {
		getListProducts().remove(listProduct);
		listProduct.setShoppingList(null);

		return listProduct;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}