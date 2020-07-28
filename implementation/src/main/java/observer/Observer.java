package observer;

import java.sql.SQLException;

import composite.CompositeCollection;

public interface Observer {

  void update(CompositeCollection accounts,CompositeCollection categories) throws SQLException;
}
