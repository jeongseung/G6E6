package sysone.g6e6.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import sysone.g6e6.model.ErrorReport;
import sysone.g6e6.model.Quiz;
import sysone.g6e6.model.User;
import sysone.g6e6.model.UserErrorReport;
import sysone.g6e6.repository.ErrorReportRepository;
import sysone.g6e6.repository.QuizRepository;
import sysone.g6e6.repository.SubjectRepository;
import sysone.g6e6.repository.UserErrorReportRepository;
import sysone.g6e6.repository.UserRepository;

public class AdminErrorService {
	private ErrorReportRepository errorReportRepository = new ErrorReportRepository();
	private UserErrorReportRepository userErrorReportRepository = new UserErrorReportRepository();
	private UserRepository userRepository = new UserRepository();
	private QuizRepository quizRepository = new QuizRepository();
	private SubjectRepository subjectRepository = new SubjectRepository();

	public HashMap<String, List<?>> getAllErrors() throws Exception {
		// 닉네임, 이메일 , 신고일시, 유형, 퀴즈, 삭제, 유지
		HashMap<String, List<?>> errorReportHashMap = new HashMap<>();
		List<ErrorReport> errorReports = errorReportRepository.findAllUnresolved();

		List<Integer> errorReportIds = new ArrayList<>();
		List<String> userNicknames = new ArrayList<>();
		List<String> userEmails = new ArrayList<>();
		List<LocalDateTime> errorReprotDate = new ArrayList<>();
		List<String> subjectContents = new ArrayList<>();
		List<Quiz> errorQuizzes = new ArrayList<>();

		for (ErrorReport errorReport : errorReports) {
			UserErrorReport userErrorReport = userErrorReportRepository.findByErrorId(
				errorReport.getId());
			User user = userRepository.findByUserId(userErrorReport.getUserId());
			Quiz quiz = quizRepository.findByQuizId(errorReport.getQuizId());

			errorReportIds.add(errorReport.getId());
			userNicknames.add(user.getNickname());
			userEmails.add(user.getEmail());
			errorReprotDate.add(userErrorReport.getReportDate());
			subjectContents.add(subjectRepository.findBySubjectId(quiz.getSubjectId()).getContent());
			errorQuizzes.add(quiz);
		}
		errorReportHashMap.put("신고번호", errorReportIds);
		errorReportHashMap.put("닉네임", userNicknames);
		errorReportHashMap.put("이메일", userEmails);
		errorReportHashMap.put("신고일시", errorReprotDate);
		errorReportHashMap.put("유형", subjectContents);
		errorReportHashMap.put("퀴즈", errorQuizzes);
		return errorReportHashMap;
	}

	public void deleteErrorQuiz(Integer quizId, Integer errorReportId) throws Exception {
		userErrorReportRepository.deleteUserErrorReport(errorReportId);
		errorReportRepository.deleteErrorReport(errorReportId);
		quizRepository.deleteQuizById(quizId);
	}

	public void revokeErrorQuiz(Integer errorReportId) throws Exception {
		userErrorReportRepository.deleteUserErrorReport(errorReportId);
		errorReportRepository.deleteErrorReport(errorReportId);
	}
}
