package com.cheaplist.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the openfactfood database table.
 * 
 */
@Entity
@Table(name="openfactfood")
@NamedQuery(name="Openfactfood.findAll", query="SELECT o FROM Openfactfood o")
public class Openfactfood implements Serializable {
	

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;

	@Column(length=255)
	private String brands;

	@Column(length=255)
	private String categorie1;

	@Column(length=255)
	private String categorie2;

	@Column(length=255)
	private String categorie3;

	@Column(length=255)
	private String categorie4;

	@Column(length=255)
	private String categorie5;

	@Column(length=255)
	private String categorie6;

	@Column(length=255)
	private String categorie7;

	@Column(length=255)
	private String generic;

	@Column(length=255)
	private String name;

	@Column(length=255)
	private String quantity;

	@Column(length=255)
	private String truecategory;

	public Openfactfood() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrands() {
		return this.brands;
	}

	public void setBrands(String brands) {
		this.brands = brands;
	}

	public String getCategorie1() {
		return this.categorie1;
	}

	public void setCategorie1(String categorie1) {
		this.categorie1 = categorie1;
	}

	public String getCategorie2() {
		return this.categorie2;
	}

	public void setCategorie2(String categorie2) {
		this.categorie2 = categorie2;
	}

	public String getCategorie3() {
		return this.categorie3;
	}

	public void setCategorie3(String categorie3) {
		this.categorie3 = categorie3;
	}

	public String getCategorie4() {
		return this.categorie4;
	}

	public void setCategorie4(String categorie4) {
		this.categorie4 = categorie4;
	}

	public String getCategorie5() {
		return this.categorie5;
	}

	public void setCategorie5(String categorie5) {
		this.categorie5 = categorie5;
	}

	public String getCategorie6() {
		return this.categorie6;
	}

	public void setCategorie6(String categorie6) {
		this.categorie6 = categorie6;
	}

	public String getCategorie7() {
		return this.categorie7;
	}

	public void setCategorie7(String categorie7) {
		this.categorie7 = categorie7;
	}

	public String getGeneric() {
		return this.generic;
	}

	public void setGeneric(String generic) {
		this.generic = generic;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return this.quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTruecategory() {
		return this.truecategory;
	}

	public void setTruecategory(String truecategory) {
		this.truecategory = truecategory;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Openfactfood [id=").append(id).append(", brands=").append(brands).append(", categorie1=")
				.append(categorie1).append(", categorie2=").append(categorie2).append(", categorie3=")
				.append(categorie3).append(", categorie4=").append(categorie4).append(", categorie5=")
				.append(categorie5).append(", categorie6=").append(categorie6).append(", categorie7=")
				.append(categorie7).append(", generic=").append(generic).append(", name=").append(name)
				.append(", quantity=").append(quantity).append(", truecategory=").append(truecategory).append("]");
		return builder.toString();
	}
}