package iterator;

import model.Category;

import java.util.List;

public class CategoryCollectionIterator implements Iterator {

  private final List<Category> categoryList;
  private int index = 0;

  public CategoryCollectionIterator(List<Category> categoryList) {
    this.categoryList = categoryList;
  }

  @Override
  public boolean hasNext() {
    return index < categoryList.size();
  }

  @Override
  public Category next() {
    Category category = categoryList.get(index);
    ++index;
    return category;
  }
}
