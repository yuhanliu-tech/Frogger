package org.cis120.frogger;

import javax.swing.*;
import java.io.*;

public class GameSaver {

    /**
     * object that writes game state to file
     */

    final JOptionPane err;

    public GameSaver() {

        err = new JOptionPane();

    }

    public int[] toArr(String list) {
        int[] arr = new int[list.length()];

        String[] arrOfStr = list.split(",", 0);

        for (int i = 0; i < arrOfStr.length; i++) {
            arr[i] = Integer.parseInt(arrOfStr[i]);
        }

        return arr;
    }

    private String saveList(int[] list) {

        String listStr;

        try {
            listStr = "" + list[0];

            for (int i = 1; i < list.length ; i++) {
                listStr += "," + list[i];
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            listStr = "";
        }

        if (listStr.equals("")) {
            listStr = "0";
        }

        return listStr;
    }

    public void updateFile(int ff, int fX, int fY, int lives, String bin, int[] list1, int[] list2,
                           int[] list3, int[] list4, int[] list5, int[] rist1,
                           int[] rist2, int[] rist3, int[] rist4) {

        try {
            FileWriter saver = new FileWriter("files/state.txt");
            saver.write(ff + "\n" + fX + "\n" + fY + "\n" +
                lives + "\n" + bin + "\n" + saveList(list1) + "\n"
                + saveList(list2) + "\n" + saveList(list3) + "\n" + saveList(list4) +
                    "\n" + saveList(list5) + "\n"
                + saveList(rist1) + "\n" + saveList(rist2) + "\n" +
                    saveList(rist3) + "\n" + saveList(rist4));
            saver.close();
        } catch (IOException e) {
            err.showMessageDialog(new JFrame("ERROR MESSAGE"), "FILE ERROR", "ERROR",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

}
