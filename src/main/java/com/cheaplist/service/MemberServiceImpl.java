package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.MemberNotFound;
import com.cheaplist.model.Member;
import com.cheaplist.repository.MemberRepository;

@Service
public class MemberServiceImpl implements MemberService {
	
	@Resource
	private MemberRepository memberRepository;

	@Override
	@Transactional
	public Member create(Member member) {
		Member createdMember = member;
		return memberRepository.save(createdMember);
	}
	
	@Override
	@Transactional
	public Member findById(int id) {
		return memberRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=MemberNotFound.class)
	public Member delete(int id) throws MemberNotFound {
		Member deletedMember = memberRepository.findOne(id);
		
		if (deletedMember == null)
			throw new MemberNotFound();
		
		memberRepository.delete(deletedMember);
		return deletedMember;
	}

	@Override
	@Transactional
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=MemberNotFound.class)
	public Member update(Member member) throws MemberNotFound {
		Member updatedMember = memberRepository.findOne(member.getId());
		
		if (updatedMember == null)
			throw new MemberNotFound();

		updatedMember.setName(member.getName());
		updatedMember.setLogin(member.getLogin());
		updatedMember.setPassword(member.getPassword());
		updatedMember.setCreatedDate(member.getCreatedDate());
		updatedMember.setIsActive(member.getIsActive());
		updatedMember.setEmail(member.getEmail());
		updatedMember.setToken(member.getToken());
		
		
		
		return updatedMember;
	}

}
