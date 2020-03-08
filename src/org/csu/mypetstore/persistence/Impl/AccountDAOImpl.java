package org.csu.mypetstore.persistence.Impl;

import org.csu.mypetstore.domain.Account;
import org.csu.mypetstore.persistence.AccountDAO;
import org.csu.mypetstore.persistence.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AccountDAOImpl implements AccountDAO {
    private static final String GET_ACCOUNT_BY_USERNAME = "SELECT SIGNON.USERNAME, ACCOUNT.EMAIL, ACCOUNT.FIRSTNAME, ACCOUNT.LASTNAME, ACCOUNT.STATUS, ACCOUNT.ADDR1 AS address1, ACCOUNT.ADDR2 AS address2,\n" +
            "          ACCOUNT.CITY, ACCOUNT.STATE, ACCOUNT.ZIP, ACCOUNT.COUNTRY, ACCOUNT.PHONE, PROFILE.LANGPREF AS languagePreference, PROFILE.FAVCATEGORY AS favouriteCategoryId, PROFILE.MYLISTOPT AS listOption,\n" +
            "          PROFILE.BANNEROPT AS bannerOption, BANNERDATA.BANNERNAME FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA WHERE ACCOUNT.USERID = ? AND SIGNON.USERNAME = ACCOUNT.USERID AND PROFILE.USERID = ACCOUNT.USERID AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY";
    private static final String GET_ACCOUNT_BY_USERNAME_AND_PASSWORD = "SELECT SIGNON.USERNAME, ACCOUNT.EMAIL, ACCOUNT.FIRSTNAME, ACCOUNT.LASTNAME, ACCOUNT.STATUS, ACCOUNT.ADDR1 AS address1, ACCOUNT.ADDR2 AS address2,\n" +
            "      ACCOUNT.CITY, ACCOUNT.STATE, ACCOUNT.ZIP, ACCOUNT.COUNTRY, ACCOUNT.PHONE, PROFILE.LANGPREF AS languagePreference, PROFILE.FAVCATEGORY AS favouriteCategoryId, PROFILE.MYLISTOPT AS listOption,\n" +
            "      PROFILE.BANNEROPT AS bannerOption, BANNERDATA.BANNERNAME FROM ACCOUNT, PROFILE, SIGNON, BANNERDATA WHERE ACCOUNT.USERID = ? AND SIGNON.PASSWORD = ? AND SIGNON.USERNAME = ACCOUNT.USERID\n" +
            "      AND PROFILE.USERID = ACCOUNT.USERID AND PROFILE.FAVCATEGORY = BANNERDATA.FAVCATEGORY";
    private static final String UPDATE_ACCOUNT = "UPDATE ACCOUNT SET EMAIL = ?, FIRSTNAME = ?, LASTNAME = ?, STATUS = ?, ADDR1 = ?, ADDR2 = ?, CITY = ?, STATE = ?, ZIP = ?, ?, PHONE = ? WHERE USERID = ?";
    private static final String UPDATE_PROFILE = "UPDATE PROFILE SET LANGPREF = ?, FAVCATEGORY = ? WHERE USERID = ?";
    private static final String UPDATE_SIGNON = "UPDATE SIGNON SET PASSWORD = ? WHERE USERNAME = ?";
    private static final String INSERT_ACCOUNT = "INSERT INTO ACCOUNT (EMAIL, FIRSTNAME, LASTNAME, STATUS, ADDR1, ADDR2, CITY, STATE, ZIP, COUNTRY, PHONE, USERID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String INSERT_PROFILE = "INSERT INTO PROFILE (LANGPREF, FAVCATEGORY, MYLISTOPT, BANNEROPT, USERID)  VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_SIGNON = "INSERT INTO SIGNON (PASSWORD,USERNAME) VALUES (?, ?)";
    @Override
    public Account getAccountByUsername(String username) {
        Account account = new Account();
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_ACCOUNT_BY_USERNAME);
            preparedStatement.setString(1,username);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                account.setUsername(resultSet.getString(1));
                account.setEmail(resultSet.getString(2));
                account.setFirstName(resultSet.getString(3));
                account.setLastName(resultSet.getString(4));
                account.setStatus(resultSet.getString(5));
                account.setAddress1(resultSet.getString(6));
                account.setAddress2(resultSet.getString(7));
                account.setCity(resultSet.getString(8));
                account.setState(resultSet.getString(9));
                account.setZip(resultSet.getString(10));
                account.setCountry(resultSet.getString(11));
                account.setPhone(resultSet.getString(12));
                account.setLanguagePreference(resultSet.getString(13));
                account.setFavouriteCategoryId(resultSet.getString(14));
                account.setListOption(resultSet.getBoolean(15));
                account.setBannerOption(resultSet.getBoolean(16));
                account.setBannerName(resultSet.getString(17));
            }else{
                return null;
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return account;
    }
    @Override
    public Account getAccountByUsernameAndPassword(Account account) {
        Account myAccount = null;
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(GET_ACCOUNT_BY_USERNAME_AND_PASSWORD);
            preparedStatement.setString(1,account.getUsername());
            preparedStatement.setString(2,account.getPassword());
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                myAccount = new Account();
                myAccount.setUsername(resultSet.getString(1));
                myAccount.setEmail(resultSet.getString(2));
                myAccount.setFirstName(resultSet.getString(3));
                myAccount.setLastName(resultSet.getString(4));
                myAccount.setStatus(resultSet.getString(5));
                myAccount.setAddress1(resultSet.getString(6));
                myAccount.setAddress2(resultSet.getString(7));
                myAccount.setCity(resultSet.getString(8));
                myAccount.setState(resultSet.getString(9));
                myAccount.setZip(resultSet.getString(10));
                myAccount.setCountry(resultSet.getString(11));
                myAccount.setPhone(resultSet.getString(12));
                myAccount.setLanguagePreference(resultSet.getString(13));
                myAccount.setFavouriteCategoryId(resultSet.getString(14));
                myAccount.setListOption(resultSet.getBoolean(15));
                myAccount.setBannerOption(resultSet.getBoolean(16));
                myAccount.setBannerName(resultSet.getString(17));
            }
            DBUtil.closeAll(conn,preparedStatement,resultSet);
        }catch (Exception e){
            e.printStackTrace();
        }
        return myAccount;
    }

    @Override
    public void insertAccount(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_ACCOUNT);
            preparedStatement.setString(1,account.getEmail());
            preparedStatement.setString(2,account.getFirstName());
            preparedStatement.setString(3,account.getLastName());
            preparedStatement.setString(4,account.getStatus());
            preparedStatement.setString(5,account.getAddress1());
            preparedStatement.setString(6,account.getAddress2());
            preparedStatement.setString(7,account.getCity());
            preparedStatement.setString(8,account.getState());
            preparedStatement.setString(9,account.getZip());
            preparedStatement.setString(10,account.getCountry());
            preparedStatement.setString(11,account.getPhone());
            preparedStatement.setString(12,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertProfile(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_PROFILE);
            preparedStatement.setString(1,account.getLanguagePreference());
            preparedStatement.setString(2,account.getFavouriteCategoryId());
            preparedStatement.setBoolean(3,account.isListOption());
            preparedStatement.setBoolean(4,account.isBannerOption());
            preparedStatement.setString(5,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void insertSignon(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(INSERT_SIGNON);
            preparedStatement.setString(1,account.getPassword());
            preparedStatement.setString(2,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateAccount(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_ACCOUNT);
            preparedStatement.setString(1,account.getEmail());
            preparedStatement.setString(2,account.getFirstName());
            preparedStatement.setString(3,account.getLastName());
            preparedStatement.setString(4,account.getStatus());
            preparedStatement.setString(5,account.getAddress1());
            preparedStatement.setString(6,account.getAddress2());
            preparedStatement.setString(7,account.getCity());
            preparedStatement.setString(8,account.getState());
            preparedStatement.setString(9,account.getZip());
            preparedStatement.setString(10,account.getCountry());
            preparedStatement.setString(11,account.getPhone());
            preparedStatement.setString(12,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateProfile(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_PROFILE);
            preparedStatement.setString(1,account.getLanguagePreference());
            preparedStatement.setString(2,account.getFavouriteCategoryId());
            preparedStatement.setString(3,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void updateSignon(Account account) {
        try{
            Connection conn = DBUtil.getconn();
            PreparedStatement preparedStatement = conn.prepareStatement(UPDATE_SIGNON);
            preparedStatement.setString(1,account.getPassword());
            preparedStatement.setString(2,account.getUsername());
            preparedStatement.executeUpdate();
            conn.close();
            preparedStatement.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        AccountDAOImpl accountDAO  = new AccountDAOImpl();
        Account account = new Account();
        account.setUsername("123");
        account.setPassword("123");
        Account account1 = accountDAO.getAccountByUsernameAndPassword(account);
        System.out.println(account1);
    }
}
