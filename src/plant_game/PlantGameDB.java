/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public class PlantGameDB {

    private final DBManager dbManager;
    private final Connection conn;
    private Statement statement;

    public PlantGameDB() {
        dbManager = new DBManager();
        conn = dbManager.getConnection();
    }

    public void connectPlantGameDB() {
        //Use the conn to intilise this
    }

    /**
     * Creates a table representing the players plant field
     */
    public void createFieldTable() {
//growTime,timePlanted,value,growth,growCounter,waterCount,waterLimit,price
        String newTable = "FIELD";
        //Drop if exists
        dropIfExists(newTable);
        String sqlCreateTable = "CREATE TABLE " + newTable + " (POSITION INT,ID INT)";
        String insertData = "INSERT INTO FIELD VALUES()";
        //So need to pass in plant ids.....
    }

    public void createPlayerTable() {
        //growTime,timePlanted,value,growth,growCounter,waterCount,waterLimit,price
        String newTable = "PLAYER";
        //Drop if exists
        dropIfExists(newTable);
        String sqlCreateTable = "CREATE TABLE " + newTable + " (NAME VARCHAR (20),MONEY FLOAT, ENERGY INT, DAY INT,----FIELD----,SCORE INT)";
        String insertData = "INSERT INTO PLANT VALUES()";
    }

    public void createShopTable() {
        //growTime,timePlanted,value,growth,growCounter,waterCount,waterLimit,price
        String newTable = "SHOP";
        //Drop if exists
        dropIfExists(newTable);
        String sqlCreateTable = "CREATE TABLE " + newTable + " (NAME VARCHAR (20),PRICE INT)";
        String insertData = "INSERT INTO SHOP VALUES()";
    }

    public void createUnlockTable() {
        //growTime,timePlanted,value,growth,growCounter,waterCount,waterLimit,price
        String newTable = "UNLOCKS";
        //Drop if exists
        dropIfExists(newTable);
        String sqlCreateTable = "CREATE TABLE " + newTable + " (NAME VARCHAR (20),PRICE INT)";
        String insertData = "INSERT INTO UNLOCKS VALUES()";
    }

    public void createPlantTable() {

        //growTime,timePlanted,value,growth,growCounter,waterCount,waterLimit,price
        String newTable = "PLANTSET";
        //Drop if exists
        dropIfExists(newTable);
        String sqlCreateTable = "CREATE TABLE " + newTable + " (NAME VARCHAR (20),GROWTIME INT, TIMEPLANTED INT, VALUE INT,GROWTH INT,GROWCOUNTER INT,WATERCOUNT INT,WATERLIMIT INT, PRICE INT,POLLINATOR BOOLEAN,POLLINATED BOOLEAN )";
        String insertData = "INSERT INTO PLANT VALUES()";
        //Can use this to generate some plant types
        for (PlantSet i : PlantSet.values()) {
            System.out.println(i.getPlant().toString());
        }
    }

    public boolean exists(String tableName) throws SQLException {
        DatabaseMetaData meta;

        meta = conn.getMetaData();

        ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

        return resultSet.next();

    }

    public void dropIfExists(String tableName) {
        //statement.executeUpdate("drop table if exists " + newTable);

        DatabaseMetaData meta;
        try {

            meta = conn.getMetaData();

            ResultSet resultSet = meta.getTables(null, null, tableName, new String[]{"TABLE"});

            if (resultSet.next()) {

                statement.executeUpdate("DROP TABLE " + tableName);
            }
        } catch (SQLException ex) {
            System.err.println("DROP TABLE FAILED: " + ex);
            Logger.getLogger(PlantGameDB.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NullPointerException ex) {
            System.err.println(ex);
        }

    }

    public static void main(String[] args) {
        PlantGameDB b = new PlantGameDB();
        b.createPlantTable();
    }

}
