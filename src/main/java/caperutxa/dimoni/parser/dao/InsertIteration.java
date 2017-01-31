package caperutxa.dimoni.parser.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import caperutxa.dimoni.parser.model.LogModel;

public class InsertIteration extends BaseDAO {

	DateFormat formatSqlDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/**
	 * Insert a new iteration and return a Result set with the new id inserted
	 * 
	 * @param model
	 * @return
	 * @throws SQLException
	 */
	public ResultSet insertNewIteration(LogModel model) throws SQLException {
		StringBuilder query = new StringBuilder("INSERT INTO test_iteration ")
				.append(" (test_name, environment, success, result, message, start_time, end_time, log_file, report_file) ")
				.append(" VALUES (?,?,?,?,?,?,?,?,?) ");
		
		PreparedStatement statement = connection.prepareStatement(query.toString(), Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, model.getTestNameAsString());
		statement.setString(2, model.getEnvironment());
		statement.setBoolean(3, model.isTestSuccess());
		statement.setString(4, model.getTestResultDeclaration().name());
		statement.setString(5, model.getLogLevelDeclarationCountAsString());
		statement.setString(6, formatSqlDate.format(model.getStartTestTime().toDate()));
		statement.setString(7, formatSqlDate.format(model.getEndTestTime().toDate()));
		statement.setString(8, model.getLogFileAbsolutePath());
		statement.setString(9, model.getLogReportFileAbsolutePath());
		
		statement.execute();
		
		return statement.getGeneratedKeys();
	}
	
	/**
	 * Insert partial times into DB
	 * 
	 * @param iteration
	 * @param timeType
	 * @param page
	 * @param duration
	 * @throws SQLException
	 */
	public void insertPartialTimes(int iteration, String timeType, String page, long duration) throws SQLException {
		StringBuilder query = new StringBuilder("INSERT INTO test_time ")
				.append(" (test_iteration, time_type_generic, time_type_specific, duration) ")
				.append(" VALUES (?,?,?,?) ");
		
		PreparedStatement statement = connection.prepareStatement(query.toString());
		statement.setInt(1, iteration);
		statement.setString(2, timeType);
		statement.setString(3, page);
		statement.setLong(4, duration);
		
		statement.executeUpdate();
	}
	
}
