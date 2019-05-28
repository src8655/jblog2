package com.cafe24.jblog.controller.api;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cafe24.jblog.dto.JSONResult;
import com.cafe24.jblog.service.UserService;

//Ajax 요청 처리
@Controller("userAPIController")
@RequestMapping("/user/api")
public class UserController {
	
	@Autowired
	UserService userService;

	@ResponseBody
	@RequestMapping("/checkid")
	public JSONResult checkEmail(@RequestParam(value="id", required = true, defaultValue = "") String id) {
		boolean exist = userService.existId(id);
		return JSONResult.success(exist);
	}
}
