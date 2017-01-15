package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;

/**
 * The persistent class for the category database table.
 * 
 */
@Entity
@Table(name = "category")
@NamedQuery(name = "Category.findAll", query = "SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.SectionCategory.class,View.ListProduct.class,View.Category.class,View.MemberList.class})
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(unique = true, nullable = false)
	private int id;

	@JsonView(View.Category.class)
	@Column(name="is_active", nullable = false, columnDefinition = "TINYINT(1)")
	private Boolean isActive;

	@JsonView({ View.SectionCategory.class, View.Category.class,View.ListProduct.class,View.MemberList.class})
	@Column(nullable = false, length = 45)
	private String name;

	// bi-directional many-to-one association to Section
	@JsonView({View.ListProduct.class,View.MemberList.class})
	@ManyToOne
	@JoinColumn(name = "section_id", nullable = false)
	private Section section;
	
	@JsonView({View.Category.class,View.SectionCategory.class})
	@Column(name = "url_image")
	private String urlImage;

	// bi-directional many-to-one association to Product
	@OrderBy("id asc")
	@JsonView(View.CategoryProduct.class)
	@OneToMany(mappedBy = "category", fetch = FetchType.EAGER)
	private Set<Product> products;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public String getUrlImage() {
		return this.urlImage;
	}

	public void setUrlImage(String urlImage) {
		this.urlImage = urlImage;
	}

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Set<Product> getProducts() {
		return this.products;
	}

	public void setProducts(Set<Product> products) {
		this.products = products;
	}

	public Product addProduct(Product product) {
		getProducts().add(product);
		product.setCategory(this);
		return product;
	}

	public Product removeProduct(Product product) {
		getProducts().remove(product);
		product.setCategory(null);
		return product;
	}

}