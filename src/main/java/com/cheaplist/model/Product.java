package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.math.BigInteger;
import java.util.Set;


/**
 * The persistent class for the product database table.
 * 
 */
@Entity
@Table(name="product")
@NamedQuery(name="Product.findAll", query="SELECT p FROM Product p")
public class Product implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.ListProduct.class,View.ProductSection.class,View.ProductShop.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(nullable=false)
	@JsonView(View.ListProduct.class)
	private BigInteger ean;

	@Column(nullable=false, length=45)
	@JsonView({View.ListProduct.class,View.ProductSection.class, View.ProductShop.class,View.CategoryProduct.class})
	private String name;

	@Column(name="unit_name", nullable=false, length=45)
	@JsonView({View.ListProduct.class,View.ProductShop.class})
	private String unitName;

	@Column(nullable=false)
	@JsonView(View.ListProduct.class)
	private float volume;

	//bi-directional many-to-one association to ListProduct
	@OneToMany(mappedBy="product",fetch=FetchType.EAGER)
	private Set<ListProduct> listProducts;

	//bi-directional many-to-one association to Brand
	@JsonView(View.ProductSection.class)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="brand_id", nullable=false)
	private Brand brand;

	//bi-directional many-to-one association to ShopProduct
	@JsonView(View.ProductShop.class)
	@OneToMany(mappedBy="product",fetch=FetchType.EAGER)
	private Set<ShopProduct> shopProducts;

	public Product() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigInteger getEan() {
		return this.ean;
	}

	public void setEan(BigInteger ean) {
		this.ean = ean;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUnitName() {
		return this.unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public float getVolume() {
		return this.volume;
	}

	public void setVolume(float volume) {
		this.volume = volume;
	}

	public Set<ListProduct> getListProducts() {
		return this.listProducts;
	}

	public void setListProducts(Set<ListProduct> listProducts) {
		this.listProducts = listProducts;
	}

	public ListProduct addListProduct(ListProduct listProduct) {
		getListProducts().add(listProduct);
		listProduct.setProduct(this);

		return listProduct;
	}

	public ListProduct removeListProduct(ListProduct listProduct) {
		getListProducts().remove(listProduct);
		listProduct.setProduct(null);

		return listProduct;
	}

	public Brand getBrand() {
		return this.brand;
	}

	public void setBrand(Brand brand) {
		this.brand = brand;
	}

	public Set<ShopProduct> getShopProducts() {
		return this.shopProducts;
	}

	public void setShopProducts(Set<ShopProduct> shopProducts) {
		this.shopProducts = shopProducts;
	}

	public ShopProduct addShopProduct(ShopProduct shopProduct) {
		getShopProducts().add(shopProduct);
		shopProduct.setProduct(this);

		return shopProduct;
	}

	public ShopProduct removeShopProduct(ShopProduct shopProduct) {
		getShopProducts().remove(shopProduct);
		shopProduct.setProduct(null);

		return shopProduct;
	}

}