package test;

//import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.junit.jupiter.api.Test;

class DatabaseTest{

	Connection connection;
	Statement statement;


	@Test
	void testDBConnection() throws SQLException {
		

		connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
	    statement = connection.createStatement();
		
		assertNotEquals(null, connection);
		assertNotEquals(null, statement);
		
		
		statement.close();
		connection.close();
		
		assertTrue(connection.isClosed());
		assertTrue(statement.isClosed());
	
	}
	
	@Test
	void testCreateInsertSelect() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
	    statement = connection.createStatement();
	    
	    String userNameInput = "testUserName";
	    String passwordInput = "testPassword";
	    String usernameFromTable = "";
	    String passwordFromTable = "";
	    
	    statement.execute(
				"CREATE TABLE IF NOT EXISTS user_test (username varchar(100) primary key, " + "password varchar(100))");

	    statement.execute("INSERT OR IGNORE INTO user_test " + "VALUES('"+userNameInput+"', '"+passwordInput+"')");
	    String sqlSelect = "SELECT * FROM user_test";
	    
	    
	    ResultSet rs = statement.executeQuery(sqlSelect);

		while (rs.next()) {
			usernameFromTable = rs.getString("username");
			passwordFromTable = rs.getString("password");
		}

		statement.close();
		
	    assertEquals(userNameInput, usernameFromTable);
	    assertEquals(passwordInput, passwordFromTable);
	    assertNotNull(usernameFromTable);
	    assertNotNull(passwordFromTable);
	        		
	}
	
	
	@Test
	void testSelectDelete() throws SQLException {
		connection = DriverManager.getConnection("jdbc:sqlite:.\\testDB.db");
	    statement = connection.createStatement();
	    
	    String userNameInput = "testUserName";
	    String passwordInput = "testPassword";
	    String usernameFromTable = "";
	    String passwordFromTable = "";
	    
	    statement.execute(
				"CREATE TABLE IF NOT EXISTS user_test (username varchar(100) primary key, " + "password varchar(100))");

	    statement.execute("INSERT OR IGNORE INTO user_test " + "VALUES('"+userNameInput+"', '"+passwordInput+"')");
	   
	    
	    statement.execute("DELETE FROM user_test");
	    String selectStatement = "SELECT * FROM user_test";
	    ResultSet rs = statement.executeQuery(selectStatement);
	    usernameFromTable = null;
	    passwordFromTable = null;
	    
	    while (rs.next()) {
			usernameFromTable = rs.getString("username");
			passwordFromTable = rs.getString("password");
		}

		statement.close();
		
	    assertEquals(usernameFromTable, null);
	    assertEquals(passwordFromTable, null);

	}


}


