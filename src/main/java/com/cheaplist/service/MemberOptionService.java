package com.cheaplist.service;

import java.util.List;

import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.MemberOption;

public interface MemberOptionService {
	
	public MemberOption create(MemberOption memberMemberOption);
	public MemberOption delete(int id) throws ExceptionMessage;
	public List<MemberOption> findAll();
	public MemberOption update(MemberOption memberMemberOption) throws ExceptionMessage;
	public MemberOption findById(int id);

}
