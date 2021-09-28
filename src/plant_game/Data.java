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

    private boolean start;
    private int plantsetSize;

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
    
    private String player;
    
    
    public Data() {
        start = true;
        viewPlants=new String[3][3];
        waterPlants=new Boolean[3][3];
        pollinatePlants=new Boolean[3][3];
        //Set up defaults
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                viewPlants[i][j] ="";
                waterPlants[i][j] = false;
                pollinatePlants[i][j] = false;
            }
        }
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
        this.setLoadText(loadText);
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
}
