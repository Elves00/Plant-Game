/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.StringTokenizer;
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
                myUpdate("DROP TABLE Player");
                myUpdate("DROP TABLE Field");
                myUpdate("DROP TABLE Shop");
                myUpdate("DROP TABLE Unlock");

                if (!checkTableExisting("Player")) {
                    System.out.println("CREATING A PLAYER TABLE");
                    //All tabel creation here
                    this.myUpdate("CREATE TABLE " + "Player" + " (slot INT,playerName VARCHAR(20),money FLOAT,energy INT ,day INT,score INT)");
                    //Inserts a player into each save slot.
                    for (int i = 0; i < 6; i++) {
                        myUpdate("INSERT INTO Player VALUES (" + i + ",'Brecon',200,100,0,0)");
                    }

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

                if (!checkTableExisting("Shop")) {
                    System.out.println("CREATING A Shop TABLE");
//                      PreparedStatement pstmt = conn.prepareStatement(" UPDATE CARTABLE SET PRICE=? WHERE MODEL=?");

                    myUpdate("CREATE TABLE Shop (slot INT,name VARCHAR(10))");
                    myUpdate("INSERT INTO Shop VALUES (0,'broccoli'),"
                            + "\n (0,'cabbage'),"
                            + "\n (0,'carrot')");
                    myUpdate("INSERT INTO Shop VALUES (1,'broccoli'),"
                            + "\n (1,'cabbage'),"
                            + "\n (1,'carrot')");
                }

                if (!checkTableExisting("Unlock")) {
                    System.out.println("CREATING A Unlock TABLE");
                    myUpdate("CREATE TABLE Unlock (slot INT,name VARCHAR(10),cost INT)");
                    myUpdate("INSERT INTO Unlock VALUES (3,'tulip',30)");
                    myUpdate("INSERT INTO Unlock VALUES (0,'tulip',30)");

                }

                if (!checkTableExisting("Field")) {
                    System.out.println("CREATING A FIELD TABLE");
//                      PreparedStatement pstmt = conn.prepareStatement(" UPDATE CARTABLE SET PRICE=? WHERE MODEL=?");

                    myUpdate("CREATE TABLE Field (slot INT,x INT,y INT,name VARCHAR(10),growtime INT,timeplanted INT,value INT,growcounter INT, growth INT,waterlimit INT,watercounter INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");
////                    myUpdate("INSERT INTO Field VALUES ");
//                    myUpdate("INSERT INTO Field VALUES"
//                            + "   (1,1,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,1,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,1,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,2,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,2,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,2,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,3,1,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,3,2,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE),"
//                            + "\n (1,3,3,'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE)");

                    //Inserts the inital field values for slots 0,1,2,3,4,5 
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {
                            for (int k = 0; k < 3; k++) {
                                myUpdate("INSERT INTO Field VALUES (" + i + "," + j + "," + k + ",'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE)");
                            }
                        }
                    }

                }

                if (!checkTableExisting("Save")) {
                    System.out.println("CREATING A SAVE TABLE");
                    myUpdate("CREATE TABLE Save (slot INT)");
                    myUpdate("INSERT INTO Save VALUES (1)");
                }

            } catch (Throwable e) {
                System.out.println("error" + e);

            }
        }
    }

    /**
     *
     * @param name
     * @return
     */
    public Data newGame(String name) {
        Data data = new Data();
        data.setPlayerName(name);
        if (!checkTableExisting("Player")) {
            System.out.println("THE PLAYER DOESNT EXIST UH OH");
        }
        //Set details for shop 
        data = selectShop(0, data);
        myUpdate("INSERT INTO Player VALUES (1,'" + name + "',200,100,0,0)");

        return data;
    }

    public Data loadGame(int selection) {
        if (this.conn != null) {
            System.out.println("ITS NOT LOADED");
        }
        try {
            System.out.println("SELECTION" + selection);

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

            data = selectShop(selection, data);

            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Data selectShop(int selection, Data data) {
        try {
            System.out.println("SELECTION" + selection);
            String sql = "SELECT * FROM Shop WHERE slot=" + selection;
//                    WHERE slot=" + selection+"";
            ResultSet rs;
            rs = this.myQuery(sql);
            String shop = "";
            while (rs.next()) {
                shop += rs.getString("name") + " ";
            }
            data.setShop(shop);
            System.out.println("shop:" + shop);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("ERROR REACHED HERE");
        return null;
    }

    public void updateShop(int selection, String plant) {
        System.out.println("Inserting " + plant + " into save slot " + selection);
        String sql = "INSERT INTO Shop VALUES(" + selection + ",'" + plant + "'  )";

        myUpdate(sql);

    }

    public Data selectUnlockShop(int slot, Data data) {
        try {
            System.out.println("Unlock " + slot);

            String sql = "SELECT * FROM Unlock WHERE slot=" + slot;
//                    WHERE slot=" + selection+"";
            ResultSet rs;
            rs = this.myQuery(sql);
            String unlock = "";
            String unlockCost = "";
            while (rs.next()) {
                unlock += rs.getString("name");
                unlockCost += rs.getInt("cost");

            }

            data.setUnlock(unlock);
            data.setUnlockCost(unlockCost);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Data updateUnlock(int selection, String plant, Data data) {
        System.out.println("Deleting " + plant + " from save slot " + selection);
        String sql = "DELETE FROM Unlock WHERE slot=" + selection + " AND  name ='" + plant + "'";
        myUpdate(sql);

        data = selectUnlockShop(selection, data);
        return data;
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

    public void saveGame(int slot, Data data) {
        savePlayer(slot, data);
        saveUnlock(slot, data);
        saveShop(slot, data);

    }

    public void savePlayer(int slot, Data data) {

        try {

            String sql = "update Player set playerName=? ,money=? , energy =? , day= ? , score =?  where slot=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setString(1, data.getPlayerName());
            preparedStatement.setFloat(2, data.getMoney());
            preparedStatement.setInt(3, data.getEnergy());
            preparedStatement.setInt(4, data.getDay());
            preparedStatement.setInt(5, data.getScore());
            preparedStatement.setInt(6, slot);

            //Print the number of rows affected by update
            System.out.println(preparedStatement.executeUpdate());

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void saveUnlock(int slot, Data data) {
//        String sql = "update Unlock set name =? , cost =? where slot=?";
    }

    public void saveShop(int slot, Data data) {
        try {
            String sql = "DELETE FROM Shop where slot=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, slot);
            preparedStatement.executeUpdate();

            StringTokenizer st = new StringTokenizer(data.getShop());
             sql = "INSERT INTO SHOP (slot , name) VALUES(?,?)";
                preparedStatement = conn.prepareStatement(sql);
             
            while (st.hasMoreTokens()) {
                preparedStatement.setInt(1, slot);
                preparedStatement.setString(2, st.nextToken());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String check = "Select * From Shop Where slot="+slot; 
         ResultSet rs;
            rs = this.myQuery(check);
            String unlock = "";
            
         
        try {
            while (rs.next()) {
                unlock += rs.getString("name");
               
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println(unlock);
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
