package caperutxa.dimoni.parser.manager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import caperutxa.dimoni.parser.Parser;
import caperutxa.dimoni.parser.constants.ETimes;
import caperutxa.dimoni.parser.constants.LogLevel;
import caperutxa.dimoni.parser.dao.InsertIteration;

/**
 * The manager is used after the log and console file are read,
 * Then if any error happens here not will be reflected in HTML reports
 * 
 * @author caperutxa
 *
 */
public class IterationTestManager {

	InsertIteration iteration;
	
	/**
	 * To register a new iteration we need to insert the iteration
	 * and then link the times to this new iteration inserted (get id)
	 */
	public void registerTestIteration() {
		iteration = new InsertIteration();
		int iterationNumber = 0;
		
		try {
			iteration.connect();
			
			ResultSet rs = iteration.insertNewIteration(Parser.logModel);
			if(rs.next()) {
				iterationNumber = rs.getInt(1);
				Parser.addHtmlConsoleStep(LogLevel.INFO, "New iteration inserted with id : " + iterationNumber);
			}
			
			for(Map.Entry<ETimes, Map<String, Long>> entry : Parser.logModel.getTestTimeList().entrySet()) {
				for(Map.Entry<String, Long> partial : entry.getValue().entrySet()) {
					if(0 < partial.getValue()) {
						try {
							Parser.addHtmlConsoleStep(LogLevel.INFO, "Insert new time iteration : " + iterationNumber + " - " + entry.getKey().name() + " - " + partial.getKey() + " - " + partial.getValue());
							iteration.insertPartialTimes(iterationNumber, entry.getKey().name(), partial.getKey().split("\\(")[0], partial.getValue());
						} catch(com.mysql.cj.jdbc.exceptions.MysqlDataTruncation dt) {
							Parser.addHtmlConsoleStep(LogLevel.DEBUG, "iterationNumber=" + iterationNumber + ", Time type=" + entry.getKey().name() + ", time label = " + partial.getKey() + ", time = " + partial.getValue());
							Parser.logInternaleError(dt);
						}
					}
				}
			}
			
			iteration.close();
		} catch (ClassNotFoundException | SQLException e) {
			Parser.logInternaleError(e);
			System.out.println(e.toString());
			e.printStackTrace();
		}
		
	}
}
