/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package plant_game;

import static java.lang.Integer.parseInt;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Observable;
import java.util.StringTokenizer;

/**
 * Class for purchasing new plants
 *
 * @author breco
 */
public class UnlockShop extends Observable implements ToFile {

    private int truffles;
    private int tulip;
    private int saffron;
    private HashMap<String, Integer> unlocks;
    private ArrayList<Plant> view;

    /**
     * Sets up the default unlock shop truffles,saffron and tulips in the unlock
     * shop.
     */
    public UnlockShop() {

        unlocks = new HashMap<String, Integer>();
        view = new ArrayList<Plant>();
        //Establishes unlock shop content and prices
        truffles = 200;
        tulip = 30;
        saffron = 400;
        view.add(new Saffron());
        view.add(new Truffle());
        view.add(new Tulip());

        unlocks.put("saffron", saffron);
        unlocks.put("truffle", truffles);
        unlocks.put("tulip", tulip);
    }

    /**
     * Creates an unlock shop with details stored in an ArrayList.
     *
     * @param details
     */
    public UnlockShop(ArrayList<String> details) {
        view = new ArrayList<Plant>();
        unlocks = new HashMap<String, Integer>();

        StringTokenizer st = new StringTokenizer(details.get(0));
        String holder = "";

        while (st.hasMoreTokens()) {
            holder = st.nextToken();
            System.out.println("Holder part 1:" + holder);

            //Cycles through all plants in the plant set
            for (PlantSet p : PlantSet.values()) {
                //When a string matches the toString of a plant in the enum set adds the plant to the unlock view.
                if (p.toString().equalsIgnoreCase(holder)) {
                    view.add(p.getPlant());

                }
            }

        }

        //Creates a new tokenizer that unlocks plants the correct plants in the view.
        st = new StringTokenizer(details.get(1));
        int count = 0;
        while (st.hasMoreTokens()) {

            holder = st.nextToken();
            for (Plant p : view) {
                System.out.println("Plant" + p);
                System.out.println("Cost" + holder);
//                unlocks.put(p.toString(), parseInt(holder));

            }
            unlocks.put(view.get(count).toString(), parseInt(holder));

            count++;
        }
//        System.out.println(unlocks);
    }

    /**
     * Purchases an item from the unlock shop.
     *
     * Takes user inputed selection and deducts the appropriate amount of money
     * from the player.
     *
     * @param player Player purchasing from shop.
     * @param plantselection The Plant selection the player has access to.
     * @param Selection The player selection.
     * @return
     */
    public void price(Player player, PlantSelection plantselection, int Selection) {
        //Gets the place
        try {
            if (Selection < 1 || Selection > view.size()) {
                throw new ArrayIndexOutOfBoundsException("Selection out of bounds. Current Selection=" + Selection);
            }
            player.setMoney(player.getMoney() - unlocks.get(view.get(Selection - 1).toString()));
            plantselection.unlock(view.get(Selection - 1));
            view.remove(Selection - 1);
        } catch (MoneyException me) {
            System.out.println("Unlock failed you don't have enough money");
        }

    }

    public String toView(int i) {
        String toView;
        //Want to get each unlock store object as a string with it's price and number
        toView = view.get(i).toString() + " " + unlocks.get(view.get(i).toString()) + "$ ";

        return toView;
    }

    public String toData(int i) {
        String toData;
        toData = view.get(i).toString();
        return toData;
    }

    /**
     * Returns the size of the unlocks
     *
     * @return
     */
    public int size() {
        return view.size();
    }

    /**
     * Prints the name and cost of all items currently in the UnlockShop
     *
     * @return
     */
    public String view() {
        String viewing = "";
        //Prints out the name and cost of each item in the unlock shop.
        for (int i = 0; i < view.size(); i++) {
            viewing += (i + 1) + "." + view.get(i).toString() + " " + unlocks.get(view.get(i).toString()) + "$ ";
        }
        return viewing;
    }

    /**
     *
     * @return A string of all unlocks currently available to the player
     */
    @Override
    public String toFile() {
        String tofile = "";
        for (int i = 0; i < view.size(); i++) {
            tofile += view.get(i).toString() + " ";
        }
        tofile += "\n";

        for (int i = 0; i < view.size(); i++) {
            tofile += unlocks.get(view.get(i).toString()) + " ";
        }
        return tofile;
    }

    @Override
    public String toString() {
        String viewing = "";
        for (int i = 0; i < view.size(); i++) {
            viewing += (i + 1) + "." + view.get(i).toString() + " " + unlocks.get(view.get(i).toString()) + "$ ";
        }
        return viewing;
    }
}
