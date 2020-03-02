package com.Medhanialem.controller;

import javax.validation.Valid;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Medhanialem.request.ChurchMembersRequest;
import com.Medhanialem.service.TwService;

@RestController
@RequestMapping("api/v1/sms")
public class TwController {

	private final TwService twService;
	
	
	public TwController(TwService twService) {
		this.twService=twService;
		
	}
	
	@PostMapping(value="/Churchmembers")
	public ResponseEntity<String> sendMesaage(@Valid @RequestBody ChurchMembersRequest req){
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("ke1", "hi");
		headers.add("key2", "hello");
		try {
		twService.sendSmsToMembers(req);
		return new ResponseEntity<String>("Message succesfully sent", headers,HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<String>("wrong phone number", headers,HttpStatus.BAD_REQUEST);
		}
	}
}
