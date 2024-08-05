package sysone.g6e6.service;

import java.util.List;
import java.util.Map;

import sysone.g6e6.repository.PlayRecordRepository;

public class RankingService {

	private PlayRecordRepository playRecordRepository = new PlayRecordRepository();

	public List<Map<String, Double>> getNicknameTime(String difficulty, String subjectContent) throws Exception {
		return playRecordRepository.findByDifficulty(difficulty, subjectContent);
	}

}
