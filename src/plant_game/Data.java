/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

/**
 *
 * @author breco
 */
public class Data {

    /**
     * @return the checkScores
     */
    public boolean isCheckScores() {
        return checkScores;
    }

    /**
     * @param checkScores the checkScores to set
     */
    public void setCheckScores(boolean checkScores) {
        this.checkScores = checkScores;
    }

    /**
     * @return the warningCheck
     */
    public boolean isWarningCheck() {
        return warningCheck;
    }

    /**
     * @param warningCheck the warningCheck to set
     */
    public void setWarningCheck(boolean warningCheck) {
        this.warningCheck = warningCheck;
    }

    /**
     * @return the warning
     */
    public String getWarning() {
        return warning;
    }

    /**
     * @param warning the warning to set
     */
    public void setWarning(String warning) {
        this.warning = warning;
    }

    private boolean start;
    private int plantsetSize;

    //Game variables
    //Player
    private String playerName;
    private float money;
    private int energy;
    private int day;
    private String[] fieldDetails;
    private int score;
//    private int gameState;

    //Field
    private String[][] plants;
    private String[][] plantsDescription;
    //Shop
    private String shop;
    //Unlock
    private String unlock;
    private String unlockCost;

    //Shop information
    private int shopSize;
    private String[] shopText;
    private boolean shopStart;
    private boolean shopUpdate;

    private int unlockSize;
    private String[] unlockText;
    private boolean unlockStart;
    private boolean unlockUpdate;

    private boolean end;
    private boolean newGame;
    //not needed remove previous game
    private boolean previousGame;
    private boolean loadGame;
    private String[] loadText;
    private boolean mainGame;

    private boolean fieldUpdate;
    private boolean fieldPick;
    private String[][] viewPlants;
    private Boolean[][] waterPlants;
    private Boolean[][] pollinatePlants;

    private boolean saveUpdate;
    private boolean saveStart;
    private String[] saveText;

    private boolean infoUpdate;
    private String[] infoText;

    private boolean endGame;

    //Stores information about highscores.
    private boolean checkScores;
    private int[] scores;
    private String[] names;

    //stores player.tostring
    private String player;

    private String warning;
    private boolean warningCheck;

