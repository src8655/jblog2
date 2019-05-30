package com.cafe24.jblog.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.service.BlogService;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;
import com.cafe24.security.Auth;
import com.cafe24.security.AuthUser;

@Controller
@RequestMapping("/{blogId:(?!assets).*}")
public class BlogController {

	@Autowired
	BlogService blogService;
	

	@RequestMapping({"","/","/{pathNo1}","/{pathNo1}/{pathNo2}"})
	public String blog(
			@PathVariable String blogId,
			@PathVariable Optional<Long> pathNo1,
			@PathVariable Optional<Long> pathNo2,
			Model model
			) {
		
		BlogVo blogVo = blogService.getBlogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		
		if(pathNo1.isPresent()) model.addAttribute("hasCategory", true);
		else model.addAttribute("hasCategory", false);
		
		//포스트 뷰
		PostVo mainPostVo = blogService.getPost(pathNo1, pathNo2, blogId);
		model.addAttribute("mainPostVo", mainPostVo);
		
		//포스트 리스트
		Map<String, Object> postMap = blogService.getPostList(pathNo1, pathNo2, blogId, 1);
		List<PostVo> mainPostList = (List<PostVo>)postMap.get("postList");
		model.addAttribute("mainPostList", mainPostList);
		Map<String, Integer> pagingMap = (Map<String, Integer>)postMap.get("pagingMap");
		model.addAttribute("pagingMap", pagingMap);
		
		//카테고리 리스트
		List<CategoryVo> categoryList = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", categoryList);
		
		return "blog/blog-main";
	}
	
	@Auth
	@RequestMapping(value="/admin/basic", method = RequestMethod.GET)
	public String adminBasic(
			@PathVariable String blogId,
			@AuthUser UserVo authUser,
			Model model
			) {
		
		//잘못된 접근
		if(!authUser.getId().equals(blogId)) return "redirect:/";
		
		BlogVo blogVo = blogService.getBlogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		
		
		//메뉴 - 현재위치
		model.addAttribute("position", "basic");
		
		return "blog/blog-admin-basic";
	}

	@Auth
	@RequestMapping(value="/admin/basic", method = RequestMethod.POST)
	public String adminBasic(
			@PathVariable String blogId,
			@AuthUser UserVo authUser,
			@ModelAttribute @Valid BlogVo blogVo,
			BindingResult result,
			@RequestParam(value="logo_file") MultipartFile logo_file
			) {
		
		//잘못된 접근
		if(result.hasErrors()) return "redirect:/";
		if(!authUser.getId().equals(blogId)) return "redirect:/";
		
		blogService.update(blogVo, logo_file, authUser);
		
		return "redirect:/" + blogId + "/admin/basic";
	}

	@Auth
	@RequestMapping("/admin/category")
	public String adminCategory(
			@PathVariable String blogId,
			@AuthUser UserVo authUser,
			Model model
			) {
		
		//잘못된 접근
		if(!authUser.getId().equals(blogId)) return "redirect:/";
		
		BlogVo blogVo = blogService.getBlogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> categoryList = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", categoryList);
		model.addAttribute("cnt", categoryList.size());
		
		
		//메뉴 - 현재위치
		model.addAttribute("position", "category");
		
		return "blog/blog-admin-category";
	}

	@Auth
	@RequestMapping(value="/admin/write", method = RequestMethod.GET)
	public String adminWrite(
			@PathVariable String blogId,
			@AuthUser UserVo authUser,
			Model model
			) {
		
		//잘못된 접근
		if(!authUser.getId().equals(blogId)) return "redirect:/";
		
		BlogVo blogVo = blogService.getBlogInfo(blogId);
		model.addAttribute("blogVo", blogVo);
		
		List<CategoryVo> categoryList = blogService.getCategoryList(blogId);
		model.addAttribute("categoryList", categoryList);
		
		//메뉴 - 현재위치
		model.addAttribute("position", "write");
		
		return "blog/blog-admin-write";
	}

	@Auth
	@RequestMapping(value="/admin/write", method = RequestMethod.POST)
	public String adminWrite(
			@ModelAttribute @Valid PostVo postVo,
			BindingResult result,
			@PathVariable String blogId,
			@AuthUser UserVo authUser,
			Model model
			) {
		
		//잘못된 접근
		if(result.hasErrors()) return "redirect:/";
		if(!authUser.getId().equals(blogId)) return "redirect:/";
		
		blogService.addPost(postVo);
		
		return "redirect:/" + blogId + "/admin/write";
	}
}
