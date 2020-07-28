package database;


import java.sql.SQLException;

public class DatabaseConnection {

  String userName = "";
  String password = "";
  SQLSelect select = new SQLSelect();
  boolean success = false;

  public Boolean logIn(String name, String password) throws SQLException {

    this.userName = name;
    this.password = password;

    success = select.findUser(userName, password);

    return success;
  }

}
