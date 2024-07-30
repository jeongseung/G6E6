package sysone.g6e6.service;

import sysone.g6e6.repository.ReviewNoteChildRepository;

public class ReviewNoteChildService {
    private ReviewNoteChildRepository reviewNoteChildRepository = new ReviewNoteChildRepository();

    public void addErrorReports(int user_id, int quiz_id)throws Exception {
        reviewNoteChildRepository.saveErrorReports(user_id, quiz_id);
    }
}
