package wgu.c195pa.dao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import wgu.c195pa.helper.JDBC;
import wgu.c195pa.model.Users;

import java.sql.*;

//import static wgu.c195pa.helper.JDBC.connection;

public class UserDatabaseAccess {
    public static ObservableList<Users> getUsers() throws SQLException {
        ObservableList<Users> userList = FXCollections.observableArrayList();
        String sql = "Select * from users";
        PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while(rs.next()){
            int userId = rs.getInt("User_ID");
            String userName = rs.getString("User_Name");
            String userPassword = rs.getString("Password");
            Date createdDate = rs.getDate("Create_Date");
            String createdBy = rs.getString("Created_By");
            Date lastUpdated = rs.getDate("Last_Update");
            String lastUpdatedBy = rs.getString("Last_Updated_By");
            Users User = new Users(userId, userName, userPassword, createdDate, createdBy, lastUpdated, lastUpdatedBy);
            userList.add(User);}
        return userList;
    }
    public static ObservableList<Integer> getUserIDList() throws SQLException {
        ObservableList<Integer> userIDList = FXCollections.observableArrayList();
        String query = "SELECT User_ID FROM users";
        Statement statement = JDBC.getConnection().createStatement();
        ResultSet result = statement.executeQuery(query);

        while (result.next()) {
            Integer userID = result.getInt("User_ID");
            userIDList.add(userID);
        }
        return userIDList;
    }
}
