package org.jit.sose.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.jit.sose.entity.ArchiveReservation;
import org.jit.sose.service.ArchiveReservationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ArchiveReservationController {
	
	
	@Autowired
	private ArchiveReservationService ArchiveReservationService;
	
	
	@ResponseBody
	@RequestMapping(value = "/insertArchiveReservation.do",method = RequestMethod.POST)
	@CrossOrigin(origins = "*", maxAge = 3600)
	public com.alibaba.fastjson.JSONObject insertArchiveReservation(@RequestBody String str,ArchiveReservation archiveReservation) throws ParseException{
		JSONObject strj = new JSONObject(str);
		com.alibaba.fastjson.JSONObject res = new com.alibaba.fastjson.JSONObject();
		System.out.println(str);
		archiveReservation.setUserid(strj.getString("userId"));
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(strj.get("rvDate").toString());
		archiveReservation.setRvdate(date);
		Date date1 = new SimpleDateFormat("HH:mm").parse(strj.get("rvStartTime").toString());
		archiveReservation.setRvstarttime(date1);
		Date date2 = new SimpleDateFormat("HH:mm").parse(strj.get("rvEndTime").toString());
		archiveReservation.setRvendtime(date2);
		archiveReservation.setRvthing(strj.getString("rvThing"));
		archiveReservation.setRvplace(strj.getString("rvPlace"));
		archiveReservation.setRemark(strj.getString("remark"));
		System.out.println(archiveReservation);
		boolean b = ArchiveReservationService.insertReservation(archiveReservation);
		if (b) {
			res.put("success", true);
		}else {
			res.put("success", false);
		}
		return res;
	}

}
