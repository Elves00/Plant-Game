/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Boolean.parseBoolean;
import static java.lang.Integer.parseInt;
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
//                myUpdate("DROP TABLE Player");
//                myUpdate("DROP TABLE Field");
//                myUpdate("DROP TABLE Shop");
//                myUpdate("DROP TABLE Unlock");
//                myUpdate("DROP TABLE Info");
//                myUpdate("DROP TABLE Scores");

                if (!checkTableExisting("Player")) {

                    //All tabel creation here
                    this.myUpdate("CREATE TABLE " + "Player" + " (slot INT,playerName VARCHAR(20),money FLOAT,energy INT ,day INT,score INT)");
                    //Inserts a player into each save slot.
                    for (int i = 0; i < 6; i++) {
                        myUpdate("INSERT INTO Player VALUES (" + i + ",'Empty',200,100,0,0)");
                    }

                }

                if (!checkTableExisting("Plant")) {
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

                    myUpdate("CREATE TABLE Shop (slot INT,name VARCHAR(10))");
                    String sql = "INSERT INTO SHOP(slot,name) VALUES(?,?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    for (int i = 0; i < 6; i++) {

                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "broccoli");
                        preparedStatement.executeUpdate();
                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "cabbage");
                        preparedStatement.executeUpdate();
                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "carrot");
                        preparedStatement.executeUpdate();

                    }

                }

                if (!checkTableExisting("Unlock")) {

                    myUpdate("CREATE TABLE Unlock (slot INT,name VARCHAR(10),cost INT)");

                    String sql = "INSERT INTO Unlock(slot,name,cost) VALUES(?,?,?)";
                    PreparedStatement preparedStatement = conn.prepareStatement(sql);
                    for (int i = 0; i < 6; i++) {
                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "saffron");
                        preparedStatement.setInt(3, 400);
                        preparedStatement.executeUpdate();
                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "truffle");
                        preparedStatement.setInt(3, 200);
                        preparedStatement.executeUpdate();
                        preparedStatement.setInt(1, i);
                        preparedStatement.setString(2, "tulip");
                        preparedStatement.setInt(3, 30);
                        preparedStatement.executeUpdate();
                    }

                }

                //Creates the scores table. Orginally there is no values in score.
                if (!checkTableExisting("Scores")) {

                    myUpdate("CREATE TABLE Scores (id INT,playerName VARCHAR(20),score INT)");

                }

                if (!checkTableExisting("Field")) {

//                      PreparedStatement pstmt = conn.prepareStatement(" UPDATE CARTABLE SET PRICE=? WHERE MODEL=?");
                    myUpdate("CREATE TABLE Field (slot INT,x INT,y INT,name VARCHAR(10),growtime INT,timeplanted INT,value INT,growcounter INT, growth INT,waterlimit INT,watercounter INT,price INT,pollinator BOOLEAN,pollinated BOOLEAN)");

                    //Inserts the inital field values for slots 0,1,2,3,4,5 
                    for (int i = 0; i < 6; i++) {
                        for (int j = 0; j < 3; j++) {
                            for (int k = 0; k < 3; k++) {
                                myUpdate("INSERT INTO Field VALUES (" + i + "," + j + "," + k + ",'dirt',0,0,0,0,0,10,0,10,FALSE,FALSE)");
                            }
                        }
                    }

                }

                if (!checkTableExisting("Info")) {
                    myUpdate("CREATE TABLE Info (information VARCHAR(14),line INT,words VARCHAR(254))");
                    String sql = "INSERT INTO Info (information,line,words) VALUES(?,?,?)";

                    PreparedStatement preparedStatement = conn.prepareStatement(sql);

                    String words = "Welcome to the Plant Game! Your main goal is to pick and plant crops to make enough money to pay the weeks rent. \n"
                            + "\n"
                            + "To select an option simply type the corresponding number on your keyboard and press enter.\n"
                            + "\n"
                            + "To plant a crop simply pick the plant crop option and type in the co-ordianates you want your plant to appear in the field. \n"
                            + "Crops will not grow by themselves, they need water and time in order to become crops worth picking. \n"
                            + "\n"
                            + "Each crop type has it's own growth cycle from seed to fully grown. For a plant to move up a stage in the growth cycle you will\n"
                            + "need to water it a certain number of times, as shown in the crop information panel. As well as watering crops require time \n"
                            + "to grow; by pressing the next day button one day will pass and the crop will be one stage closer to growing. Some plants \n"
                            + "take longer to grow then others and this information can be viewed in the plant information menu. \n"
                            + "\n"
                            + "A plant will not grow unless both enough time has passed since it's last growth and it has enough water. \n"
                            + "\n"
                            + "Once a crop has grown past the size of a seedling it will continue to grow in value. To realize this value you can use the \n"
                            + "pick option. This will remove a plant from the specified co-ordinate in your field and add it's current value to your wallet.\n"
                            + "The game ends once a player fails to meet rent which is paid every 5 days.  Enjoy the game :)";
                    StringTokenizer st1 = new StringTokenizer(words, "\n");
                    int count = 0;
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Information");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }

                    words = "By selecting unlock you are entering the unlock shop. This is a shop where you may purchase new and better plants. If you chose to buy an unlock the corresponding plant will be \n"
                            + "made avaliable to you next time you select the plant a plant option.";
                    st1 = new StringTokenizer(words, "\n");
                    count = 0;
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Unlock");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "By selecting the next day option the game will move to the next day. When the games moves to the next day all croops will have the \n"
                            + "potential to grow to there next stage. A plant will grow as long as it has been watered enough and enough days have passed. Your energy\n"
                            + "will also be set back to 100 at the start of the new day.";
                    st1 = new StringTokenizer(words, "\n");
                    count = 0;
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Next Day");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "When you select the pick a plant a view of the value of all plants within the field will be displayed. You will be prompted to enter a row and collumn number this represents the plant you would like to pick.\n"
                            + "When a plant is picked it will be removed from the field and replaced by dirt unless it is a special plant such as truffle which will instead\n"
                            + "replant itself when picked. A plant that is picked will transfer any value it has accumulated to you. For example a carrot with a value of 4$ when picked \n"
                            + "will increase your total money by 4$.";
                    st1 = new StringTokenizer(words, "\n");
                    count = 0;
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Pick Plant");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "When you selecting water the field will be displayed with numbers representing how many times each plant has been watered and how many times it needs to be watered to gorw.\n"
                            + "For example a carrot that has not been watered will display the numbers 0/2 representing that it has been watered 0 times and needs to be watered twice in order to grow. \n";
                    st1 = new StringTokenizer(words, "\n");
                    count = 0;
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Water");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "When you select the plant a plant option you may chose a plant to buy for your field. A plants cost is displayed next to it's selection number. Once you have purchased a plant you may select the \n"
                            + "row and collumn you would like to plant the plant in. When a plant is planted it will replace any plant that is currently in the selected spot.";
                    st1 = new StringTokenizer(words, "\n");
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Plant a Plant");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "When you select the save game button a list of games will be displayed. By selecting one of the numbers you will override any save file that is currently there \n"
                            + "and replace it with the save file of the game you are currently playing.";
                    st1 = new StringTokenizer(words, "\n");
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Save Game");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;

                    words = "Name: broccoli\n"
                            + "Value:0/1/2/3 $ Per day \n"
                            + "Grows: 3 times\n"
                            + "Grow time: 6 days\n"
                            + "Water Limit: 1 time\n"
                            + "Price:10\n"
                            + "Unique:Broccoli begins to gain value after the 3rd growth stage.\n"
                            + "\n"
                            + "\n"
                            + "Name: cabbage\n"
                            + "Value:0/1/2/3/4 $ Per day \n"
                            + "Grows: 4 time\n"
                            + "Grow time: 4 days\n"
                            + "Water Limit: 2 times\n"
                            + "Price:10\n"
                            + "\n"
                            + "Name: carrot\n"
                            + "Value:0/1/2/3 $ Per day \n"
                            + "Grows: 3 times\n"
                            + "Grow time: 3 days\n"
                            + "Water Limit: 2 times\n"
                            + "Price:10\n"
                            + "\n"
                            + "Name: Saffron\n"
                            + "Value:0/10/20 $ Does not increase each day.\n"
                            + "Grows: 2 time\n"
                            + "Grow time: 2 days\n"
                            + "Water Limit: 3 times\n"
                            + "Price:10\n"
                            + "Pollinator: Plants in grid spaces next to this plant receive 1$ each day after the plant is fully grown.\n"
                            + "\n"
                            + "Name: Truffle\n"
                            + "Value:0/2/4 $ Per day \n"
                            + "Grows: 2 times\n"
                            + "Grow time: 10 days\n"
                            + "Water Limit: 1 time\n"
                            + "Price:10\n"
                            + "Unique: When picked truffle a new truffle is planted in it's place.\n"
                            + "\n"
                            + "Name: Tulip\n"
                            + "Value:0/3 $ Does not increase each day.\n"
                            + "Grows: 1 time\n"
                            + "Grow time: 1 day\n"
                            + "Water Limit: 3 times\n"
                            + "Price:10\n"
                            + "Pollinator: Plants in grid spaces next to this plant receive 1$ each day after the plant is fully grown.\n"
                            + "";
                    st1 = new StringTokenizer(words, "\n");
                    while (st1.hasMoreTokens()) {
                        preparedStatement.setString(1, "Plants");
                        preparedStatement.setInt(2, count);
                        preparedStatement.setString(3, st1.nextToken());
                        preparedStatement.executeUpdate();
                        count++;
                    }
                    count = 0;
                    preparedStatement.close();
                }

                System.out.println("----Printing Player----");
                //Player
                String sql = "SELECT * FROM Player";
                ResultSet rs = this.myQuery(sql);
                while (rs.next()) {
                    System.out.print("" + rs.getInt("slot"));
                    System.out.print(" " + rs.getString("playerName"));
                    System.out.print(" " + rs.getFloat("money"));
                    System.out.print(" " + rs.getInt("energy"));
                    System.out.print(" " + rs.getInt("day"));
                    System.out.print(" " + rs.getInt("score"));
                    System.out.println("");
                }
                System.out.println("-----------");
                rs.close();

                System.out.println("----Printing Shop----");
                sql = "SELECT * FROM shop";
                rs = this.myQuery(sql);
                while (rs.next()) {
                    System.out.print("" + rs.getInt("slot"));
                    System.out.print(" " + rs.getString("name"));
                    System.out.println("");
                }
                System.out.println("-----------");
                rs.close();

                System.out.println("----Printing Unlock----");
                sql = "SELECT * FROM Unlock";
                rs = this.myQuery(sql);
                while (rs.next()) {
                    System.out.print("" + rs.getInt("slot"));
                    System.out.print(" " + rs.getString("name"));
                    System.out.print(" " + rs.getInt("cost"));
                    System.out.println("");
                }
                System.out.println("-----------");
                rs.close();

                System.out.println("----Printing Field----");
                sql = "SELECT * FROM Field";
                rs = this.myQuery(sql);
                while (rs.next()) {
                    System.out.print("" + rs.getInt("slot"));
                    System.out.print(" " + rs.getInt("x"));
                    System.out.print(" " + rs.getInt("y"));
                    System.out.print(" " + rs.getString("name"));
                    System.out.print(" " + rs.getInt("growtime"));
                    System.out.print(" " + rs.getInt("timeplanted"));
                    System.out.print(" " + rs.getInt("value"));
                    System.out.print(" " + rs.getInt("growcounter"));
                    System.out.print(" " + rs.getInt("growth"));
                    System.out.print(" " + rs.getInt("waterlimit"));
                    System.out.print(" " + rs.getInt("watercounter"));
                    System.out.print(" " + rs.getInt("price"));
                    System.out.print(" " + rs.getBoolean("pollinator"));
                    System.out.print(" " + rs.getBoolean("pollinated"));
                    System.out.println("");

                }
                System.out.println("-----------");
                rs.close();
