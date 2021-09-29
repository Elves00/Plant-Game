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
                    this.myUpdate("CREATE TABLE " + "Player" + " (slot INT,playerName VARCHAR(20),money FLOAT,energy INT ,day INT,score INT)");
//                    myUpdate("INSERT INTO Player VALUES (1,'Brecon',200,100,2,0)");
                }

                if (!checkTableExisting("Plant")) {
                    System.out.println("CREATING A PLANT TABLE");
                    myUpdate("CREATE TABLE PLANT (name VARCHAR(10),growtime INT,timeplanted INT,value INT,growcounter INT, growth INT,waterlimit INT,watercounter INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");

                    myUpdate("INSERT INTO Plant VALUES"
                            + "   ('broccoli',3,0,0,6,0,3,0,10,FALSE,FALSE),"
                            + "\n ('cabbage',4,0,0,4,0,2,0,10,FALSE,FALSE),"
                            + "\n ('carrot',2,0,0,3,0,2,0,10,FALSE,FALSE),"
                            + "\n ('dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n ('saffron',2,0,0,2,0,3,0,10,TRUE,FALSE),"
                            + "\n ('truffle',10,0,0,4,0,1,0,10,FALSE,FALSE),"
                            + "\n ('tulip',10,0,0,4,0,1,0,10,FALSE,FALSE)");
                }

                if (!checkTableExisting("Field")) {
                    System.out.println("CREATING A FIELD TABLE");
                    myUpdate("CREATE TABLE Field (slot INT,x INT,y INT,name VARCHAR(10),growtime INT,timeplanted INT,value INT,growcounter INT, growth INT,waterlimit INT,watercounter INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");
//                    myUpdate("INSERT INTO Field VALUES ");
                    myUpdate("INSERT INTO Field VALUES"
                            + "   (1,1,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,1,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,1,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,2,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,2,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,2,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,3,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,3,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
                            + "\n (1,3,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE)");
                }

                if (!checkTableExisting("Save")) {
                    System.out.println("CREATING A SAVE TABLE");
                    myUpdate("CREATE TABLE Save (slot INT)");
                    myUpdate("INSERT INTO Save VALUES (1)");
                }

//                //drop tables
//                myUpdate("DROP TABLE Plant");
//                myUpdate("DROP TABLE Field");
//                myUpdate("Drop Table Player");
//                myUpdate("Drop Table Save");
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

    public Data newGame(String name) {
        Data data = new Data();
        data.setPlayerName(name);
        if (!checkTableExisting("Player")) {
            System.out.println("THE PLAYER DOESNT EXIST UH OH");
        }

        myUpdate("INSERT INTO Player VALUES (1,'" + name + "',200,100,0,0)");

        return data;
    }

    public Data loadGame(int selection) {
        try {
            Data data = new Data();
            String sql = "SELECT * FROM Player WHERE slot =" + selection + "";
            ResultSet rs = this.myQuery(sql);
            while (rs.next()) {
                data.setPlayerName(rs.getString("playerName"));
                data.setMoney(rs.getFloat("money"));
                data.setEnergy(rs.getInt("energy"));
                data.setDay(rs.getInt("day"));
                data.setScore(rs.getInt("score"));

            }
            sql = "SELECT * FROM Field WHERE slot=" + selection + "";
            rs = this.myQuery(sql);
            String[][] plants = new String[3][3];
            String[][] plantsDescription = new String[3][3];
            int i = 0;
            int j = 0;
            while (rs.next()) {
                plants[i][j] = rs.getString("name");
                plantsDescription[i][j] = rs.getString("growtime") + " " + rs.getString("timePlanted") + " " + rs.getString("value") + " " + rs.getString("growcounter") + " " + rs.getString("growth") + " " + rs.getString("waterLimit") + " " + rs.getString("watercounter") + " " + rs.getString("price") + " " + rs.getString("pollinator");

                i++;
                if (i == 3) {
                    i = 0;
                    j++;
                }
            }

            for (int k = 0; k < 3; k++) {
                for (int l = 0; l < 3; l++) {
                    System.out.println(plants[k][l]);
                    System.out.println(plantsDescription[k][l]);
                }
            }
            
            data.setPlants(plants);
            data.setPlantsDescription(plantsDescription);

            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void updateField(int i, int j, String[][] field) {
        for (int k = 0; k < 3; k++) {
            for (int l = 0; l < 3; l++) {
                String sql = "UPDATE Field "
                        + "SET name = " + field[k][l] + " WHERE id in (" + i + "," + j + ")";
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
        db.loadGame(1);

    }
}
