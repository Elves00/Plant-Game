 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class writes and reads the state of the game to files for the Plant
 * Game.
 *
 * @author breco
 */
public class GameState {

    /**
     * Constructor.
     */
    public GameState() {

    }

    /**
     * Reads information from the Game Information file and adds it to a String
     * array
     *
     * @param words ArrayList of type string to add info to
     * @param find The word to find in the game information file
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> gameInfo(ArrayList<String> words, String find) throws FileNotFoundException, IOException {
        String hold;

        try (BufferedReader inStream = new BufferedReader(new FileReader("GameInformation.txt"))) {
            //Reads through the whole file
            while ((hold = inStream.readLine()) != null) {
                //If the searched word is found print until the word is found again.
                if (hold.equals(find)) {
                    while (!((hold = inStream.readLine()).equals(find))) {
                        words.add(hold);
                    }

                }
            }
        }
        return words;

    }

    /**
     * Reads information from the Plant Information file and adds it to an
     * ArrayList of strings
     *
     * @param words ArrayList to be added to.
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> plantInfo(ArrayList<String> words) throws FileNotFoundException, IOException {
        String hold;

        try (BufferedReader inStream = new BufferedReader(new FileReader("PlantInformation.txt"))) {
            while ((hold = inStream.readLine()) != null) {
                //Search for the word plants and prints all info until the next time plants is a line by itself
                if (hold.equals("plants")) {

                    while ((hold = inStream.readLine()) != null) {
                        if (hold.equals("plants")) {

                            break;

                        }
                        words.add(hold);
                    }
                    break;
                }
            }
        }
        return words;

    }

    /**
     * Reads from text files containing specific information about the state of
     * the game and information. and returns the information contained in a
     * ArrayList of strings.
     *
     * @param info
     * @return ArrayList<String> contains each line read from a file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public ArrayList<String> information(String info) throws FileNotFoundException, IOException {
        String hold;

        ArrayList<String> words = new ArrayList();

        //Checks which information is being searched for.
        switch (info) {
            case "plants":
                words = plantInfo(words);
                break;
            case "Information":
                words = gameInfo(words, info);
                break;
            case "Unlock":
                words = gameInfo(words, info);
                break;
            case "Next day":
                words = gameInfo(words, info);
                break;
            case "Pick Plant":
                words = gameInfo(words, info);
                break;
            case "Water":
                words = gameInfo(words, info);
                break;
            case "Plant a Plant":
                words = gameInfo(words, info);
                break;
            case "Save game":
                words = gameInfo(words, info);
                break;
            default:
                break;
        }

        return words;
    }

    /**
     * Creates a new game with a brand new player, plant shop and unlocks.New
     * Player creates a player using the name given to it.
     *
     * It then creates a new shop and unlock object to set up inital unlock and
     * PlantSelection files.
     *
     * @param name The name to create a new player object with
     * @return Player object with the stats of a new new player.
     * @throws java.io.FileNotFoundException
     */
    public Player newPlayer(String name) throws MoneyException, FileNotFoundException {

        //Sets up new player unlock and plant selection
        Player player = new Player(name);
        UnlockShop unlock = new UnlockShop();
        PlantSelection plantSelection = new PlantSelection();

        //writes the new unlock and plantselection to file.
        this.printShop(plantSelection);
        this.printUnlock(unlock);

        //Returns the new player.
        return player;

    }

    /**
     * Writes the stats of a player to a file. Wipes old file.
     *
     * @param user
     * @throws FileNotFoundException
     */
    public void savePlayer(Player user) throws FileNotFoundException {

        try ( //Sets up the print writer to allow writing to file.
                //Sets up output file.
                PrintWriter pw = new PrintWriter(new FileOutputStream("Player.txt"))) {
            pw.println(user.getName());
            pw.println(user.getMoney());
            pw.println(user.getEnergy());
            pw.println(user.getDay());
            pw.println(user.getScore());
            String[] croops = new String[6];
            croops = user.getField().toFile();
            for (String i : croops) {
                pw.println(i);
            }
            pw.close();
            //So we need to add to this so theres shops and unlocks
        }

    }

