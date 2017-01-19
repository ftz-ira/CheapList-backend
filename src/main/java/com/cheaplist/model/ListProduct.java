package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * The persistent class for the list_product database table.
 * 
 */
@Entity
@Table(name="list_product")
@NamedQuery(name="ListProduct.findAll", query="SELECT l FROM ListProduct l")
public class ListProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.ListProduct.class,View.MemberList.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@JsonView({View.ListProduct.class,View.MemberList.class})
	@Column(name="product_quantity", nullable=false)
	private Integer productQuantity;
	
	//bi-directional many-to-one association to ShoppingList
	//@JsonView(View.ListProduct.class)
	@ManyToOne
	@JoinColumn(name="list_id", nullable=false)
	private ShoppingList shoppingList;

	//bi-directional many-to-one association to Product
	@ManyToOne
	@JsonView({View.ListProduct.class,View.MemberList.class})
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	public ListProduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Integer getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(Integer productQuantity) {
		this.productQuantity = productQuantity;
	}

	public ShoppingList getShoppingList() {
		return this.shoppingList;
	}

	public void setShoppingList(ShoppingList shoppingList) {
		this.shoppingList = shoppingList;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

}