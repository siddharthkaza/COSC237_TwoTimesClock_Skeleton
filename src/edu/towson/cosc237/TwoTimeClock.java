/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.towson.cosc237;

/**
 *
 * The following is an analog and digital clock that shows time
 * at two places in the world (after you are done with assignment). 
 * 
 * 
 * @author sidd
 * Base code for the clock face from http://wiki.scn.sap.com/wiki/display/Snippets/Implmenting+a+GUI+analog+timer+using+java+Swing
 */

/* Notes: 
    Step 1. You should first run this program so it shows one clock. Get some idea of how it works
    Step 2. Then, make it use your Time class (you will have to add a new Time class to this project). 
        So it become one clock that uses an object of your Time class.
    Step 3. Then, modify it so it can use two objects. 
    
    JAVA API is your freind! Look up the classes and methods you are using. http://docs.oracle.com/javase/7/docs/api/ 
 */
import java.awt.*;
import javax.swing.*;


public class TwoTimeClock extends JFrame {

    Clock clockFace;

    public static void main(String[] args) {
        JFrame windo = new TwoTimeClock();
        windo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windo.setVisible(true);
    }

    /**
     * empty constructor. initializes instance variables, draws the clocks, 
     * and starts them
     */
    public TwoTimeClock() {
        Container content = this.getContentPane();
        content.setLayout(new BorderLayout());
        
        
        clockFace = new Clock();
        
        content.add(clockFace, BorderLayout.WEST);  //BorderLayout.WEST and BorderLayout.EAST decide the clocks lie in the left or right of box
        //hint if you add another clockFace to the content object above, then the canvas will grow in size
        
        this.setTitle("Baltimore                                New Delhi");
        this.pack();
        clockFace.start();
    }
}


