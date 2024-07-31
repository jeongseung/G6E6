package sysone.g6e6.service;

import sysone.g6e6.repository.ReviewNoteRepository;

public class ReviewNoteChildService {
	private ReviewNoteRepository reviewNoteRepository = new ReviewNoteRepository();

	public void addErrorReports(int user_id, int quiz_id) throws Exception {
		reviewNoteRepository.saveErrorReports(user_id, quiz_id);
	}
}
