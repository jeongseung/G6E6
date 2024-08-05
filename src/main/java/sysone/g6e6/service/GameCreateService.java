package sysone.g6e6.service;

import java.util.ArrayList;
import java.util.List;

import sysone.g6e6.model.Subject;
import sysone.g6e6.repository.SubjectRepository;

public class GameCreateService {
	private SubjectRepository subjectRepository = new SubjectRepository();

	public List<String> getAllSubjects() throws Exception {
		List<String> subjectContentList = new ArrayList<>();
		subjectRepository.findAll().forEach(subject -> subjectContentList.add(subject.getContent()));
		return subjectContentList;
	}

	public Subject getSubjectByContent(String content) throws Exception {
		return subjectRepository.findByContent(content);
	}
}
