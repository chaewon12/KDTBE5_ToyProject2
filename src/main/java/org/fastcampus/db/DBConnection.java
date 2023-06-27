package org.fastcampus.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    public static Connection getInstance(){
        String url = "jdbc:mysql://localhost:3306/baseballapp"; //스키마명
        String username = "root";
        String password = "0000"; //개인비밀번호 입력

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);
            System.out.println("DB connection success");
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
