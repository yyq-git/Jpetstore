package org.csu.mypetstore.persistence.Impl;

import org.csu.mypetstore.domain.Category;
import org.csu.mypetstore.persistence.CategoryDAO;
import org.csu.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAOImpl implements CategoryDAO {
    private static final String GET_CATAGORY_LIST = "SELECT CATID AS categoryId, NAME, DESCN AS description FROM CATEGORY";
    private static final String GET_CATAGORY = "SELECT CATID AS categoryId, NAME, DESCN AS description FROM CATEGORY WHERE CATID = ?";

    @Override
    public List<Category> getCategoryList() {
        List<Category> catagoryList = new ArrayList<Category>();
        try{
            //连接数据库并将查到的数据保存到categoryList中
            Connection conn = DBUtil.getconn();//开路
            PreparedStatement preparedStatement = conn.prepareStatement(GET_CATAGORY_LIST);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Category category = new Category();
                //与数据库中的列一一对应，注意类型
                category.setCategoryId(resultSet.getString(1));
                category.setName(resultSet.getString(2));
                category.setDescription(resultSet.getString(3));
                catagoryList.add(category);
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return catagoryList;
    }

    @Override
    public Category getCategory(String categoryId) {
        Category category = null;
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_CATAGORY);
            preparedStatement.setString(1,categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                category = new Category();
                category.setCategoryId(resultSet.getString(1));
                category.setName(resultSet.getString(2));
                category.setDescription(resultSet.getString(3));
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return category;
    }
}
