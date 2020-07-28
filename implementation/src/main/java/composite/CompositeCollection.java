package composite;

import iterator.Iterator;

import java.sql.SQLException;

public interface CompositeCollection {
    void add (Object obj) throws SQLException;
    Object getByName(String name);
    void delete(String objName);
    void print();
    Iterator createIterator();
}
