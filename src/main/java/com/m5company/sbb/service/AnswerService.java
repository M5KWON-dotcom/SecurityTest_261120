package com.m5company.sbb.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.m5company.sbb.entity.Answer;
import com.m5company.sbb.entity.Question;
import com.m5company.sbb.entity.SiteUser;
import com.m5company.sbb.repository.AnswerRepository;

@Service
public class AnswerService {
	
	@Autowired
	private AnswerRepository answerRepository;
	
	//답변 등록 메소드
	public void createAnswer(String content, Question question, SiteUser author) {
		
		//답변 저장->save(entity)		
		Answer answer = new Answer();
		answer.setContent(content); //답변의 내용 등록
		answer.setCreateDate(LocalDateTime.now()); //현재 시간 등록
		answer.setQuestion(question); //부모질문글 엔티티 객체 등록
		answer.setAuthor(author); //답변 등록자 객체 추가
		
		answerRepository.save(answer);
	}
}