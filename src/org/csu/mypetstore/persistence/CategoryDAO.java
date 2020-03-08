package org.csu.mypetstore.persistence;

import org.csu.mypetstore.domain.Category;

import java.util.List;

public interface CategoryDAO {
    //获得所有的大类
    List<Category> getCategoryList();
    //选择某一个确定的类
    Category getCategory(String categoryId);

}
