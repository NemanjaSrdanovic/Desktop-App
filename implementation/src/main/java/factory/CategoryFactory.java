package factory;

import model.Category;
import enums.CategoryType;
import model.Expense;
import model.Income;

/*
 * @nemanja
 * This factory is used only for creating categories
 * By encapsulating the creating of the categories, we only have one place to make modifications if needed
 * 
*/

public class CategoryFactory {
	
	public Category makeCategory(CategoryType categoryType, String categoryName) {
		
		Category newCategory = null;

		switch (categoryType) {
			case INCOME:
				newCategory = new Income(categoryName);
				break;
			case EXPENSE:
				newCategory = new Expense(categoryName);
				break;
		}
		return newCategory;
	}
	
	public Category makeCategory(CategoryType categoryType, String categoryName, double categoryBalance) {
		
		Category newCategory = null;

		switch (categoryType) {
			case INCOME:
				newCategory = new Income(categoryName, categoryBalance);
				break;
			case EXPENSE:
				newCategory = new Expense(categoryName, categoryBalance);
				break;
		}
		return newCategory;
	}

}
