package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Article;

/**
 * articlesテーブルを操作するリポジトリクラス.
 * 
 * @author yuma.watanabe
 *
 */
@Repository
public class ArticleRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private final static RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		return article;

	};

	/**
	 * 記事の全件検索.
	 * 
	 * @return 記事の全件情報
	 */
	public List<Article> findAll() {
		String findAllSql = "SELECT id,name,content from articles ORDER BY id DESC";
		List<Article> articleList = template.query(findAllSql, ARTICLE_ROW_MAPPER);
		return articleList;
	}

	/**
	 * 記事の追加.
	 * 
	 * @param article 記事情報
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String insertSql = "INSERT INTO articles(name,content)VALUES(:name,:content)";
		template.update(insertSql, param);
	}

}
