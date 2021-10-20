/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author breco
 */
public class DBconstructor {

    Connection conn;

    /**
     * Creates a DBconstructor.
     *
     * Establish connection to the database using the inputted conn.
     *
     * @param conn
     */
    public DBconstructor(Connection conn) {
        this.conn = conn;
    }

    /**
     * Creates the default player table with 6 players with initial starting
     * values.
     *
     * Primary Key:Slot
     */
    public void createPlayerTable() {

        //All tabel creation here
        this.myUpdate("CREATE TABLE " + "Player" + " (slot INT,playerName VARCHAR(20),money FLOAT,energy INT ,day INT,score INT)");
        //Inserts a player into each save slot.
        for (int i = 0; i < 6; i++) {
            myUpdate("INSERT INTO Player VALUES (" + i + ",'uGaTL@V%yiW3',200,100,0,0)");
        }

    }

    /**
     * Creates the default shop table with 6 shops containing broccoli,cabbage
     * and carrot
     *
     * Primary key: Slot+Name
     */
    public void createShopTable() {
        try {
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

        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates the default Unlock table with 6 unlock sets containing
     * saffron,truffle and tulip.
     *
     * Primary key:Slot+Name
     */
    public void createUnlockTable() {
        try {
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
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Creates a scores table with no default values. Primary Key:Id
     */
    public void createScoresTable() {
        myUpdate("CREATE TABLE Scores (id INT,playerName VARCHAR(20),score INT)");

    }

    /**
     * Creates a field table with default values.
     *
     * The field table stores the plants within a players field. The default
     * starting values for each field is 9 dirt's.
     *
     * The table starts with 6 different fields representing the 6 different
     * save slots the game controls.
     *
     * Primary Key: Slot + X + Y
     */
    public void createFieldTable() {
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

    public void createInfoTable() {
        try {
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

            words = "By selecting unlock you are entering the unlock shop. This is a shop where you may purchase new and better plants. If you chose \n"
                    + " to buy an unlock the corresponding plant will be made avaliable to you next time you select the plant a plant option.";
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

            words = "When you select the pick a plant a view of the value of all plants within the field will be displayed. You will be prompted to enter \n"
                    + "a row and collumn number this represents the plant you would like to pick.When a plant is picked it will be removed from the field \n"
                    + " and replaced by dirt unless it is a special plant such as truffle which will instead replant itself when picked. A plant that is \n "
                    + "picked will transfer any value it has accumulated to you. For example a carrot with a value of 4$ when picked will increase your \n"
                    + "total money by 4$.";
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

            words = "When you selecting water the field will be displayed with numbers representing how many times each plant has been watered and how many\n"
                    + " times it needs to be watered to gorw. For example a carrot that has not been watered will display the numbers 0/2 representing that \n"
                    + " it has been watered 0 times and needs to be watered twice in order to grow.";
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

            words = "When you select the plant a plant option you may chose a plant to buy for your field. A plants cost is displayed next to it's selection\n"
                    + " number. Once you have purchased a plant you may select the row and collumn you would like to plant the plant in. When a plant is \n"
                    + " planted it will replace any plant that is currently in the selected spot.";
            st1 = new StringTokenizer(words, "\n");
            while (st1.hasMoreTokens()) {
                preparedStatement.setString(1, "Plant a Plant");
                preparedStatement.setInt(2, count);
                preparedStatement.setString(3, st1.nextToken());
                preparedStatement.executeUpdate();
                count++;
            }
            count = 0;

            words = "When you select the save game button a list of games will be displayed. By selecting one of the numbers you will override any save \n"
                    + "file that is currently there and replace it with the save file of the game you are currently playing.";
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
        } catch (SQLException ex) {
            Logger.getLogger(DBManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Checks for each table within the database. If any are missing create
     * them.
     */
    public boolean dbsetup() {

        if (this.conn != null) {
      
            try {

                //Check for all necessary tables and creates any that do not exist.
                if (!checkTableExisting("Player")) {
                    createPlayerTable();
                }

                if (!checkTableExisting("Shop")) {
                    createShopTable();
                }

                if (!checkTableExisting("Unlock")) {
                    createUnlockTable();
                }

                if (!checkTableExisting("Scores")) {
                    createScoresTable();
                }

                if (!checkTableExisting("Field")) {
                    createFieldTable();
                }

                if (!checkTableExisting("Info")) {
                    createInfoTable();
                }

            } catch (Throwable e) {

                return false;
            }
        }

        return true;
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

            String[] types = {"TABLE"};
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet rsDBMeta = dbmd.getTables(null, null, null, null);//types);
            //Statement dropStatement=null;
            while (rsDBMeta.next()) {
                String tableName = rsDBMeta.getString("TABLE_NAME");
                if (tableName.compareToIgnoreCase(newTableName) == 0) {

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

}
