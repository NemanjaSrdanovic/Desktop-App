package model;

import enums.CategoryType;

public interface Category {
  CategoryType getCategoryType();
  String getCategoryName();
  String toString();
  double getCategoryBalance();
  void setCategoryBalance(double amount);
}
