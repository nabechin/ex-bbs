package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * 
 * commentsテーブルを操作するクラス.
 * 
 * @author yuma.watanabe
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		return comment;
	};

	/**
	 * コメント情報の検索.
	 * @param id　記事id
	 * @return コメント情報
	 */
	public List<Comment> findByArticleId(int id) {
		String findByArticleIdSql = "SELECT id,name,content,article_id from comments WHERE article_id=:id ORDER BY id DESC";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Comment> commentList = template.query(findByArticleIdSql, param, COMMENT_ROW_MAPPER);
		return commentList;
	}
}
