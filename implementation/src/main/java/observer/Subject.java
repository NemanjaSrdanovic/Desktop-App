package observer;

import java.sql.SQLException;

public interface Subject {

  void registerObserver(Observer o) throws SQLException;

  void removeObserver(Observer o) throws SQLException;

  void notifyObservers() throws SQLException;

}
