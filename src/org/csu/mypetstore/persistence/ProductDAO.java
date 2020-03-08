package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Product;

import java.util.List;

public interface ProductDAO {
    //大类查找小类
    List<Product> getProductListByCategory(String categoryId);
    //小类查找具体
    Product getProduct(String productId);
    //关键字查找
    List<Product> searchProductList(String keywords);
}
