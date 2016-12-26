package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the brand database table.
 * 
 */
@Entity
@Table(name="brand")
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.SectionProduct.class,View.ProductSection.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Temporal(TemporalType.TIMESTAMP)	
	@Column(name="created_date", nullable=false)
	private Date createdDate;

	@Column(name="is_active")
	@JsonView(View.SectionProduct.class)
	private byte isActive;

	@Column(nullable=false, length=45)
	@JsonView({View.SectionProduct.class,View.ProductSection.class})
	private String name;

	@Column(name="update_date", length=45)
	private String updateDate;

	//bi-directional many-to-many association to Category
	@JsonView(View.ProductSection.class)
	@ManyToMany(mappedBy="brands",fetch=FetchType.EAGER)
	private Set<Category> categories;

	//bi-directional many-to-one association to Product
	@JsonView(View.SectionProduct.class)
	@OneToMany(mappedBy="brand",fetch=FetchType.EAGER)
	private Set<Product> products;

	public Brand() {
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

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setBrand(this);

		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setBrand(null);

		return product;
	}

}