package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonView;

import java.util.Set;


/**
 * The persistent class for the section database table.
 * 
 */
@Entity
@Table(name="section")
@NamedQuery(name="Section.findAll", query="SELECT s FROM Section s")
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@JsonView(View.SectionCategory.class)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@JsonView(View.ProductSection.class)
	@Column(nullable=false, length=45)
	private String description;

	@JsonView({View.ProductSection.class,View.SectionCategory.class})
	@Column(nullable=false, length=45)
	private String name;

	//bi-directional many-to-one association to Category
	@JsonView(View.SectionCategory.class)
	@OneToMany(mappedBy="section",fetch=FetchType.EAGER)
	private Set<Category> categories;

	public Section() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Category addCategory(Category category) {
		getCategories().add(category);
		category.setSection(this);

		return category;
	}

	public Category removeCategory(Category category) {
		getCategories().remove(category);
		category.setSection(null);

		return category;
	}

}