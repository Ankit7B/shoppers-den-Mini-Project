package com.tata.shoppersden.dao;

import com.tata.shoppersden.helpers.PostgresConnectionHelper;
import com.tata.shoppersden.models.Category;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ResourceBundle;

public class CategoryDaoImpl implements CategoryDao {
    private Connection conn;
    private ResourceBundle resourceBundle;
    private PreparedStatement addCategoryPre, removeCategoryPre;
    private Statement viewAllCategoryStatement;
    private Category category;

    public CategoryDaoImpl() {
        conn = PostgresConnectionHelper.getConnection();
        resourceBundle = ResourceBundle.getBundle("db");
    }

    @Override
    public void addCategory(Category category) {

    }

    @Override
    public void removeCategory(int categoryId) {

    }

    @Override
    public void viewAllCategory() {

    }
}
