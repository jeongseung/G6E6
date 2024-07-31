package sysone.g6e6.service;

import sysone.g6e6.repository.PlayRecordRepository;


import java.util.List;
import java.util.Map;

public class RankingService {

    private PlayRecordRepository playRecordRepository = new PlayRecordRepository();

    public List<Map<String, Double>> getNicknameTime(String difficulty, String subjectContent) throws Exception {
        return playRecordRepository.findByDifficulty(difficulty, subjectContent);
    }

}
