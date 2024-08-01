package sysone.g6e6.service;

import sysone.g6e6.repository.ErrorReportRepository;

public class ReviewNoteChildService {
	private ErrorReportRepository errorReportRepository = new ErrorReportRepository();

	public void addErrorReports(int user_id, int quiz_id) throws Exception {
		errorReportRepository.saveErrorReports(user_id, quiz_id);
	}
}
