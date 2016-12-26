package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.MemberOptionNotFound;
import com.cheaplist.model.MemberOption;

public interface MemberOptionService {
	
	public MemberOption create(MemberOption memberMemberOption);
	public MemberOption delete(int id) throws MemberOptionNotFound;
	public List<MemberOption> findAll();
	public MemberOption update(MemberOption memberMemberOption) throws MemberOptionNotFound;
	public MemberOption findById(int id);

}