//
//                System.out.println("----Printing Info----");
//                sql = "SELECT * FROM Info";
//                rs = this.myQuery(sql);
//                while (rs.next()) {
//                    System.out.print("" + rs.getString("information"));
//                    System.out.print(" " + rs.getInt("line"));
//                    System.out.print(" " + rs.getString("words"));
//                    System.out.println("");
//                }
//                System.out.println("-----------");
//                rs.close();

                System.out.println("----Printing Scores----");
                sql = "SELECT * FROM Scores";
                rs = this.myQuery(sql);
                while (rs.next()) {
                    System.out.print("" + rs.getInt("id"));
                    System.out.print(" " + rs.getString("playerName"));
                    System.out.print(" " + rs.getInt("score"));
                    System.out.println("");
                }

                System.out.println("-----------");
                rs.close();

            } catch (Throwable e) {
                System.out.println("error" + e);

            }
        }
    }

    /**
     * Sets up a data for a new game.
     *
     * Sets up player name and creates a new data instance.
     *
     * @param name name for player
     * @return Data
     */
    public Data newGame(String name) {
        Data data = new Data();
        data.setPlayerName(name);
        return data;
    }

    /**
     * Loads a game using data from the database.
     *
     * Collects data for the player/field/shop and unlock from the tables and
     * stores it in the Data class
     *
     * @param selection
     * @return
     */
    public Data loadGame(int selection) {
        if (this.conn != null) {

        } else {
            System.err.println("ITS NOT LOADED");
        }
        try {
            //Gets player data.
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
            rs.close();

            //Gets field data.
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
            rs.close();

            //Plants in the field
            data.setPlants(plants);
            data.setPlantsDescription(plantsDescription);

            //Sets up the shop and unlocks shop in data.
            data = selectShop(selection, data);
            data = selectUnlockShop(selection, data);
            return data;

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Loads information form the information table and stores it in the Data
     * class.
     *
     * @param selection
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

            //stores the array list in an array to pass to data class.
            String[] words = new String[info.size()];
            for (int i = 0; i < info.size(); i++) {
                words[i] = info.get(i);
            }
            data.setInfoText(words);

            //Returns edieted data.
            return data;
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
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
    public Data selectShop(int selection, Data data) {
        try {
            System.out.println("Selecting shop");
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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.err.println("ERROR REACHED HERE");
        return null;
    }

    /**
     * Inserts a new plant into the shop table.
     *
     * @param selection which shop to insert the plant into
     * @param plant plant to be inserted
     */
    public void updateShop(int selection, String plant) {
        System.out.println("Inserting " + plant + " into save slot " + selection);
        String sql = "INSERT INTO Shop VALUES(" + selection + ",'" + plant + "'  )";
        myUpdate(sql);

    }

    /**
     *
     * @param slot
     * @param data
     * @return
     */
    public Data selectUnlockShop(int slot, Data data) {
        try {
            System.out.println("Unlock " + slot);

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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     *
     * @param selection
     * @param plant
     * @param data
     * @return
     */
    public Data updateUnlock(int selection, String plant, Data data) {
        System.out.println("Deleting " + plant + " from save slot " + selection);
        String sql = "DELETE FROM Unlock WHERE slot=" + selection + " AND  name ='" + plant + "'";
        myUpdate(sql);

        data = selectUnlockShop(selection, data);
        return data;
    }

    /**
     *
     * @param slot
     * @param field
     */
    public void saveField(int slot, String[] field) {
        try {
            System.out.println("UPDATING THE FIELD FOR SAVE " + slot);
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

            //Sets up the 9 plants within the field using the array.
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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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

            //Print the number of rows affected by update
            System.out.println("Rows affected: " + preparedStatement.executeUpdate());

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
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
            //String sql = "update Unlock set name =? , cost =? where slot=?";

            String sql = "DELETE FROM Unlock where slot=?";
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setInt(1, slot);
            preparedStatement.executeUpdate();

            sql = "INSERT INTO Unlock (name,cost,slot) VALUES(?,?,?)";
            StringTokenizer st1 = new StringTokenizer(data.getUnlock());
            StringTokenizer st2 = new StringTokenizer(data.getUnlockCost());

            preparedStatement = conn.prepareStatement(sql);
//            System.out.println("Unlock:" + data.getUnlock());
//            System.out.println("UnlockCost:" + data.getUnlockCost());
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

            try {
                while (rs.next()) {
                    unlock += rs.getString("name");

                }
                rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
            }
//            System.out.println(unlock);

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        String check = "Select * From Shop Where slot=" + slot;
        ResultSet rs;
        rs = this.myQuery(check);
        String unlock = "";

        try {
            while (rs.next()) {
                unlock += rs.getString("name");

            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
//        System.out.println(unlock);
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
                System.out.println("Updating player table");
//                preparedStatement.setString(1, tableNames[i]);
                preparedStatement.setString(1, "empty");
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
            //Inserts the inital field values for slots 0,1,2,3,4,5 

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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        data.setEndGame(true);

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

        System.out.println("UPDATING SCORES");
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
                System.out.println("THE ID FOR INSERTION IS:" + id);
                sql = "INSERT INTO Scores(id,playerName,score) VALUES(?,?,?)";
                PreparedStatement preparedStatement = conn.prepareStatement(sql);
                preparedStatement.setInt(1, id);
                preparedStatement.setString(2, data.getPlayerName());
                preparedStatement.setInt(3, data.getScore());
                preparedStatement.executeUpdate();

            }

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }

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
            System.out.println("LOADING SCORES");

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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
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
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
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
