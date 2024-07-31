package sysone.g6e6.service;

import java.util.List;

import sysone.g6e6.model.PlayRecord;
import sysone.g6e6.repository.PlayRecordRepository;

public class MyPageService {
	private PlayRecordRepository playRecordRepository = new PlayRecordRepository();

	public List<PlayRecord> createRecordList(int userId, int curYear, int curMonth) throws Exception {
		return playRecordRepository.findByIdAndDate(userId, curYear, curMonth);
	}

	public boolean getPreviousRecords(int userId, int curYear, int curMonth) throws Exception {
		return playRecordRepository.findCounts(userId, curYear, curMonth);
	}

}
