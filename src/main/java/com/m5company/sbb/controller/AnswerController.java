package com.m5company.sbb.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.m5company.sbb.dto.AnswerForm;
import com.m5company.sbb.entity.Answer;
import com.m5company.sbb.entity.Question;
import com.m5company.sbb.entity.SiteUser;
import com.m5company.sbb.repository.AnswerRepository;
import com.m5company.sbb.repository.UserRepository;
import com.m5company.sbb.service.AnswerService;
import com.m5company.sbb.service.QuestionService;
import com.m5company.sbb.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequestMapping("/answer")
@RequiredArgsConstructor
@Controller
public class AnswerController {
	
	private final AnswerService answerService;
	
	private final QuestionService questionService;
	
	private final UserService userService;
	
	@PostMapping("/create/{id}")
	//id->답변이 달릴 부모질문글의 번호
	public String createAnswer(@PathVariable("id") Integer id, 
			@Valid AnswerForm answerForm, BindingResult bindingResult, 
			Principal principal, Model model) {
		
		//principal.getName(); //현재 로그인한 유저의 username(아이디) 추출		
		
		Question question = questionService.getQuestion(id);
		
		//답변 등록자의 엔티티를 넣어주기 위해서 SiteUSer 객체 가져오기		
		SiteUser user = userService.getUser(principal.getName());
		//현재 로그인한 유저(principal.getName())로 유저 엔티티 가져오기
		
		
		if(bindingResult.hasErrors()) { //답변글 validation 실패 
			model.addAttribute("question", question);
			return "question_detail";
		}
		
		answerService.createAnswer(answerForm.getContent(), question, user);
		//model에 실어서 보내주기
//		Question question2 = questionService.getQuestion(id);
//		model.addAttribute("question", question2);
		
		return "redirect:/question/detail/"+id; 
	}
	@GetMapping("/modify/{id}")
	public String answerModify(AnswerForm answerForm, @PathVariable("id") Integer id,
			Principal principal) {
		Answer answer = answerService.getAnswer(id); //수정하려는 답변 엔티티 가져오기
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		answerForm.setContent(answer.getContent());
		
		return ""; //답변을 수정하는 폼
		
	}
	@PostMapping("/modify/{id}")
	public String answerModify(@PathVariable("id") Integer id,Principal principal,
			@RequestParam("content") String content) {
		Answer answer = answerService.getAnswer(id); //수정하려는 답변 엔티티 가져오기
		
		if(!answer.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		answerService.answerModify(answer,content);
		
		return "redirect:/question.list"; //답변을 수정하는 폼
		
	}
	@PostMapping("/vote/{id}")
	public String answerVote(@PathVariable("id")Integer id, Principal principal) {
		Answer answer = answerService.getAnswer(id);
		SiteUser user =userService.getUser(principal.getName());
		
		questionService.questionVote(answer,user);
		
	}
}