package sysone.g6e6.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;


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
        pstmt.setDouble(4, playRecord.getSolveTime());
        pstmt.executeUpdate();
        return playRecord;
    }

    public List<Map<String, Double>> findByDifficulty(String difficulty, String subjectContent) throws Exception {

        String sql = "SELECT * FROM (" +
                "    SELECT nickname, solve_time " +
                "    FROM (" +
                "        SELECT u.nickname, pr.solve_time, " +
                "               ROW_NUMBER() OVER (PARTITION BY pr.user_id ORDER BY pr.solve_time ASC) AS rn " +
                "        FROM playrecords pr " +
                "        JOIN users u ON pr.user_id = u.user_id " +
                "        WHERE pr.difficulty = ? " +
                "        AND pr.subject_id = (SELECT s.subject_id FROM subjects s WHERE s.subject_content = ?) " +
                "    ) " +
                "    WHERE rn = 1 " +
                "    ORDER BY solve_time ASC" +
                ") " +
                "WHERE ROWNUM <= 10";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, difficulty);
        pstmt.setString(2, subjectContent);
        ResultSet rs = pstmt.executeQuery();
        List<Map<String, Double>> results = new ArrayList<>();
        while (rs.next()) {
            String nickname = rs.getString("nickname");
            Double solveTime = rs.getDouble("solve_time");
            Map<String, Double> map = new HashMap<>();
            map.put(nickname, solveTime);
            results.add(map);
        }
        return results;
    }

    public List<PlayRecord> findByIdAndDate(int userId, int year, int month) throws Exception {
        List<PlayRecord> myMonthlyRecords = new ArrayList<>();
        LocalDate startDate = LocalDate.of(year, month, 1);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        LocalDateTime endDateTime = endDate.atTime(23, 59, 59);

        PreparedStatement pstmt = conn.prepareStatement(
                "SELECT * FROM playrecords " +
                        "WHERE user_id = ? "
                        + "AND play_date BETWEEN ? AND ?"
        );
        pstmt.setInt(1, userId);
        pstmt.setTimestamp(2, Timestamp.valueOf(startDateTime));
        pstmt.setTimestamp(3, Timestamp.valueOf(endDateTime));
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            myMonthlyRecords.add(new PlayRecord(rs.getInt(1), rs.getInt(2), rs.getInt(3), rs.getString(4), rs.getInt(5),
                    new Timestamp(rs.getDate(6).getTime()).toLocalDateTime()));
        }
        return myMonthlyRecords;
    }

    public boolean findCounts(int userId, int curYear, int curMonth) throws Exception {
        YearMonth yearMonth = YearMonth.of(curYear, curMonth);
        LocalDate lastDayOfMonth = yearMonth.atEndOfMonth();
        LocalDateTime endDateTime = lastDayOfMonth.atTime(23, 59, 59);
        PreparedStatement pstmt = conn.prepareStatement("select 1 from playrecords where "
                + "user_id = ? and "
                + "play_date < ? and rownum = 1"
        );
        pstmt.setInt(1, userId);
        pstmt.setTimestamp(2, Timestamp.valueOf(endDateTime));
        ResultSet rs = pstmt.executeQuery();
        return rs.next();

    }
}

