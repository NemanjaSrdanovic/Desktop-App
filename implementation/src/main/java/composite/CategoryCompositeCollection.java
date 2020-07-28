package composite;

import enums.AccountType;
import enums.CategoryType;
import factory.CategoryFactory;
import iterator.CategoryCollectionIterator;
import iterator.Iterator;
import model.Account;
import model.Category;
import model.CategoryException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DriverClass;

public class CategoryCompositeCollection implements CompositeCollection {
  final List<Category> categories;

  public CategoryCompositeCollection() {
    this.categories = new ArrayList<>();
  }

  public void addCategory(CategoryType categoryType, String categoryName) {
    try {
      if (getByName(categoryName) != null) {
        throw new CategoryException("Category " + categoryName + " already exists");
      }else {
        Category category;
        CategoryFactory factory = new CategoryFactory();
        category = factory.makeCategory(categoryType, categoryName);
        if(category != null) { categories.add(category); }
      }

    } catch (CategoryException e) {
      System.err.println(e.getMessage());
    }
  }

  public List<Category> getCategories() throws SQLException {
	  
	  List<Category> categories = null;
		DriverClass database;

			database = new DriverClass();
		
			categories = database.findAllUserCategories();

		return categories;
  }
  
	public double getAllBalanceByType(CategoryType type) throws SQLException {
		
		double balance=0.0;
		
		for(Category c: getCategories()) {
			if(c.getCategoryType().equals(type)) {balance+=c.getCategoryBalance();}
		}
		
		return balance;
	}

  @Override
  public void add(Object category) throws SQLException {

    Category newCategory = (Category) category;

    boolean helper = false;

    for(Category c: getCategories()){
        if(c.getCategoryName().equals(newCategory.getCategoryName())) helper = true;
    }

    try {
      if (helper) {
        throw new CategoryException("Category" + newCategory.getCategoryName() + " already exists");

      }
      else{
      categories.add(newCategory);}
    } catch (CategoryException e) {
      System.err.println(e.getMessage());
    }

	  DriverClass database;
		try {
			database = new DriverClass();
			database.insertCategoryTable(((Category) category).getCategoryType(), ((Category) category).getCategoryName() );
		} catch (SQLException e) {
			e.printStackTrace();
		}
  }

  @Override
  public Category getByName(String categoryName) {
	  
	 Category category = null;

		DriverClass database;
		database = new DriverClass();
		try {
			category = database.findCategoryByName(categoryName);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return category;
  }

  @Override
  public void delete(String categoryName) {
	  
	  if(getByName(categoryName) != null) {
			DriverClass database;
			database = new DriverClass();
			try {
				database.DeleteCategoryByName(categoryName);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else { throw new
			  IllegalArgumentException(categoryName + " does not exist"); 
		}
  }

  @Override
  public void print() {
	  this.categories.clear();
		 
	  try {		  
		  DriverClass database = new DriverClass();
		  for(Category c : database.findAllUserCategories()) {
			  this.categories.add(c);
		  }
		 }
		 catch(SQLException e) {
			 e.getMessage();
		 }

    for(Category category : categories) {
      System.out.println(category.toString());
    }
  }

  @Override
  public Iterator createIterator() {
    return new CategoryCollectionIterator(this.categories);
  }
}
