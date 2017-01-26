package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;


/**
 * The persistent class for the shop_product database table.
 * 
 */
@Entity
@Table(name="shop_product")
@NamedQuery(name="ShopProduct.findAll", query="SELECT s FROM ShopProduct s")
public class ShopProduct implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.ProductShop.class,View.PriceProduct.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@JsonView({View.ProductShop.class,View.PriceProduct.class,View.ShopTime.class})
	private Double price;

	private float ratio;

	//bi-directional many-to-one association to Product
	@JsonView(View.PriceProduct.class)
	@ManyToOne
	@JoinColumn(name="product_id", nullable=false)
	private Product product;

	//bi-directional many-to-one association to Shop
//	@JsonView(View.ProductShop.class)
	@JsonView(View.PriceProduct.class)
	@ManyToOne
	@JoinColumn(name="shop_id", nullable=false)
	private Shop shop;

	public ShopProduct() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public float getRatio() {
		return this.ratio;
	}

	public void setRatio(float ratio) {
		this.ratio = ratio;
	}

	public Product getProduct() {
		return this.product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Shop getShop() {
		return this.shop;
	}

	public void setShop(Shop shop) {
		this.shop = shop;
	}
	

}