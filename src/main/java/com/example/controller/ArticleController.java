package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

/**
 * 記事情報を受け取るControllerクラス.
 * 
 * @author yuma.watanabe
 *
 */
@Controller
@RequestMapping("article")
public class ArticleController {

	@ModelAttribute
	public ArticleForm setUpForm() {
		return new ArticleForm();
	}

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	/**
	 * 記事全件の情報を表示.
	 * 
	 * @param model リクエストスコープ
	 * @return 記事全件情報
	 */
	@RequestMapping("")
	public String index(Model model) {
		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
		}

		model.addAttribute("articleList", articleList);

		return "showArticleList";
	}

	/**
	 * 一件の記事の追加.
	 * 
	 * @param articleForm 記事の入力情報
	 * @param model       リクエストスコープ
	 * @return 記事の追加画面
	 */
	@RequestMapping("insert")
	public String insert(ArticleForm articleForm, Model model) {
		Article article = new Article();
		article.setName(articleForm.getName());
		article.setContent(articleForm.getContent());
		articleRepository.insert(article);
		index(model);
		return "showArticleList";
	}

}
