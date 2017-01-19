package com.cheaplist.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.cheaplist.exception.ErrorResponse;
import com.cheaplist.exception.ExceptionMessage;
import com.cheaplist.model.Member;
import com.cheaplist.model.ShoppingList;
import com.cheaplist.model.View;
import com.cheaplist.service.MemberService;
import com.cheaplist.service.ShoppingListService;
import com.cheaplist.validator.MemberValidator;
import com.fasterxml.jackson.annotation.JsonView;

// Fix d'urgence
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(value = "/members")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@Autowired
	private ShoppingListService shoppingListService;

	@Autowired
	private MemberValidator memberValidator;

	@InitBinder
	private void initBinder(WebDataBinder binder) {
		binder.setValidator(memberValidator);
	}

	/***** CREATE A NEW MEMBER ******/
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	ResponseEntity<Member> createNewShop(@RequestBody Member member, BindingResult result) throws ExceptionMessage {
		memberValidator.validate(member, result);
		if (result.hasErrors())
			throw new ExceptionMessage("ERROR CREATE MEMBER : " + result.getAllErrors().get(0).getCode());
		System.out.println(member.toString());
		member = memberService.create(member);

		/****
		 * By default, a new member has 9 lists.
		 */
		for (int i = 1; i < 10; i++) {
			/** Create Empty ShoppingList Method **/
			ShoppingList shoppingList = new ShoppingList();
			shoppingList.setName(member.getName() + " List" + i);
			shoppingList.setMember(member);
			shoppingList.setIsActif(true);
			shoppingList.setCreatedDate(new Timestamp(System.currentTimeMillis()));
			shoppingList.setIsFavor(null);
			shoppingList.setIsClose(false);
			shoppingList.setIsDone(false);
			shoppingListService.create(shoppingList);
		}
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	/***** READ ALL METHODE : ALL MEMBER ******/
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Member>> identityFindAll() throws ExceptionMessage {
		ArrayList<Member> memberList = (ArrayList<Member>) memberService.findAll();
		return new ResponseEntity<List<Member>>(memberList, HttpStatus.OK);

	}

	/*****
	 * READ ONE METHODE : ONE MEMBER
	 * 
	 * @throws ExceptionMessage
	 ******/
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Member> identityFindId(@PathVariable Integer id) throws ExceptionMessage {

		Member member;
		member = memberService.findById(id.intValue());
		if (member == null) {
			throw new ExceptionMessage("MEMBER NOT FOUND");
		}
		return new ResponseEntity<Member>(member, HttpStatus.OK);

	}

	/***** READ ONE MEMBER : ALL LIST BY MEMBER ******/
	@JsonView(View.MemberList.class)
	@RequestMapping(value = "/{id}/lists")
	public ResponseEntity<Member> listFindid(@PathVariable Integer id) throws ExceptionMessage {
		Member member = memberService.findById(id.intValue());
		if (member == null) {
			throw new ExceptionMessage("MEMBER NOT FOUND");
		}
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}

	/*****
	 * DELETE A MEMBER
	 * 
	 * @throws MemberNotFound
	 *******/

	@RequestMapping(value = "/{idMember}", method = RequestMethod.DELETE)
	public ResponseEntity<String> RemoveOneMember(@PathVariable Integer idMember) throws ExceptionMessage {
		Member member = memberService.findById(idMember);
		if (member == null) {
			throw new ExceptionMessage("MEMBER NOT DELETE, MEMBER ID NOT FOUND");
		}
		member.setIsActive(false);
		memberService.update(member);
		return new ResponseEntity<String>("ELEMENT DELETED", HttpStatus.OK);
	}

	/****
	 * PATCH A MEMBER
	 * 
	 * @throws ExceptionMessage
	 **********/
	@JsonView(View.MemberIdentity.class)
	@RequestMapping(value = "/{idMember}", method = RequestMethod.PATCH, consumes = "application/json", produces = "application/json")
	public ResponseEntity<Member> PatchMember(@PathVariable Integer idMember, @RequestBody Member member)
			throws ExceptionMessage {

		try {
			member = memberService.patch(idMember, member);
		} catch (ExceptionMessage e) {
			throw new ExceptionMessage(e.getMessage());
		}

		return new ResponseEntity<Member>(member, HttpStatus.OK);

	}

	@ExceptionHandler(ExceptionMessage.class)
	public ResponseEntity<ErrorResponse> exceptionHandler(Exception ex) {
		ErrorResponse error = new ErrorResponse();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ErrorResponse>(error, HttpStatus.OK);

	}

}
