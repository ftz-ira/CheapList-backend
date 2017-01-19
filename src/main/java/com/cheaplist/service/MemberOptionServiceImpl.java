package com.cheaplist.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.MemberOption;
import com.cheaplist.repository.MemberOptionRepository;

@Service
public class MemberOptionServiceImpl implements MemberOptionService {
	
	@Resource
	private MemberOptionRepository memberOptionRepository;

	@Override
	@Transactional
	public MemberOption create(MemberOption memberOption) {
		MemberOption createdMemberOption = memberOption;
		return memberOptionRepository.save(createdMemberOption);
	}
	
	@Override
	@Transactional
	public MemberOption findById(int id) {
		return memberOptionRepository.findOne(id);
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public MemberOption delete(int id) throws ExceptionMessage {
		MemberOption deletedMemberOption = memberOptionRepository.findOne(id);
		
		if (deletedMemberOption == null)
			throw new ExceptionMessage();
		
		memberOptionRepository.delete(deletedMemberOption);
		return deletedMemberOption;
	}

	@Override
	@Transactional
	public List<MemberOption> findAll() {
		return memberOptionRepository.findAll();
	}

	@Override
	@Transactional(rollbackFor=ExceptionMessage.class)
	public MemberOption update(MemberOption memberOption) throws ExceptionMessage {
		MemberOption updatedMemberOption = memberOptionRepository.findOne(memberOption.getId());
		
		if (updatedMemberOption == null)
			throw new ExceptionMessage();

		updatedMemberOption.setId(memberOption.getId());
		updatedMemberOption.setName(memberOption.getName());
		
		
		return updatedMemberOption;
	}

}
