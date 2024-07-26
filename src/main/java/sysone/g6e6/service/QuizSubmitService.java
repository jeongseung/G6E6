package sysone.g6e6.service;

import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.Subject;
import sysone.g6e6.repository.QuizRepository;
import sysone.g6e6.repository.SubjectRepository;

public class QuizSubmitService {
	private QuizRepository quizRepository = new QuizRepository();
	private SubjectRepository subjectRepository = new SubjectRepository();

	public Quiz createQuiz(int subjectId, String problem, String answer) throws Exception {
		Quiz quiz = new Quiz(null, subjectId, problem, answer);
		return quizRepository.save(quiz);
	}

	public List<String> getAllSubjects() throws Exception {
		List<String> subjectContentList = new ArrayList<>();
		subjectRepository.findAll().forEach(subject -> subjectContentList.add(subject.getContent()));
		return subjectContentList;
	}

	public Subject getSubjectByContent(String content) throws Exception {
		return subjectRepository.findByContent(content);
	}
}