    /**
     * Saves the state of a game to the current game file Writes player
     * information first Writes shop information writes unlock information
     * closes.
     *
     * @param ps current plant selection
     * @param un current unlock
     * @param user player to save
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void saveCurrentGame(PlantSelection ps, UnlockShop un, Player user) throws FileNotFoundException, IOException {

        printShop(ps);
        printUnlock(un);
        savePlayer(user);

        try (PrintWriter pw = new PrintWriter(new FileOutputStream("CurrentGame.txt"))) {
            String hold;

            //Prints the player to file
            BufferedReader inStream = new BufferedReader(new FileReader("Player.txt"));
            while ((hold = inStream.readLine()) != null) {
                pw.println(hold);
            }
            inStream.close();

            //prints the shop to file
            inStream = new BufferedReader(new FileReader("Shop.txt"));
            while ((hold = inStream.readLine()) != null) {
                pw.println(hold);
            }
            inStream.close();

            //prints the unlock to file.
            inStream = new BufferedReader(new FileReader("Unlock.txt"));
            while ((hold = inStream.readLine()) != null) {
                pw.println(hold);
            }
            inStream.close();
        }
    }

    /**
     * Reads the current game file and writes corresponding information to the
     * player,PlantSelection and unlock file
     *
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void loadCurrentGame() throws FileNotFoundException, IOException {
        ArrayList<String> reading = new ArrayList<>();

        try (BufferedReader inStream = new BufferedReader(new FileReader("CurrentGame.txt"))) {
            String hold;

            while ((hold = inStream.readLine()) != null) {
                reading.add(hold);

            }
            //reads info about player and prints to file
            PrintWriter pw = new PrintWriter(new FileOutputStream("Player.txt"));
            for (int i = 0; i < 11; i++) {
                pw.println(reading.get(i));

            }
            pw.close();
            //reads info about shop and prints to file
            pw = new PrintWriter(new FileOutputStream("Shop.txt"));

            pw.println(reading.get(11));

            pw.close();
            //reads info about unlock and prints to file
            pw = new PrintWriter(new FileOutputStream("Unlock.txt"));
            for (int i = 12; i < 14; i++) {
                pw.println(reading.get(i));

            }
            pw.close();
        }

    }

    /**
     * Saves the current game to a specified save slot within SaveGame file.
     *
     * @param user
     * @param saveSlot
     */
    public void saveGame(PlantSelection ps, UnlockShop un, Player user, int saveSlot) throws FileNotFoundException, IOException {
        //Sets up the print writer to allow writing to file.
        //Sets up output file.
        saveCurrentGame(ps, un, user);
        BufferedReader currentStream;
        ArrayList<String> storage;
        try (BufferedReader inStream = new BufferedReader(new FileReader("SaveGame.txt"))) {
            currentStream = new BufferedReader(new FileReader("CurrentGame.txt"));
            storage = new ArrayList<String>();
            //Store details of all saves before index
            for (int j = 1; j < saveSlot; j++) {
                for (int i = 0; i < 16; i++) {
                    storage.add(inStream.readLine());

                }
            }

            //Store details of current player
            for (int i = 0; i < 16; i++) {
                storage.add(currentStream.readLine());
            }

            //Cycles instream to avoid the slot at selection.
            for (int i = 0; i < 16; i++) {
                inStream.readLine();
            }

            //Store details of all saves after index
            for (int i = saveSlot + 1; i < 6; i++) {
                for (int j = 0; j < 16; j++) {
                    storage.add(inStream.readLine());
                }
            }
        }
        currentStream.close();

        //Print all details from storage back to file
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("SaveGame.txt"))) {
            //Print all details from storage back to file
            storage.forEach(i -> {
                pw.println(i);
            });
        }

    }

    /**
     * Reads the saveGame file to retrieve details to create a new player object
     * with the stats of previous game.
     *
     * @param selection The position the player has selected.
     * @returnPlayer A player class with the stats of a previously played game
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FileLoadErrorException
     */
    public void loadGame(int selection) throws FileNotFoundException, IOException, FileLoadErrorException {

        //Sets up the file reader
        BufferedReader inStream = new BufferedReader(new FileReader("SaveGame.txt"));

        //if the selection is greater then 1 we need to skipp over data entrys to reach 
        //the desried set of player stats.
        for (int i = 1; i < selection; i++) {
            for (int j = 0; j < 16; j++) {
                inStream.readLine();
            }
        }

        //Sets up and prints player info to file
        PrintWriter pw = new PrintWriter(new FileOutputStream("Player.txt"));
        for (int i = 0; i < 11; i++) {
            pw.println(inStream.readLine());
        }
        pw.close();

        //prints shop info to file
        pw = new PrintWriter(new FileOutputStream("Shop.txt"));

        pw.println(inStream.readLine());

        pw.close();

        //prints unlock info to file
        pw = new PrintWriter(new FileOutputStream("Unlock.txt"));

        for (int i = 13; i < 15; i++) {
            pw.println(inStream.readLine());
        }
        pw.close();

    }

    /**
     * saveDisplay returns a string array of save game player names.
     *
     * Functions reads the first 5 names within the saveGame file and returns
     * them.
     *
     * @return String[] String array containing 5 player names representing the
     * 5 save files available to the game.
     * @throws IOException
     */
    public String[] saveDisplay() throws IOException {
        BufferedReader inStream = new BufferedReader(new FileReader("SaveGame.txt"));
        //String to hold player names
        String[] savegame = new String[5];

        //Stores the name then cycles through the player information avaliable in the file 
        //till it reaches the position of the next name. This should be 11 lines if file information is saved
        //Correctly.
        for (int i = 0; i < 5; i++) {
            savegame[i] = (i + 1) + " " + inStream.readLine();
            inStream.readLine();
            inStream.readLine();
            savegame[i] += " Day:" + inStream.readLine();
            for (int j = 0; j < 12; j++) {
                inStream.readLine();

            }

        }

        return savegame;
    }

    /**
     * Checks if there is a currentGame text file and returns false if there
     * isn't Returns false if
     *
     * @return True if savePlayer file exists
     */
    public boolean saveState() throws IOException {
        try {
            try (BufferedReader inStream = new BufferedReader(new FileReader("Player.txt"))) {
                String hold;
                //If the file is not filled with infor return null
                if ((hold = inStream.readLine()) == null) {
                    return false;
                }
            }

            return true;
        } catch (FileNotFoundException m) {
            return false;
        }
    }

    /**
     * Reads the stats from the currentGame file and creates a player with the
     * associated stats
     *
     * @return Player with stats from last game
     * @throws FileNotFoundException
     * @throws IOException
     * @throws FileLoadErrorException
     */
    public Player loadPlayer() throws FileNotFoundException, IOException, FileLoadErrorException, InstantiationException, IllegalAccessException {

        try {

            //Sets up a player
            Player player = new Player();
            //String to hold file line input
            try ( //Sets up the file reader
                    BufferedReader inStream = new BufferedReader(new FileReader("Player.txt"))) {
                //String to hold file line input
                String holder;
                /*Reads each line of the current game file and assigns it to the appropriate palyer variable
                The order for loading in lines is
                Player name
                Player money
                Player Energy
                Player day
                Player score
                Player field state
                Player field plant names
                 */
                holder = inStream.readLine();
                player.setName(holder);
                holder = inStream.readLine();
                player.setMoney(parseFloat(holder));
                holder = inStream.readLine();
                player.setEnergy(parseInt(holder));
                holder = inStream.readLine();
                player.setDay(parseInt(holder));
                holder = inStream.readLine();
                player.setScore(parseInt(holder));

                //Creates a string list to hold plant details from file
                ArrayList<String> description = new ArrayList();
                //String to hold each plant detail
                String details = "";
                //Runs a loop over the 3 lines which should hold the plant details.
                for (int i = 0; i < 3; i++) {

                    StringTokenizer st = new StringTokenizer(inStream.readLine());

                    for (int j = 0; j < 3; j++) {

                        //CHANGED TO 10 FROM 8
                        for (int k = 0; k < 10; k++) {
                            details = details + " " + st.nextToken();
                        }
                        description.add(details);
                        details = "";
                    }
                }
                //Throws an error if there is less than or greater then 9 pices of info.
                if (description.size() != 9) {
                    throw new FileLoadErrorException(" Error in loading plant details");
                }
                ArrayList<Plant> hold = new ArrayList();
                for (int i = 0; i < 3; i++) {
                    //Need to get each plant and then put them into an array then loadPlayer array in
                    StringTokenizer st = new StringTokenizer(inStream.readLine());

                    for (int j = 0; j < 3; j++) {

                        //Can't loadPlayer a plant so has to read and then create a plant
                        holder = st.nextToken();

                        //Search through the plant set and add the appropriate plant to the filed
                        for (PlantSet p : PlantSet.values()) {

                            if (p.toString().equalsIgnoreCase(holder)) {
                                hold.add(p.getPlant().getClass().newInstance());
                            }
                        }
                    }
                }

                //Throws an error if there is less than or greater then 9 pices of info.
                if (hold.size() != 9) {
                    throw new FileLoadErrorException(" Error in loading plant details");
                }

                //Sets all the plants but not all the stats
                player.getField().setAllPlants(hold);

                //Sets all the plants stats
                player.getField().setAllPlantStatus(description);
            }
            return player;

        } catch (MoneyException ex) {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    /**
     * Saves the current high scores to the high score file.
     *
     * @param highscores An OrderedList containing the games high scores.
     * @param player current plant game player.
     * @throws FileNotFoundException
     */
    public void saveHighScore(OrderedList<Score> highscores, Player player) throws FileNotFoundException {

        highscores.add(new Score(player.getName(), player.getScore()));

        try ( //Print out the new highscore list
                PrintWriter pw = new PrintWriter(new FileOutputStream("HighScores.txt"))) {
            //Prints the top 10 
            for (int i = 0; i < 10; i++) {
                pw.println(highscores.get(i));
            }
        } catch (NullPointerException n) {
            //We may not always have 10 highscores avaliable so catch null pointer exception
        }
    }

    /**
     * Reads from the HighScores file and creates a new OrderedList containing
     * all the current game scores.
     *
     * @return sorter A ordered list of high score.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public OrderedList loadHighScore() throws FileNotFoundException, IOException {
        BufferedReader inStream = new BufferedReader(new FileReader("HighScores.txt"));

        //An ordered list which we simply add the score to.
        OrderedList sorter = new OrderedList<Score>();
        String x;
        int z;
        //Read the current high scores into the list.
        while ((x = inStream.readLine()) != null) {
            StringTokenizer st = new StringTokenizer(x);
            //trialling
            x = st.nextToken();
            z = parseInt(st.nextToken());

            Score score = new Score(x, z);

            sorter.add(score);
        }

        return sorter;
    }

    /**
     * Reads the current players stats and details and writes them to the
     * currenGame file.
     *
     * @param player
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void endGame(Player player) throws FileNotFoundException, IOException {

        //An ordered list which we simply add the score to.
        OrderedList sorter = new OrderedList<Integer>();

        String x;

        int z;
        //Read the current high scores into the list.
        try (BufferedReader inStream = new BufferedReader(new FileReader("HighScores.txt"))) {
            //Read the current high scores into the list.
            while ((x = inStream.readLine()) != null) {
                StringTokenizer st = new StringTokenizer(x);
                //trialling
                x = st.nextToken();
                z = parseInt(st.nextToken());

                Score score = new Score(x, z);

                sorter.add(score);
            }
            //Add new highscore into the list
            sorter.add(new Score(player.getName(), player.getScore()));

            try ( //Print out the new highscore list
                    PrintWriter pw = new PrintWriter(new FileOutputStream("HighScores.txt"))) {
                for (int i = 0; i < sorter.size(); i++) {
                    pw.println(sorter.get(i));
                }
            }
        }
    }

    /**
     * Prints the details of a given PlantSelection to the shop file
     * representing the state of the current plant selection.
     *
     * @param plantSelection
     * @throws FileNotFoundException
     */
    public void printShop(PlantSelection plantSelection) throws FileNotFoundException {
        //Uses to file method to print plantselection to file.
        try ( //File to write to.
                PrintWriter pw = new PrintWriter(new FileOutputStream("Shop.txt"))) {
            //Uses to file method to print plantselection to file.
            pw.println(plantSelection.toFile());

        }
    }

    /**
     * Prints the details of a given UnlockShop to the UnlockShop file
     * representing the state of the current UnlockShop.
     *
     * @param unlock
     * @throws FileNotFoundException
     */
    public void printUnlock(UnlockShop unlock) throws FileNotFoundException {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream("Unlock.txt"))) {
            pw.println(unlock.toFile());
        }
    }

    /**
     * Reads the details stored in the shop file and creates a plantSelection
     * option matching the details.
     *
     * @return PlantSelection with details specified by current shop file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public PlantSelection readShop() throws FileNotFoundException, IOException {
        String shop;
        try (BufferedReader inStream = new BufferedReader(new FileReader("Shop.txt"))) {
            shop = "";
            shop += inStream.readLine() + " ";

        }

        return new PlantSelection(shop);
    }

    /**
     * Reads the details stored in the UnlockShop file and creates an UnlockShop
     * object option matching the details.
     *
     * @return UnlockShop with details specified by current UnlockShop file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    public UnlockShop readUnlock() throws FileNotFoundException, IOException {
        ArrayList<String> unlocks;
        try (BufferedReader inStream = new BufferedReader(new FileReader("Unlock.txt"))) {
            unlocks = new ArrayList<>();
            String shop = "";
            shop += inStream.readLine();
            unlocks.add(shop);
            shop = "";
            shop += inStream.readLine();
            unlocks.add(shop);
        }

        return new UnlockShop(unlocks);
    }

}
