package sysone.g6e6.repository;

import sysone.g6e6.util.DBUtil;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;

public class ReviewNoteChildRepository {
    private Connection conn = DBUtil.getInstance().getConn();
    private CallableStatement callableStatement = null;

    public void saveErrorReports(int userId, int quizId) throws Exception{
        String plSql = "{ ? = call add_error_report(?, ?) }";
        callableStatement = conn.prepareCall(plSql);
        callableStatement.registerOutParameter(1, Types.VARCHAR);
        callableStatement.setInt(2, userId);
        callableStatement.setInt(3, quizId);
        callableStatement.execute();

        String result = callableStatement.getString(1);
        System.out.println("Result: " + result);
    }
}
