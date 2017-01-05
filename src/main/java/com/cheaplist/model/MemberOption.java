package com.cheaplist.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;



/**
 * The persistent class for the member_option database table.
 * 
 */
@Entity
@Table(name="member_option")
@NamedQuery(name="MemberOption.findAll", query="SELECT m FROM MemberOption m")
public class MemberOption implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
//	@JsonView(View.MemberIdentity.class)
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(unique=true, nullable=false)
	private int id;
	
	//@JsonView(View.MemberIdentity.class)
	@Column(length=45)
	private String name;

	//bi-directional many-to-one association to Member
	
	@ManyToOne(fetch=FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="member_id", nullable=false)
	private Member member;

	public MemberOption() {
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Member getMember() {
		return this.member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

}