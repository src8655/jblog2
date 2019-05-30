package com.cafe24.jblog.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cafe24.jblog.vo.PostVo;

@Repository
public class PostDao {

	@Autowired
	SqlSession sqlSession;

	public int countByCategoryNo(Long no) {
		return (Integer)sqlSession.selectOne("post.countByCategoryNo", no);
	}

	public boolean insert(PostVo postVo) {
		int result = sqlSession.insert("post.insert", postVo);
		return result == 1;
	}

	public PostVo getPost(String blogId) {
		return (PostVo)sqlSession.selectOne("post.getPostByNone", blogId);
	}

	public PostVo getPostNo1(Map<String, Object> map) {
		return (PostVo)sqlSession.selectOne("post.getPostByNo1", map);
	}

	public PostVo getPostNo1No2(Map<String, Object> map) {
		return (PostVo)sqlSession.selectOne("post.getPostByNo1No2", map);
	}

	public List<PostVo> getPostListNo1(Map<String, Object> map) {
		return sqlSession.selectList("post.getPostListNo1", map);
	}

	public List<PostVo> getPostList(Map<String, Object> map) {
		return sqlSession.selectList("post.getPostList", map);
	}

	public int countPostListNo1(Map<String, Object> map) {
		return (Integer)sqlSession.selectOne("post.countPostListNo1", map);
	}

	public int countPostList(String blogId) {
		return (Integer)sqlSession.selectOne("post.countPostList", blogId);
	}

	public int countPositionNo2(Map<String, Object> positionMap) {
		Integer result = (Integer)sqlSession.selectOne("post.countPositionNo2", positionMap);
		if(result == null) return 1;
		else return result;
	}
}
