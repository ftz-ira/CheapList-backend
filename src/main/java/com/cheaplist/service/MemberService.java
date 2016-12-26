package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.MemberNotFound;
import com.cheaplist.model.Member;

public interface MemberService {
	
	public Member create(Member member);
	public Member delete(int id) throws MemberNotFound;
	public List<Member> findAll();
	public Member update(Member member) throws MemberNotFound;
	public Member findById(int id);

}
