package org.csu.mypetstore.persistence.Impl;

import org.csu.mypetstore.domain.Item;
import org.csu.mypetstore.domain.Product;
import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.persistence.ItemDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemDAOImpl implements ItemDAO {
    private static final String GET_ITEM_LIST = "SELECT I.ITEMID, LISTPRICE, UNITCOST, SUPPLIER AS supplierId, I.PRODUCTID AS \"product.productId\", NAME AS \"product.name\", DESCN AS \"product.description\", CATEGORY AS \"product.categoryId\",STATUS,ATTR1 AS attribute1,ATTR2 AS attribute2,ATTR3 AS attribute3,ATTR4 AS attribute4,ATTR5 AS attribute5 FROM ITEM I, PRODUCT P WHERE P.PRODUCTID = I.PRODUCTID AND I.PRODUCTID = ?";
    private static final String GET_ITEM = "select I.ITEMID, LISTPRICE, UNITCOST, SUPPLIER AS supplierId, I.PRODUCTID AS \"product.productId\", NAME AS \"product.name\", DESCN AS \"product.description\", CATEGORY AS \"product.categoryId\", STATUS, ATTR1 AS attribute1, ATTR2 AS attribute2, ATTR3 AS attribute3, ATTR4 AS attribute4,ATTR5 AS attribute5, QTY AS quantity from ITEM I, INVENTORY V, PRODUCT P where P.PRODUCTID = I.PRODUCTID and I.ITEMID = V.ITEMID and I.ITEMID = ?";
    private static final String GET_QTY = "SELECT QTY AS value FROM INVENTORY WHERE ITEMID = ?";
    private static final String UPDATE_QTY = "UPDATE INVENTORY SET QTY = QTY - ? WHERE ITEMID = ? ";

    @Override
    public void updateInventoryQuantity(Map<String, Object> param) {
        try {
            Connection connection = DBUtil.getconn();
            PreparedStatement pStatement = connection.prepareStatement(UPDATE_QTY);
            String itemId = param.keySet().iterator().next();
            Integer increment = (Integer)param.get(itemId);
            pStatement.setInt(1, increment.intValue());
            pStatement.setString(2, itemId);
            pStatement.executeUpdate();
            connection.close();
            pStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getInventoryQuantity(String itemId) {
        int qty = 0;
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_QTY);
            preparedStatement.setString(1,itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                qty = resultSet.getInt(1);
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch(Exception e){
            e.printStackTrace();
        }
        return qty;
    }

    @Override
    public List<Item> getItemListByProduct(String productId) {
        List<Item> itemList = new ArrayList<Item>();
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_ITEM_LIST);
            preparedStatement.setString(1,productId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while(resultSet.next()){
                Item item = new Item();
                item.setItemId(resultSet.getString(1));
                item.setListPrice(resultSet.getBigDecimal(2));
                item.setUnitCost(resultSet.getBigDecimal(3));
                item.setSupplierId(resultSet.getInt(4));
                item.setProductId(resultSet.getString(5));
                Product product = new Product();
                product.setProductId(resultSet.getString(5));
                product.setName(resultSet.getString(6));
                product.setDescription(resultSet.getString(7));
                product.setCategoryId(resultSet.getString(8));
                //不要忘记这个
                item.setProduct(product);
                item.setStatus(resultSet.getString(9));
                item.setAttribute1(resultSet.getString(10));
                item.setAttribute2(resultSet.getString(11));
                item.setAttribute3(resultSet.getString(12));
                item.setAttribute4(resultSet.getString(13));
                item.setAttribute5(resultSet.getString(14));
                itemList.add(item);
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return itemList;
    }

    @Override
    public Item getItem(String itemId) {
        Item item = new Item();
        try {
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_ITEM);
            preparedStatement.setString(1,itemId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                item.setItemId(resultSet.getString(1));
                item.setListPrice(resultSet.getBigDecimal(2));
                item.setUnitCost(resultSet.getBigDecimal(3));
                item.setSupplierId(resultSet.getInt(4));
                item.setProductId(resultSet.getString(5));
                Product product = new Product();
                product.setProductId(resultSet.getString(5));
                product.setName(resultSet.getString(6));
                product.setDescription(resultSet.getString(7));
                product.setCategoryId(resultSet.getString(8));
                //不要忘记这个
                item.setProduct(product);
                item.setStatus(resultSet.getString(9));
                item.setAttribute1(resultSet.getString(10));
                item.setAttribute2(resultSet.getString(11));
                item.setAttribute3(resultSet.getString(12));
                item.setAttribute4(resultSet.getString(13));
                item.setAttribute5(resultSet.getString(14));
                item.setQuantity(resultSet.getInt(15));

            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return item;
    }
    public static void main(String[] args){
        ItemDAOImpl itemImpl = new ItemDAOImpl();
        List<Item> itemList = itemImpl.getItemListByProduct("AV-CB-01");
//        Item item = itemImpl.getItem("EST-1");
        System.out.println(itemList);
    }

}
