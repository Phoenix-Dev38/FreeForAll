package com.github.phoenix_dev38.ffa;

import java.sql.*;

public class MySQL {

    private final String host;
    private final String database;
    private final String user;
    private final String password;

    private static Connection con;

    public MySQL(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;

        connect();
    }

    public void connect() {
        try {
            con = DriverManager.getConnection("jdbc:mysql://" + host + ":3306/" + database + "?autoReconnect=true&characterEncoding=utf8&useSSL=false&useUnicode=true", user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close() {
        try {
            if (con != null)
                con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(String qry) {
        try {
            Statement st = con.createStatement();
            st.executeUpdate(qry);
            st.close();
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
    }

    public ResultSet query(String qry) {
        ResultSet rs = null;

        try {
            Statement st = con.createStatement();
            rs = st.executeQuery(qry);
        } catch (SQLException e) {
            connect();
            System.err.println(e);
        }
        return rs;
    }
}