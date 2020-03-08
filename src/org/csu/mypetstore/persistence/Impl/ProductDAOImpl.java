package org.csu.mypetstore.persistence.Impl;

import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.persistence.ProductDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOImpl implements ProductDAO {
    //特别注意这里的sql语句，它的顺序与数据库列表的顺序不一致，所以把名字
    public static final String GET_PRODUCT_LIST = "SELECT PRODUCTID, CATEGORY as categoryId, NAME, DESCN as description FROM PRODUCT WHERE CATEGORY = ?";
    public static final String GET_PRODUCT = "SELECT PRODUCTID, CATEGORY as categoryId, NAME, DESCN as description FROM PRODUCT WHERE PRODUCTID = ?";
    public static final String GET_SEARCH_PRODUCT = "select PRODUCTID, NAME, DESCN as description, CATEGORY as categoryId from PRODUCT WHERE lower(name) like ?";

    @Override
    public List<Product> getProductListByCategory(String categoryId) {
        List<Product> productList = new ArrayList<Product>();
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_PRODUCT_LIST);
            preparedStatement.setString(1,categoryId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getString(1));
                product.setCategoryId(resultSet.getString(2));
                product.setName(resultSet.getString(3));
                product.setDescription(resultSet.getString(4));
                //添加到列表中
                productList.add(product);
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    @Override
    public Product getProduct(String productId) {
        Product product = new Product();
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_PRODUCT);
            preparedStatement.setString(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                product.setProductId(resultSet.getString(1));
                product.setCategoryId(resultSet.getString(2));
                product.setName(resultSet.getString(3));
                product.setDescription(resultSet.getString(4));
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return product;
    }

    @Override
    public List<Product> searchProductList(String keywords) {
        List<Product> productList = new ArrayList<Product>();
        //直接查找keywords是全词查找，故以下语句添加%用来模糊查找
        String search = "%" + keywords + "%";
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_SEARCH_PRODUCT);
            preparedStatement.setString(1,search);
            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Product product = new Product();
                product.setProductId(resultSet.getString(1));
                product.setCategoryId(resultSet.getString(4));
                product.setName(resultSet.getString(2));
                product.setDescription(resultSet.getString(3));
                productList.add(product);
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return productList;
    }

    public static void main(String[] args){
        ProductDAOImpl productDAO = new ProductDAOImpl();
        List<Product> productList = productDAO.searchProductList("l");
        System.out.println(productList.size());
    }
}
