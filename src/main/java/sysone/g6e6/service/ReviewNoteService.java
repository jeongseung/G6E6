package sysone.g6e6.service;

import sysone.g6e6.model.Quiz;
import sysone.g6e6.repository.ReviewNoteRepository;
import java.util.List;

public class ReviewNoteService {

    private ReviewNoteRepository reviewNoteRepository = new ReviewNoteRepository();

    public List<Quiz> getQuizList(int id) throws Exception {

        return reviewNoteRepository.findAllQuizByUserId(id);
    }
}
