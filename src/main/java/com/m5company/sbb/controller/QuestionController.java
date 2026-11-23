package com.m5company.sbb.controller;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.m5company.sbb.dto.QuestionDto;
import com.m5company.sbb.dto.QuestionForm;
import com.m5company.sbb.entity.Question;
import com.m5company.sbb.entity.SiteUser;
import com.m5company.sbb.repository.QuestionRepository;
import com.m5company.sbb.service.QuestionService;
import com.m5company.sbb.service.UserService;

import jakarta.validation.Valid;

@RequestMapping("/question")
@Controller
public class QuestionController {
	
	@Autowired
	private QuestionService questionService;
	
	@GetMapping("/") //root 요청->index.html
	public String root() {
		return "index";
	}
	
	@GetMapping("/list") //모든 질문 목록 요청->question/list?page=3
	public String list(Model model,@RequestParam(value = "page", defaultValue = "0") int page) {
		
		//사용자가 클릭한 페이지 번호에 해당하는 질문 리스트 가져오기(질문 10개 씩)
		Page<Question> pageList = questionService.getQuestionList(page);
		
		//model 객체에 질문리스트 List 객체 전송해주기
		model.addAttribute("questionList", pageList);
		
		return "question_list"; //.html은 쓰지 않음
	}
	
	@GetMapping("/detail/{id}")
	//유저가 클릭한 질문글 번호가 들어올 자리에 {변수이름}을 선언
	public String detail(@PathVariable("id") Integer id, Model model) {
		
		//Service에 유저가 클릭한 글의 번호에 해당하는 엔티티를 가져오는 호출
		Question question = questionService.getQuestion(id);
		if(question != null) { //해당 글이 존재함			
			model.addAttribute("question", question);			
		} else { //해당 글이 존재하지 않음
			model.addAttribute("errMsg", "선택하신 글이 존재하지 않습니다.");
		}
		
		return "question_detail";
	}
	
	@GetMapping("/create") // /question/create -> 질문등록 폼 페이지를 보여달라는 요청
	public String questionCreate() {
		return "question_form";
	}
	@PreAuthorize("isAuthenticated")
	@PostMapping("/create") //요청의 이름은 같으나 get, post 방식이 다르므로 같은 요청이름으로 생성 가능
	public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult) {
		
		SiteUser user = UserService.getUser(Principal.getName()); //현재 로그인한 유저의 엔티티 가져오기
		
		if(bindingResult.hasErrors()) { //참->에러가 발생, 유효성체크에서 에러가 발생
			return "question_form";
		}
		
		// Service 에서 db에 질문을 저장하는 메소드를 호출
		questionService.createQuestion(questionForm.getSubject(), questionForm.getContent(),
				user);
		
		return "redirect:/question/list";

}
	@PreAuthorize("isAuthenticated") //로그인한 유저만 허용되는 요청
	@GetMapping("/modify/{id}") //질문글 수정 폼으로 이동하기
	public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id
			, Principal principal) {
		
		Question question = questionService.getQuestion(id); 
		//현재 보고 있는 글(수정하려고 하는 글) 가져오기
		
		//로그인한 유저와 글쓴이가 다를때 에러 발생
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		//지금 보고 있는 글 제목과 내용으로 questionForm 채우기
		questionForm.setSubject(question.getSubject());
		questionForm.setContent(question.getContent());
		
		return "question_form";
	}
	
	@PostMapping("/modify/{id}") //질문글 수정하기
	public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult
			,Principal principal, @PathVariable("id") Integer id) {
		
		if(bindingResult.hasErrors()) { //참->에러가 발생, 유효성체크에서 에러가 발생
			return "question_form";
		}
		
		Question question = questionService.getQuestion(id); //수정하려는 질문글 불러오기
		
		//로그인한 유저와 글쓴이가 다를때 에러 발생
		if(!question.getAuthor().getUsername().equals(principal.getName())) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
		}
		
		questionService.questionModify(question, 
				questionForm.getSubject(), questionForm.getContent());
		
		return "redirect:/question/detail/"+id;
		
	}
	
	//질문 삭제 요청
	@PostMapping("/delete/{id}")
	public String questionDelete(@PathVariable("id") Integer id) {
		questionService.questionDelete(id);
		
		return "redirect:/question/list"; //글 삭제 후 글목록으로 다시 이동
	}
}

