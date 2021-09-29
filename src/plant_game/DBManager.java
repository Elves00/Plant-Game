/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public final class DBManager {

    //Server password and username
    private static final String USER_NAME = "pdc";
    private static final String PASSWORD = "pdc";
    //Embeded design
    private static final String URL = "jdbc:derby:PlantDB; create=true";
    Connection conn;

    /**
     * Establishes a connection to the DataBase
     */
    public DBManager() {
        establishConnection();
    }

    /**
     * Create the method
     */
    public void dbsetup() {

        System.out.println(conn);
        if (this.conn != null) {
            System.out.println("Creating the tables");
            try {
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

                if (!checkTableExisting("Player")) {
                    System.out.println("CREATING A PLAYER TABLE");
                    //All tabel creation here
                    this.myUpdate("CREATE TABLE " + "Player" + " (name VARCHAR(20),money FLOAT,energy INT ,day INT,score INT)");

                }

                if (!checkTableExisting("Plant")) {
                    System.out.println("CREATING A PLANT TABLE");
                    myUpdate("CREATE TABLE PLANT (name VARCHAR(10),growtime INT,timeplanted INT,value INT,growth INT, growcounter INT,watercounter INT,waterlimit INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");

                    myUpdate("INSERT INTO Plant VALUES('dirt','0','0','0','0','0','10','10','FALSE','FALSE')");
                }

//                  if (!checkTableExisting("PlantSet")) {
//                    System.out.println("CREATING A PLANTSET TABLE");
//                    myUpdate("CREATE TABLE PLANT (name VARCHAR(10),growtime INT,timeplanted INT,value INT,growth INT, growcounter INT,watercounter INT,waterlimit INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");
//
//                }
            } catch (Throwable e) {
                System.out.println("error" + e);

            }
        }
    }

    /**
     *
     * @return
     */
    public Connection getConnection() {
        return this.conn;
    }

    public void establishConnection() {
        //If there is no existing connection try connect
        if (this.conn == null) {
            try {
                System.out.println("SHOULD ESTABLISH HERE");
                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
                //System.out.println(URL + "   CONNECTED....");
            } catch (SQLException ex) {
//                Logger.getLogger(H02_DBOperations.class.getName()).log(Level.SEVERE, null, ex);

            }
        }
        System.out.println(conn);
    }

    public void closeConnections() {
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
//                FLogger.getLogger(H02_DBOperations.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public ResultSet myQuery(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void myUpdate(String sql) {

        Connection connection = this.conn;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            statement = connection.createStatement();
            statement.executeUpdate(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean checkTableExisting(String newTableName) {
        boolean flag = false;
        try {

            System.out.println("check existing tables.... ");
            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {
                    System.out.println(tableName + "  IS THERE");
                    flag = true;
                }
            }
            if (rsDBMeta != null) {
                rsDBMeta.close();
            }
        } catch (SQLException ex) {
        }
        return flag;
    }

    public static void main(String[] args) {
        //Call db setup
        DBManager db = new DBManager();
        db.dbsetup();

    }
}
