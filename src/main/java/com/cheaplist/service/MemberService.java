package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Member;

public interface MemberService {
	
	public Member create(Member member);
	public Member delete(int id) throws ExceptionMessage;
	public List<Member> findAll();
	public Member update(Member member) throws ExceptionMessage;
	public Member findById(int id);
	public Member patch(Integer idMember, Member member) throws ExceptionMessage;

}
