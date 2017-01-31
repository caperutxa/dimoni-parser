package caperutxa.dimoni.parser.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import caperutxa.dimoni.parser.Parser;
import caperutxa.dimoni.parser.constants.Constants;
import caperutxa.dimoni.parser.constants.ELogLevel;

/**
 * 
 * @author caperutxa
 *
 */
public class BaseDAO {

	protected Connection connection;
	
	public void connect() throws SQLException, ClassNotFoundException {
		if(null != connection) {
			connection.close();
		}
		
		Class.forName(Constants.JDBC_DRIVER);
		connection = DriverManager.getConnection(
				Constants.CONNECTION_STRING
				, Constants.JDBC_USER
				, Constants.JDBC_PASS);
	}
	
	public void close() throws SQLException {
		if(null == connection) {
			Parser.addInternalParseError(ELogLevel.INTERNAL_ERROR, "BaseDAO.close(). The DB connetion is null");
		} else if(connection.isClosed()) {
			Parser.addInternalParseError(ELogLevel.INTERNAL_ERROR, "BaseDAO.close(). The DB connection is already closed");
		} else {
			connection.close();
		}
	}
	
}
