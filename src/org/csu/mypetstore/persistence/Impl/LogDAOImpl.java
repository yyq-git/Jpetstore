package org.csu.mypetstore.persistence.Impl;

import org.csu.mypetstore.persistence.DBUtil;
import org.csu.mypetstore.persistence.LogDAO;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class LogDAOImpl implements LogDAO {
    public static final String INSERT_LOG = "INSERT INTO log(LOGUSERID,LOGINFO) VALUES(?,?)";

    @Override
    public void insertLog(String username, String logInfo) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_LOG);
            //设置sql语句中的变量
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,logInfo);
            preparedStatement.executeUpdate();

            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
