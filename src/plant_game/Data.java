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
    //field
    //money
    //so on so forth

    //score
    //FROM THE SAVE GAME FILE 
    //Player name
    //money
    //energy
    //day
    //score
    //plant dets x9
    //plants
    //plant select
    //unlock
    //unlock price (may not be working correctly check this is changing correct)
    //Two null lines
    /*Alternativly maybe we go by whats notified in the view panel
    
    So plant/inital view/pick are conditions
    inital save
    inital unlock
    inital view
    
    
    So we could have a game loaded variable which indicates the shop/unlock and player can be created
    
    

     */
    private boolean start;
    private int plantsetSize;

    //Shop information
    private int shopSize;
    private String[] shopText;
    private boolean shopStart;
    private boolean shopUpdate;

    private int unlockSize;
    private boolean unlockContent;
    private boolean end;
    private boolean newGame;
    private boolean previousGame;
    private boolean loadGame;
    private String[] loadText;
    private boolean mainGame;

    
    public Data()
    {
        start=true;
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
     * @return the unlockContent
     */
    public boolean isUnlockContent() {
        return unlockContent;
    }

    /**
     * @param unlockContent the unlockContent to set
     */
    public void setUnlockContent(boolean unlockContent) {
        this.unlockContent = unlockContent;
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

}
