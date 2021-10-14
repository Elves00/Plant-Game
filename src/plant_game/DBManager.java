/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
import java.sql.Connection;
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
 * Controls the data retrieval and updating of data for the plant game.
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

    private DBconstructor construct;

    /**
     * Establishes a connection to the DataBase
     */
    public DBManager() {
        establishConnection();
        if (conn != null) {
            construct = new DBconstructor(conn);
        }

    }

    public boolean constructDatabse() {
//        myUpdate("DROP TABLE Player");
//        myUpdate("DROP TABLE Field");
//        myUpdate("DROP TABLE Shop");
//        myUpdate("DROP TABLE Unlock");
//        myUpdate("DROP TABLE Info");
//        myUpdate("DROP TABLE Scores");
        if (construct.dbsetup()) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Creates a data object with initial conditions for the view.
     *
     * The views previous game button and load game options visibility is
     * determined by the pressence of a particular username in the database
     * table. This username only occurce when there is a default value occupying
     * a particular save slot.
     *
     * @return Data
     */
    public Data start() {

        try {
            Data data = new Data();
            //Load game is not vissable if a play has the name bellow and is not the current game slot.
            String sql = "SELECT * FROM PLAYER WHERE playerName='uGaTL@V%yiW3'AND slot>0";

            ResultSet rs;
            rs = this.myQuery(sql);
            while (rs.next()) {
                data.getLoadGameVisible()[rs.getInt("slot") - 1] = false;
                System.out.println(rs.getInt("slot"));

            }
            rs.close();

            //Previous game button is vissible only if the current game slot does not include this player name
            sql = "SELECT * FROM PLAYER WHERE playerName='uGaTL@V%yiW3' AND slot=0";

            rs = this.myQuery(sql);
            if (rs.next()) {
                data.setPreviousGame(false);
            } else {
                data.setPreviousGame(true);
            }
            rs.close();

            data.setLoadGameChanged(true);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Sets up a Data object containing all necessary information to create a
     * new Game.
     *
     * Sets up player name and creates a new data instance. Resets the unlock
     * and shop selection.
     *
     * @param name name for player
     * @return Data
     */
    public Data newGame(String name) {
        try {
            Data data = new Data();
            data.setPlayerName(name);
            //Saves the new player to the current game slot.
            this.savePlayer(0, data);

            //Remove any existing unlock values in the current game slot.
            String sql = "DELETE FROM Unlock where slot=0";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.executeUpdate();
            //Insert saffron truffle and tulip into the current game slot.
            sql = "INSERT INTO Unlock (name,cost,slot) VALUES(?,?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, "saffron");
            preparedStatement.setInt(2, 400);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            preparedStatement.setString(1, "truffle");
            preparedStatement.setInt(2, 200);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();
            preparedStatement.setString(1, "tulip");
            preparedStatement.setInt(2, 30);
            preparedStatement.setInt(3, 0);
            preparedStatement.executeUpdate();

            //Remove any exsiting shop values in the current game slot
            sql = "DELETE FROM Shop where slot=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.executeUpdate();

            //Insert broccoli cabbage and carrot into the current game slot
            sql = "INSERT INTO Shop (slot , name) VALUES(?,?)";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "broccoli");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "cabbage");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "carrot");
            preparedStatement.executeUpdate();
            return data;

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Loads a game using from the database storing information in Data class.
     *
     * Collects data for the player/field/shop and unlock from the tables and
     * stores it in the Data class
     *
     * @param selection
     * @return
     */
    public Data loadGame(int selection) {
        //sets up data object
        Data data = new Data();
        data = this.loadPlayer(selection, data);
        data = this.loadField(selection, data);
        //Sets up the shop for the selected save
        data = loadShop(selection, data);
        //Sets up the unlocks for the selected save
        data = loadUnlock(selection, data);
        return data;

    }

    /**
     * Loads a players information and stores it into data class.
     *
     * @param selection
     * @param data
     * @return
     */
    public Data loadPlayer(int selection, Data data) {

        try {
            //Save a player from the slot the method has been called with.
            String sql = "SELECT * FROM Player WHERE slot =" + selection + "";
            ResultSet rs = this.myQuery(sql);
            while (rs.next()) {
                data.setPlayerName(rs.getString("playerName"));
                data.setMoney(rs.getFloat("money"));
                data.setEnergy(rs.getInt("energy"));
                data.setDay(rs.getInt("day"));
                data.setScore(rs.getInt("score"));

            }
            rs.close();
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loads a fields information and stores it into data class.
     *
     * @param selection
     * @param data
     * @return
     */
    public Data loadField(int selection, Data data) {
        try {
            //Retrieves the slots field and stores it in the data object.
            String sql = "SELECT * FROM Field WHERE slot=" + selection + "";
            ResultSet rs;
            rs = this.myQuery(sql);
            //Strings to store plantNames names.
            String[][] plantNames = new String[3][3];
            //Strings to store plantNames details.
            String[][] plantsDescription = new String[3][3];
            int i = 0;
            int j = 0;
            while (rs.next()) {
                //Saves name to name holder
                plantNames[i][j] = rs.getString("name");
                //Saves details
                plantsDescription[i][j] = rs.getString("growtime") + " "
                        + rs.getString("timePlanted") + " "
                        + rs.getString("value") + " "
                        + rs.getString("growcounter") + " "
                        + rs.getString("growth") + " "
                        + rs.getString("waterLimit") + " "
                        + rs.getString("watercounter") + " "
                        + rs.getString("price") + " "
                        + rs.getString("pollinator");

                i++;
                //Since it's a 3 by 3 array we need to reset i once it reaches 3
                if (i == 3) {
                    i = 0;
                    j++;
                }

            }
            rs.close();
            //Stores plant names in data.
            data.setPlants(plantNames);
            //Stores plant descriptions in data.
            data.setPlantsDescription(plantsDescription);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loads information form the information table and stores it in the Data
     * Object.
     *
     * Uses selection as a primary key to retrieve all the words stored within
     * information table related to the key.
     *
     * @param selection key to be retrieved from information table
     * @param data
     * @return
     */
    public Data loadInfo(String selection, Data data) {
        try {
            //Select all information
            String sql = "SELECT * FROM Info Where information='" + selection
                    + "'";
            //set up array list to store information
            ArrayList<String> info = new ArrayList();
            ResultSet rs;
            rs = this.myQuery(sql);
            //Cycles through all entrys adding to the info list,
            while (rs.next()) {
                info.add(rs.getString("words"));
            }
            rs.close();

            //Stores the arraylist as a string[] in the data object.
            data.setInfoText(info.toArray(new String[info.size()]));

            //Returns edieted data.
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loads the details stored within the score table and updates data.
     *
     *
     * @param data
     * @return
     */
    public Data loadScores(Data data) {
        try {
            int[] scores = new int[20];
            String[] playerName = new String[20];
            String sql = "Select * From Scores";
            ResultSet rs;
            rs = this.myQuery(sql);
            int i = 0;
            while (rs.next()) {
                scores[i] = rs.getInt("score");
                playerName[i] = rs.getString("playerName");
                i++;

            }

            data.setScores(scores);
            data.setNames(playerName);
            data.setCheckScores(true);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Retrieves details about shop from the shop table and stores it in a data
     * object.
     *
     * @param selection which shop to load details from
     * @param data data to save shop details to
     * @return data
     */
    public Data loadShop(int selection, Data data) {
        try {
            String sql = "SELECT * FROM Shop WHERE slot=" + selection;

            ResultSet rs;
            rs = this.myQuery(sql);
            String shop = "";
            while (rs.next()) {
                shop += rs.getString("name") + " ";
            }
            rs.close();

            data.setShop(shop);

            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("ERROR REACHED HERE");
        return null;
    }

    public Data loadText(Data data) {
        try {
            String loadText[] = new String[5];

            String sql = "SELECT * FROM Player where slot=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs;
            for (int i = 1; i < 6; i++) {
                preparedStatement.setInt(1, i);

                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    loadText[i - 1] = rs.getString("playerName");

                }
                rs.close();
            }

            data.setLoadText(loadText);

            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param slot
     * @param data
     * @return
     */
    public Data loadUnlock(int slot, Data data) {
        try {

            String sql = "SELECT * FROM Unlock WHERE slot=" + slot;

            ResultSet rs;
            rs = this.myQuery(sql);
            String unlock = "";
            String unlockCost = "";
            while (rs.next()) {
                unlock += " " + rs.getString("name");
                unlockCost += " " + rs.getInt("cost");

            }
            rs.close();

            data.setUnlock(unlock);
            data.setUnlockCost(unlockCost);
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates the unlocks table.
     *
     * Removes an entry from the unlock table.
     *
     * @param selection
     * @param plant
     * @param data
     * @return
     */
    public Data updateUnlock(int selection, String plant, Data data) {

        String sql = "DELETE FROM Unlock WHERE slot=" + selection + " AND  name ='" + plant + "'";
        myUpdate(sql);

        data = loadUnlock(selection, data);
        return data;
    }

    /**
     * Updates the scores table.
     *
     * Adds a score to the score table if there are less then 20 scores.If there
     * are more then 20 scores drop the lowest value if the lowest value is the
     * new score to be inputted don't add. the lowest.
     */
    public void updateScores(Data data) {

        try {
            //Retrieve all scores
            String sql = "Select * FROM Scores Where id=20";
            boolean exists = false;
            ResultSet rs;
            rs = this.myQuery(sql);
            while (rs.next()) {
                //If we reach here id 20 is present.
                exists = true;
            }
            rs.close();

            if (exists) {
                sql = "Select * FROM Scores";
                //Set up an int to store the lowest recorded score id
                int lowestScore = data.getScore();
                int id = 1;
                rs = this.myQuery(sql);
                while (rs.next()) {
                    if (rs.getInt("score") < lowestScore) {
                        id = rs.getInt("id");
                        lowestScore = rs.getInt("score");
                    }
                }

                //Updates the table with the lowest score being replaced
                if (lowestScore != data.getScore()) {
                    sql = "Update Scores set score=?,playerName=? where id=?";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    preparedStatement.setInt(1, data.getScore());
                    preparedStatement.setString(2, data.getPlayerName());
                    preparedStatement.setInt(3, id);
                    preparedStatement.executeUpdate();
                }

            } else {
                //Since there is less then 20 high scores we count the number of scores.
                sql = "Select * FROM Scores";
                //Id starts at one so we always add the score with a new id.
                int id = 1;
                rs = this.myQuery(sql);
                while (rs.next()) {
                    id++;
                }

                //Insert a new score to the list.
//                System.out.println("THE ID FOR INSERTION IS:" + id);
                sql = "INSERT INTO Scores(id,playerName,score) VALUES(?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, data.getPlayerName());
                preparedStatement.setInt(3, data.getScore());
                preparedStatement.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Inserts a new plant into the shop table.
     *
     * @param selection which shop to insert the plant into
     * @param plant plant to be inserted
     */
    public void updateShop(int selection, String plant) {
//        System.out.println("Inserting " + plant + " into save slot " + selection);
        String sql = "INSERT INTO Shop VALUES(" + selection + ",'" + plant + "'  )";
        myUpdate(sql);

    }

    /**
     * Saves the unlock player and shop
     *
     * @param slot
     * @param data
     */
    public void saveGame(int slot, Data data) {
        savePlayer(slot, data);
        saveUnlock(slot, data);
        saveShop(slot, data);
        saveField(slot, data.getFieldDetails());

    }

    /**
     * Saves a player to the player table.
     *
     * Takes data stored within a Data class and updates each row of the player
     * to match the content stored within
     *
     * @param slot
     * @param data
     */
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
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Updates the Unlock table based on information from data
     *
     * @param slot
     * @param data
     */
    public void saveUnlock(int slot, Data data) {
        try {
            String sql = "DELETE FROM Unlock where slot=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, slot);
            preparedStatement.executeUpdate();

            sql = "INSERT INTO Unlock (name,cost,slot) VALUES(?,?,?)";
            StringTokenizer st1 = new StringTokenizer(data.getUnlock());
            StringTokenizer st2 = new StringTokenizer(data.getUnlockCost());

            preparedStatement = conn.prepareStatement(sql);

            while (st1.hasMoreTokens() && st2.hasMoreTokens()) {

                preparedStatement.setString(1, st1.nextToken());

                preparedStatement.setInt(2, parseInt(st2.nextToken()));
                preparedStatement.setInt(3, slot);
                preparedStatement.executeUpdate();

            }

            String check = "Select * From Unlock Where slot=" + slot;
            ResultSet rs;
            rs = this.myQuery(check);
            String unlock = "";

            while (rs.next()) {
                unlock += rs.getString("name");

            }
            rs.close();

            rs.close();

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Saves the state of the shop to the shop table based on data input.
     *
     * Overrides any data saved in the shop table for the selected slot.
     *
     * @param slot key of save
     * @param data
     */
    public void saveShop(int slot, Data data) {
        try {
            String sql = "DELETE FROM Shop where slot=?";

            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, slot);
            preparedStatement.executeUpdate();

            StringTokenizer st = new StringTokenizer(data.getShop());
            sql = "INSERT INTO Shop (slot , name) VALUES(?,?)";
            preparedStatement = conn.prepareStatement(sql);

            while (st.hasMoreTokens()) {
                preparedStatement.setInt(1, slot);
                preparedStatement.setString(2, st.nextToken());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Saves the state of the plant game field to the field table based on data
     * input.
     *
     * Updates information on the selected field matching the slot selection.
     *
     * @param slot key to update in field.
     * @param field
     */
    public void saveField(int slot, String[] field) {
        try {
//            System.out.println("UPDATING THE FIELD FOR SAVE " + slot);
            String sql = "Update Field set name=?,growtime =?,timeplanted=?,value=?,growcounter=?,growth=?,waterlimit=?,watercounter=?,price=?,pollinator=?,pollinated=? WHERE slot=? AND x=? AND y=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            /*the field array is set up to first pass in all plant data and then names
            for this reason the array gets set up in two parts first taking all non
            name details of plant then name details*/
            String[][] array = new String[9][15];
            //set up plant details in array.
            for (int i = 0; i < 3; i++) {
                StringTokenizer st = new StringTokenizer(field[i]);
                for (int j = 0; j < 3; j++) {
                    array[j + i * 3][1] = "" + slot;
                    array[j + i * 3][2] = "" + j;
                    array[j + i * 3][3] = "" + i;
                    array[j + i * 3][5] = st.nextToken();
                    array[j + i * 3][6] = st.nextToken();
                    array[j + i * 3][7] = st.nextToken();
                    array[j + i * 3][8] = st.nextToken();
                    array[j + i * 3][9] = st.nextToken();
                    array[j + i * 3][10] = st.nextToken();
                    array[j + i * 3][11] = st.nextToken();
                    array[j + i * 3][12] = st.nextToken();
                    array[j + i * 3][13] = st.nextToken();
                    array[j + i * 3][14] = st.nextToken();

                }
            }
            //Set up plant names in array
            for (int i = 0; i < 3; i++) {

                StringTokenizer st = new StringTokenizer(field[i + 3]);
                for (int j = 0; j < 3; j++) {
                    array[j + i * 3][4] = st.nextToken();
                }
            }

            //Sets up the 9 plantNames within the field using the array.
            for (int i = 0; i < 9; i++) {

                preparedStatement.setString(1, array[i][4]);
                preparedStatement.setInt(2, parseInt(array[i][5]));
                preparedStatement.setInt(3, parseInt(array[i][6]));
                preparedStatement.setInt(4, parseInt(array[i][7]));
                preparedStatement.setInt(5, parseInt(array[i][8]));
                preparedStatement.setInt(6, parseInt(array[i][9]));
                preparedStatement.setInt(7, parseInt(array[i][10]));
                preparedStatement.setInt(8, parseInt(array[i][11]));
                preparedStatement.setInt(9, parseInt(array[i][12]));
                preparedStatement.setBoolean(10, parseBoolean(array[i][13]));
                preparedStatement.setBoolean(11, parseBoolean(array[i][14]));
                preparedStatement.setInt(12, parseInt(array[i][1]));
                preparedStatement.setInt(13, parseInt(array[i][2]));
                preparedStatement.setInt(14, parseInt(array[i][3]));
                preparedStatement.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Ends the current game in slot 0 and restore default values to the table.
     *
     * @return Data new game data with start set to false.
     */
    public Data endGame() {
        Data data = new Data();
        try {

            String[] tableNames = new String[]{"Player,Shop,Field,Unlock"};

            //Inserts a player into each save slot.
            String sql = "UPDATE  Player SET playerName = ?,money=?,energy=?,day=?,score=? where slot =?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);

            for (int i = 0; i < tableNames.length; i++) {
                preparedStatement.setString(1, "uGaTL@V%yiW3");
                preparedStatement.setFloat(2, 200);
                preparedStatement.setInt(3, 100);
                preparedStatement.setInt(4, 0);
                preparedStatement.setInt(5, 0);
                preparedStatement.setInt(6, 0);

                preparedStatement.executeUpdate();
            }

            //Remove any remaining unlocks
            sql = "DELETE FROM Unlock Where slot=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.executeUpdate();

            //Re add unlocks
            sql = "INSERT INTO Unlock(slot,name,cost) VALUES(?,?,?)";
            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "saffron");
            preparedStatement.setInt(3, 400);
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "truffle");
            preparedStatement.setInt(3, 200);
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "tulip");
            preparedStatement.setInt(3, 30);
            preparedStatement.executeUpdate();

            sql = "UPDATE Field SET name=?,growtime=?,timeplanted=?,value=?,growcounter=?,growth=?,waterlimit=?,watercounter=?,price=?,pollinator=?,pollinated=? where x=? AND y=? AND slot=?";
            preparedStatement = conn.prepareStatement(sql);

            for (int j = 0; j < 3; j++) {
                for (int k = 0; k < 3; k++) {

                    preparedStatement.setString(1, "dirt");
                    preparedStatement.setInt(2, 0);
                    preparedStatement.setInt(3, 0);
                    preparedStatement.setInt(4, 0);
                    preparedStatement.setInt(5, 0);
                    preparedStatement.setInt(6, 0);
                    preparedStatement.setInt(7, 10);
                    preparedStatement.setInt(8, 0);
                    preparedStatement.setInt(9, 10);
                    preparedStatement.setBoolean(10, false);
                    preparedStatement.setBoolean(11, false);
                    preparedStatement.setInt(12, j);
                    preparedStatement.setInt(13, k);
                    preparedStatement.setInt(14, 0);
                    preparedStatement.executeUpdate();
                }
            }

            sql = "DELETE FROM Shop where slot=?";
            preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, 0);
            preparedStatement.executeUpdate();

            sql = "INSERT INTO SHOP(slot,name) VALUES(?,?)";

            preparedStatement = conn.prepareStatement(sql);

            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "broccoli");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "cabbage");
            preparedStatement.executeUpdate();
            preparedStatement.setInt(1, 0);
            preparedStatement.setString(2, "carrot");
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        data.setEndGame(true);

        return data;
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

        System.out.println(this.conn);
        if (this.conn == null) {
            try {

                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);
            } catch (SQLException ex) {

            }
        }

    }

    /**
     * Reastablishs a connenction if the connection was closed.
     */
    public void openConnections() {

        try {
            if (conn.isClosed()) {

                conn = DriverManager.getConnection(URL, USER_NAME, PASSWORD);

            }
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void closeConnections() {
        if (conn != null) {

            try {
                conn.close();

            } catch (SQLException ex) {
            }
        }
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * Preforms a query of the database
     *
     * @param sql
     * @return
     */
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

    /**
     * Preforms a update of the database
     *
     * @param sql
     */
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

    public static void main(String[] args) {

        //Call db setup
        DBManager db = new DBManager();
        db.constructDatabse();
        db.loadGame(1);

    }

}
