package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import sysone.g6e6.model.PlayRecord;
import sysone.g6e6.util.DBUtil;

public class PlayRecordRepository {
	private Connection conn = DBUtil.getInstance().getConn();

	public PlayRecord save(PlayRecord playRecord) throws Exception {
		System.out.println(playRecord.toString());
		PreparedStatement pstmt = conn.prepareStatement(
			"insert into playrecords (user_id,subject_id,difficulty,solve_time,play_date) values (?,?,?,?,sysdate)");
		pstmt.setInt(1, playRecord.getUserId());
		pstmt.setInt(2, playRecord.getSubjectId());
		pstmt.setString(3, playRecord.getDifficulty());
		pstmt.setInt(4, playRecord.getSolveTime());
		pstmt.executeUpdate();
		return playRecord;
	}

	public List<Map<String, Double>> findByDifficulty(String difficulty, String subjectContent) throws Exception {

		String sql = "select nickname, solve_time " +
				"from ( " +
				"    select " +
				"        (select u.nickname from users u where u.user_id = pr.user_id) as nickname, " +
				"        pr.solve_time " +
				"    from playrecords pr " +
				"    where (pr.user_id, pr.solve_time) in ( " +
				"        select " +
				"            pr2.user_id, " +
				"            min(pr2.solve_time) " +
				"        from playrecords pr2 " +
				"        where pr2.difficulty = ? " +
				"        and pr2.subject_id = (select s.subject_id from subjects s where s.subject_content = ?) " +
				"        group by pr2.user_id, pr2.subject_id, pr2.difficulty " +
				"    ) " +
				"    and pr.difficulty = ? " +
				"    and pr.subject_id = (select s.subject_id from subjects s where s.subject_content = ?) " +
				"    order by pr.solve_time asc " +
				") " +
				"where rownum <= 10";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, difficulty);
		pstmt.setString(2, subjectContent);
		pstmt.setString(3, difficulty);
		pstmt.setString(4, subjectContent);

		ResultSet rs = pstmt.executeQuery();
		List<Map<String, Double>> results = new ArrayList<>();
		while(rs.next()) {
			String nickname = rs.getString("nickname");
			Double solveTime = rs.getDouble("solve_time");
			Map<String, Double> map = new HashMap<>();
			map.put(nickname, solveTime);
			results.add(map);
		}
		return results;
	}
}
