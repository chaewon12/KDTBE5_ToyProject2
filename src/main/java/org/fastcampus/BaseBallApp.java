package org.fastcampus;

import org.fastcampus.db.DBConnection;

public class BaseBallApp {
    public static void main(String[] args) {
        DBConnection.getInstance();
    }
}