    public Data() {
        start = true;
        viewPlants = new String[3][3];
        waterPlants = new Boolean[3][3];
        pollinatePlants = new Boolean[3][3];
        //Set up defaults
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                viewPlants[i][j] = "";
                waterPlants[i][j] = false;
                pollinatePlants[i][j] = false;
            }
        }
        warningCheck = false;

    }

    /**
     * @return the money
     */
    public float getMoney() {
        return money;
    }

    /**
     * @param money the money to set
     */
    public void setMoney(float money) {
        this.money = money;
    }

    /**
     * @return the energy
     */
    public int getEnergy() {
        return energy;
    }

    /**
     * @param energy the energy to set
     */
    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * @return the day
     */
    public int getDay() {
        return day;
    }

    /**
     * @param day the day to set
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * @return the playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * @param playerName the playerName to set
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    /**
     * @return the infoUpdate
     */
    public boolean isInfoUpdate() {
        return infoUpdate;
    }

    /**
     * @param infoUpdate the infoUpdate to set
     */
    public void setInfoUpdate(boolean infoUpdate) {
        this.infoUpdate = infoUpdate;
    }

    /**
     * @return the infoText
     */
    public String[] getInfoText() {
        return infoText;
    }

    /**
     * @param infoText the infoText to set
     */
    public void setInfoText(String[] infoText) {
        this.infoText = infoText;
    }

    /**
     * @return the saveUpdate
     */
    public boolean isSaveUpdate() {
        return saveUpdate;
    }

    /**
     * @param saveUpdate the saveUpdate to set
     */
    public void setSaveUpdate(boolean saveUpdate) {
        this.saveUpdate = saveUpdate;
    }

    /**
     * @return the saveStart
     */
    public boolean isSaveStart() {
        return saveStart;
    }

    /**
     * @param saveStart the saveStart to set
     */
    public void setSaveStart(boolean saveStart) {
        this.saveStart = saveStart;
    }

    /**
     * @return the saveText
     */
    public String[] getSaveText() {
        return saveText;
    }

    /**
     * @param saveText the saveText to set
     */
    public void setSaveText(String[] saveText) {
        this.saveText = saveText;
    }

    /**
     * @return the unlockText
     */
    public String[] getUnlockText() {
        return unlockText;
    }

    /**
     * @param unlockText the unlockText to set
     */
    public void setUnlockText(String[] unlockText) {
        this.unlockText = unlockText;
    }

    /**
     * @return the unlockStart
     */
    public boolean isUnlockStart() {
        return unlockStart;
    }

    /**
     * @param unlockStart the unlockStart to set
     */
    public void setUnlockStart(boolean unlockStart) {
        this.unlockStart = unlockStart;
    }

    /**
     * @return the fieldPick
     */
    public boolean isFieldPick() {
        return fieldPick;
    }

    /**
     * @param fieldPick the fieldPick to set
     */
    public void setFieldPick(boolean fieldPick) {
        this.fieldPick = fieldPick;
    }

    /**
     * @return the fieldUpdate
     */
    public boolean isFieldUpdate() {
        return fieldUpdate;
    }

    /**
     * @param fieldUpdate the fieldUpdate to set
     */
    public void setFieldUpdate(boolean fieldUpdate) {
        this.fieldUpdate = fieldUpdate;
    }

    /**
     * @return the start
     */
    public boolean isStart() {
        return start;
    }

    /**
     * @param start the start to set
     */
    public void setStart(boolean start) {
        this.start = start;
    }

    /**
     * @return the plantsetSize
     */
    public int getPlantsetSize() {
        return plantsetSize;
    }

    /**
     * @param plantsetSize the plantsetSize to set
     */
    public void setPlantsetSize(int plantsetSize) {
        this.plantsetSize = plantsetSize;
    }

    /**
     * @return the shopSize
     */
    public int getShopSize() {
        return shopSize;
    }

    /**
     * @param shopSize the shopSize to set
     */
    public void setShopSize(int shopSize) {
        this.shopSize = shopSize;
    }

    /**
     * @return the unlockSize
     */
    public int getUnlockSize() {
        return unlockSize;
    }

    /**
     * @param unlockSize the unlockSize to set
     */
    public void setUnlockSize(int unlockSize) {
        this.unlockSize = unlockSize;
    }

    /**
     * @return the unlockUpdate
     */
    public boolean isUnlockUpdate() {
        return unlockUpdate;
    }

    /**
     * @param unlockUpdate the unlockUpdate to set
     */
    public void setUnlockUpdate(boolean unlockUpdate) {
        this.unlockUpdate = unlockUpdate;
    }

    /**
     * @return the end
     */
    public boolean isEnd() {
        return end;
    }

    /**
     * @param end the end to set
     */
    public void setEnd(boolean end) {
        this.end = end;
    }

    /**
     * @return the newGame
     */
    public boolean isNewGame() {
        return newGame;
    }

    /**
     * @param newGame the newGame to set
     */
    public void setNewGame(boolean newGame) {
        this.newGame = newGame;
    }

    /**
     * @return the previousGame
     */
    public boolean isPreviousGame() {
        return previousGame;
    }

    /**
     * @param previousGame the previousGame to set
     */
    public void setPreviousGame(boolean previousGame) {
        this.previousGame = previousGame;
    }

    /**
     * @return the loadGame
     */
    public boolean isLoadGame() {
        return loadGame;
    }

    /**
     * @param loadGame the loadGame to set
     */
    public void setLoadGame(boolean loadGame) {
        this.loadGame = loadGame;
    }

    /**
     * @return the loadText
     */
    public String[] getLoadText() {
        return loadText;
    }

    /**
     * @param loadText the loadText to set
     */
    public void setLoadText(String[] loadText) {
        this.loadText = loadText;
    }

    /**
     * @return the mainGame
     */
    public boolean isMainGame() {
        return mainGame;
    }

    /**
     * @param mainGame the mainGame to set
     */
    public void setMainGame(boolean mainGame) {
        this.mainGame = mainGame;
    }

    /**
     * @return the shopText
     */
    public String[] getShopText() {
        return shopText;
    }

    /**
     * @param shopText the shopText to set
     */
    public void setShopText(String[] shopText) {
        this.shopText = shopText;
    }

    /**
     * @return the shopStart
     */
    public boolean isShopStart() {
        return shopStart;
    }

    /**
     * @param shopStart the shopStart to set
     */
    public void setShopStart(boolean shopStart) {
        this.shopStart = shopStart;
    }

    /**
     * @return the shopUpdate
     */
    public boolean isShopUpdate() {
        return shopUpdate;
    }

    /**
     * @param shopUpdate the shopUpdate to set
     */
    public void setShopUpdate(boolean shopUpdate) {
        this.shopUpdate = shopUpdate;
    }

    /**
     * @return the viewPlants
     */
    public String[][] getViewPlants() {
        return viewPlants;
    }

    /**
     * @param viewPlants the viewPlants to set
     */
    public void setViewPlants(String[][] viewPlants) {
        this.viewPlants = viewPlants;
    }

    /**
     * @return the waterPlants
     */
    public Boolean[][] getWaterPlants() {
        return waterPlants;
    }

    /**
     * @param waterPlants the waterPlants to set
     */
    public void setWaterPlants(Boolean[][] waterPlants) {
        this.waterPlants = waterPlants;
    }

    /**
     * @return the pollinatePlants
     */
    public Boolean[][] getPollinatePlants() {
        return pollinatePlants;
    }

    /**
     * @param pollinatePlants the pollinatePlants to set
     */
    public void setPollinatePlants(Boolean[][] pollinatePlants) {
        this.pollinatePlants = pollinatePlants;
    }

    /**
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(String player) {
        this.player = player;
    }

    /**
     * @return the scores
     */
    public int[] getScores() {
        return scores;
    }

    /**
     * @param scores the scores to set
     */
    public void setScores(int[] scores) {
        this.scores = scores;
    }

    /**
     * @return the names
     */
    public String[] getNames() {
        return names;
    }

    /**
     * @param names the names to set
     */
    public void setNames(String[] names) {
        this.names = names;
    }

    /**
     * @return the endGame
     */
    public boolean isEndGame() {
        return endGame;
    }

    /**
     * @param endGame the endGame to set
     */
    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    /**
     * @return the fieldDetails
     */
    public String[] getFieldDetails() {
        return fieldDetails;
    }

    /**
     * @param fieldDetails the fieldDetails to set
     */
    public void setFieldDetails(String[] fieldDetails) {
        this.fieldDetails = fieldDetails;
    }

    /**
     * @return the unlock
     */
    public String getUnlock() {
        return unlock;
    }

    /**
     * @param unlock the unlock to set
     */
    public void setUnlock(String unlock) {
        this.unlock = unlock;
    }

    /**
     * @return the unlockCost
     */
    public String getUnlockCost() {
        return unlockCost;
    }

    /**
     * @param unlockCost the unlockCost to set
     */
    public void setUnlockCost(String unlockCost) {
        this.unlockCost = unlockCost;
    }

    /**
     * @return the shop
     */
    public String getShop() {
        return shop;
    }

    /**
     * @param shop the shop to set
     */
    public void setShop(String shop) {
        this.shop = shop;
    }

    /**
     * @return the plantsDescription
     */
    public String[][] getPlantsDescription() {
        return plantsDescription;
    }

    /**
     * @param plantsDescription the plantsDescription to set
     */
    public void setPlantsDescription(String[][] plantsDescription) {
        this.plantsDescription = plantsDescription;
    }

    /**
     * @return the plants
     */
    public String[][] getPlants() {
        return plants;
    }

    /**
     * @param plants the plants to set
     */
    public void setPlants(String[][] plants) {
        this.plants = plants;
    }
}
