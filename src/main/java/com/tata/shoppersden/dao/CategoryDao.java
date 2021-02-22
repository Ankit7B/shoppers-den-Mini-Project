package com.tata.shoppersden.dao;

import com.tata.shoppersden.models.Category;

public interface CategoryDao {
    public void addCategory(Category category);
    public void removeCategory(int cartegoryId);
    public void viewAllCategory();
}
