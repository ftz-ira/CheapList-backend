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
@Table(name="category")
@NamedQuery(name="Category.findAll", query="SELECT c FROM Category c")
public class Category implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView({View.SectionProduct.class,View.ProductSection.class})
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(name="is_active", nullable=false)
	private byte isActive;
	
	@JsonView({View.SectionProduct.class,View.ProductSection.class})
	@Column(nullable=false, length=45)
	private String name;

	//bi-directional many-to-one association to Section
	@JsonView(View.ProductSection.class)
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="section_id", nullable=false)
	private Section section;

	//bi-directional many-to-many association to Brand
	@JsonView(View.SectionProduct.class)
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(
		name="category_brand"
		, joinColumns={
			@JoinColumn(name="category_id", nullable=false)
			}
		, inverseJoinColumns={
			@JoinColumn(name="brand_id", nullable=false)
			}
		)
	private Set<Brand> brands;

	public Category() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Section getSection() {
		return this.section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Set<Brand> getBrands() {
		return this.brands;
	}

	public void setBrands(Set<Brand> brands) {
		this.brands = brands;
	}

}