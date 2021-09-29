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

                    myUpdate("INSERT INTO Plant VALUES"
                            + " ('broccoli',3,0,0,0,6,0,3,10,FALSE,FALSE),"
                            + "\n ('cabbage',4,0,0,0,4,0,2,10,FALSE,FALSE),"
                            + "\n ('carrot',2,0,0,0,3,0,2,10,FALSE,FALSE),"
                            + "\n ('dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n ('saffron',2,0,0,0,2,0,3,10,TRUE,FALSE),"
                            + "\n ('truffle',10,0,0,0,4,0,1,10,FALSE,FALSE),"
                            + "\n ('tulip',10,0,0,0,4,0,1,10,FALSE,FALSE)");
                }

                if (!checkTableExisting("Field")) {
                    System.out.println("CREATING A FIELD TABLE");
                    myUpdate("CREATE TABLE Field (x INT,y INT,name VARCHAR(10),growtime INT,timeplanted INT,value INT,growth INT, growcounter INT,watercounter INT,waterlimit INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");

//                    myUpdate("INSERT INTO Field VALUES ");
                    myUpdate("INSERT INTO Field VALUES"
                            + "(1,1,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (1,2,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (1,3,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (2,1,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (2,2,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (2,3,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (3,1,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (3,2,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE),"
                            + "\n (3,3,'dirt',0,0,0,0,0,0,10,10,FALSE,FALSE)");
                }

                //drop tables
                myUpdate("DROP TABLE Plant");
                myUpdate("DROP TABLE Field");
//                
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

    public void updateField(int i, int j, String[][] field) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                String sql = "UPDATE Field "
                        + "SET name = "+field[k][l]+" WHERE id in (" + i + "," + j + ")";
                myUpdate(sql);
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
