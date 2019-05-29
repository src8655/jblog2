package com.cafe24.jblog.service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cafe24.jblog.repository.BlogDao;
import com.cafe24.jblog.repository.CategoryDao;
import com.cafe24.jblog.repository.PostDao;
import com.cafe24.jblog.vo.BlogVo;
import com.cafe24.jblog.vo.CategoryVo;
import com.cafe24.jblog.vo.PostVo;
import com.cafe24.jblog.vo.UserVo;

@Service
public class BlogService {
	public static final int BOARD_CNT = 3;	//한번에 보여질 게시글
	public static final int PAGE_CNT = 3;	//페이지 버튼 개수
	
	//파일 저장 위치
	private static final String SAVE_PATH = "/jblog-uploads";
	private static final String URL = "/assets/blog-images";

	@Autowired
	BlogDao blogDao;

	@Autowired
	PostDao postDao;
	
	@Autowired
	CategoryDao categoryDao;

	public BlogVo getBlogInfo(String blogId) {
		return blogDao.getByBlogId(blogId);
	}

	public boolean update(BlogVo blogVo, MultipartFile logo_file, UserVo authUser) {
		BlogVo oldBlogVo = blogDao.getByBlogId(authUser.getId());

		blogVo.setBlogId(authUser.getId());
		blogVo.setLogo(oldBlogVo.getLogo());
		
		
		String files = restore(logo_file);
		if(!files.equals("")) blogVo.setLogo(files);
		
		return blogDao.update(blogVo);
	}
	
	
	public String restore(MultipartFile multipartFile) {
		String url = "";

		try {
		
			if(multipartFile.isEmpty()) {
				return url;
			}
			
			String originalFilename = 
					multipartFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf('.')+1);
			String saveFileName = generateSaveFileName(extName);
			long fileSize = multipartFile.getSize();
			
			System.out.println("##########" + originalFilename);
			System.out.println("##########" + extName);
			System.out.println("##########" + saveFileName);
			System.out.println("##########" + fileSize);
			
			byte[] fileData = multipartFile.getBytes();
			
			OutputStream os = new FileOutputStream(SAVE_PATH + "/" + saveFileName);
			os.write(fileData);
			os.close();
			
			

			//multipartFile.transferTo(new File(SAVE_PATH + "/" + saveFileName));
			
			url = URL + "/" + saveFileName;
			
		} catch (IOException e) {
			throw new RuntimeException("Fileupload error:" + e);
		}
		
		return url;
	}
	
	private String generateSaveFileName(String extName) {
		String filename = "";
		Calendar calendar = Calendar.getInstance();
		
		filename += calendar.get(Calendar.YEAR);
		filename += calendar.get(Calendar.MONTH);
		filename += calendar.get(Calendar.DATE);
		filename += calendar.get(Calendar.HOUR);
		filename += calendar.get(Calendar.MINUTE);
		filename += calendar.get(Calendar.SECOND);
		filename += calendar.get(Calendar.MILLISECOND);
		filename += ("." + extName);
		
		return filename;
	}

	public boolean addCategory(CategoryVo categoryVo) {
		return categoryDao.insert(categoryVo);
	}

	public List<CategoryVo> getCategoryList(String blogId) {
		return categoryDao.getList(blogId);
	}

	public boolean delete(String blogId, Long no) {
		CategoryVo categoryVo = new CategoryVo();
		categoryVo.setNo(no);
		categoryVo.setBlogId(blogId);
		return categoryDao.delete(categoryVo);
	}

	public boolean existPost(Long no) {
		int count = postDao.countByCategoryNo(no);
		return count != 0;
	}

	public boolean addPost(PostVo postVo) {
		return postDao.insert(postVo);
	}

	public PostVo getPost(Optional<Long> pathNo1, Optional<Long> pathNo2, String blogId) {
		
		// 1. 둘 다 존재하지 않으면 => 전체 카테고리의 최신 글
		if(!pathNo1.isPresent() && !pathNo2.isPresent()) {
			return postDao.getPost(blogId);
		}
		
		// 2. pathNo1만 존재하면 => pathNo1 카테고리의 최신 글
		if(pathNo1.isPresent() && !pathNo2.isPresent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("blogId", blogId);
			map.put("pathNo1", pathNo1.get());
			return postDao.getPostNo1(map);
		}
		
		// 3. 둘 다 존재하면 => pathNo1 카테고리의 pathNo2 번 글
		if (pathNo1.isPresent() && pathNo2.isPresent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("blogId", blogId);
			map.put("pathNo1", pathNo1.get());
			map.put("pathNo2", pathNo2.get());
			return postDao.getPostNo1No2(map);
		}
		
		return new PostVo();
	}

	public List<PostVo> getPostList(Optional<Long> pathNo1, String blogId) {
		int count = 0;
		//List<PostVo>
		
		// 1. pathNo1이 존재하면 => pathNo1 카테고리의 포스트 리스트
		if(pathNo1.isPresent()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("blogId", blogId);
			map.put("pathNo1", pathNo1.get());
			return postDao.getPostListNo1(map);
		}else{
			// 2. pathno1이 존재하지 않으면 => 전체 카테고리의 포스트 리스트
			return postDao.getPostList(blogId);
		}
		/*
		//페이징
		int count = boardDao.getCount(kwd);	//총 게시글 개수
		int lastPage = (int) Math.ceil((double)count/(double)BOARD_CNT);	//마지막 페이지
		int startNum = ((pages-1) * BOARD_CNT);		//시작번호
		int rangeStart = ((pages-1)/PAGE_CNT) * PAGE_CNT + 1;		//페이지 범위

		Map<String, Integer> pagingMap = new HashMap<String, Integer>();
		pagingMap.put("count", count);
		pagingMap.put("lastPage", lastPage);
		pagingMap.put("startNum", startNum);
		pagingMap.put("rangeStart", rangeStart);
		pagingMap.put("boardCnt", BOARD_CNT);
		pagingMap.put("pageCnt", PAGE_CNT);
		
		Map<String, Object> daoMap = new HashMap<String, Object>();
		daoMap.put("startNum", startNum);
		daoMap.put("boardCnt", BOARD_CNT);
		daoMap.put("kwd", kwd);
		
		List<BoardVo> list = boardDao.getList(daoMap);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		map.put("pagingMap", pagingMap);*/
	}
}
