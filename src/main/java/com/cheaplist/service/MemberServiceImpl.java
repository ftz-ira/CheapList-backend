package com.cheaplist.service;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
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
		createdMember.setIsActive(true);
		createdMember.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		return memberRepository.save(createdMember);
	}

	@Override
	@Transactional
	public Member findById(int id) {
		return memberRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public Member delete(int id) throws ExceptionMessage {
		Member deletedMember = memberRepository.findOne(id);

		if (deletedMember == null)
			throw new ExceptionMessage("MEMBER DELETE... MEMBER ID NOT FOUND");

		memberRepository.delete(deletedMember);
		return deletedMember;
	}

	@Override
	@Transactional
	public List<Member> findAll() {
		return memberRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public Member update(Member member) throws ExceptionMessage {
		Member updatedMember = memberRepository.findOne(member.getId());

		if (updatedMember == null)
			throw new ExceptionMessage("MEMBER UPDATE.. MEMBER NOT FOUND");

		updatedMember.setName(member.getName());
		updatedMember.setLogin(member.getLogin());
		updatedMember.setPassword(member.getPassword());
		updatedMember.setCreatedDate(member.getCreatedDate());
		updatedMember.setIsActive(member.getIsActive());
		updatedMember.setEmail(member.getEmail());
		updatedMember.setToken(member.getToken());

		return updatedMember;
	}

	@Override
	@Transactional(rollbackFor = ExceptionMessage.class)
	public Member patch(Integer idMember, Member member) throws ExceptionMessage {

		Member updatedMember = memberRepository.findOne(idMember);
		if (updatedMember == null)
			throw new ExceptionMessage("MEMBER PATCH.. MEMBER NOT FOUND");

		if (member.getIsActive() != null)
			updatedMember.setIsActive(member.getIsActive());
		if (member.getAddress() != null)
			updatedMember.setAddress(member.getAddress());
		if (member.getEmail() != null)
			updatedMember.setEmail(member.getEmail());
		if (member.getLogin() != null)
			updatedMember.setLogin(member.getLogin());
		if (member.getMemberOptions() != null)
			updatedMember.setMemberOptions(member.getMemberOptions());
		if (member.getName() != null)
			updatedMember.setName(member.getName());
		if (member.getPassword() != null)
			updatedMember.setPassword(member.getPassword());
		if (member.getShops() != null)
			updatedMember.setShops(member.getShops());
		if (member.getToken() != null)
			updatedMember.setToken(member.getToken());
		return updatedMember;

	}

}
