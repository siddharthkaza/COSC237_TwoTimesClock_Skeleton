/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.towson.cosc237;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.Calendar;
import javax.swing.JPanel;

/**
 * This is the class that actually draws the clock and makes it tick. The time should be obtained
 * from an object of the Time class. 
 * @author skaza
 * Base code for the clock face from http://wiki.scn.sap.com/wiki/display/Snippets/Implmenting+a+GUI+analog+timer+using+java+Swing
 */
class Clock extends JPanel {
    
    //>>>>>>>>>>>>> This class uses stores time in its own instance variables
    //the developer was not aware that she/he could you an object of the Time class
    //to store the same. Did not take COSC 237. 
    //Your job is to make this class use an object of time that you pass into the constructor.
    
    private int hrs;
    private int mins;
    private int secs;
    private int millis;
    private int ampm;
    

    private static final int spacing = 10;
    private static final float threePi = (float) (3.0 * Math.PI);
    //  Angles for the trigonometric functions are measured in radians.
    //  The following in the number of radians per sec or min.
    private static final float radPerSecMin = (float) (Math.PI / 30.0);
    private int size; // height and width of clock face
    private int centerX; // x coord of middle of clock
    private int centerY; // y coord of middle of clock
    private BufferedImage clockImage;
    private javax.swing.Timer t;


    /**
     * //>>>>>>>>>>>>>>>>> You should modify this constructor to take an object of Time class as parameter
     * Constructor. Takes an object of the Time class that it will use for showing the time
     * @param 
     */

    public Clock() {
        this.setPreferredSize(new Dimension(300, 300));
        this.setBackground(Color.white);
        this.setForeground(Color.black);
        t = new javax.swing.Timer(1000,
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        update();
                    }
                });
    }
    /**
     * Replace the default update so that the plain background doesn't get drawn.
     */

    public void update() {
        this.repaint();
    }

    public void start() {
        t.start(); // start the timer
    }

    public void stop() {
        t.stop(); // start the timer
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g); // paint background, borders
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        
        // The panel may have been resized, get current dimensions
        int w = getWidth();
        int h = getHeight();
        size = ((w < h) ? w : h) - 2 * spacing;
        size -= 50; //reducing size to make space for text below clock face
        
        centerX = size / 2 + spacing;
        centerY = size / 2 + spacing;
        
        // Create the clock face background image if this is the first time,
        // or if the size of the panel has changed
        if (clockImage == null
                || clockImage.getWidth() != w
                || clockImage.getHeight() != h) {
            clockImage = (BufferedImage) (this.createImage(w, h));
            
        // now get a graphics context from this image
            Graphics2D gc = clockImage.createGraphics();
            gc.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            drawClockFace(gc);
        }
        
        // >>>>>>>>>>>>>>>>>>>>> Call the update method of the time object instead of the code to update time below
        // However you will have to write the code to update the time in the Time class. Use the code below and replicate it 
        // in the Time class. You will have to modify the code to return time with offset. 
        // Please keep in mind that an hour offset of over 12 hours will change the AM to PM and vice versa. 

        Calendar now = Calendar.getInstance();
        hrs = now.get(Calendar.HOUR);      
        mins = now.get(Calendar.MINUTE);
        secs = now.get(Calendar.SECOND);
        millis = now.get(Calendar.MILLISECOND);
        ampm = now.get(Calendar.AM_PM);
        //>>>>>>>>>>>>>>>>>>>>>>>
        
        // Draw the clock face from the precomputed image
        g2.drawImage(clockImage, null, 0, 0);
        
        // Draw the clock hands
        drawClockHands(g);
        
        //>>>>>>>>>>>>>>>>>>>>> Write the time in standard form and military form
        //You will need to use a method called drawString in the Graphics2D class for this. 
        //The object for Graphics2D is g2. Find the method in the Java API.
        //Notice that it takes three parameters, the String you want to print, and X, and Y coordinates 
        //on where you need to draw it on the canvas. 
        //The coordinates for the standard time are (90,h-15) and the second are (90,h-30)
        //>>>>>>>>>>>>>>>>> write code to draw the strings here

        
    }
    
    /**
     * The hour, minute, and second hand
     * @param g 
     */
    private void drawClockHands(Graphics g) {
        int secondRadius = size / 2;
        int minuteRadius = secondRadius * 3 / 4;
        int hourRadius = secondRadius / 2;
        
        // second hand
        float fseconds = secs + (float) millis / 1000;
        float secondAngle = threePi - (radPerSecMin * fseconds);
        drawRadius(g, centerX, centerY, secondAngle, 0, secondRadius);

        // minute hand
        float fminutes = (float) (mins + fseconds / 60.0);
        float minuteAngle = threePi - (radPerSecMin * fminutes);
        drawRadius(g, centerX, centerY, minuteAngle, 0, minuteRadius);

        // hour hand
        float fhours = (float) (hrs + fminutes / 60.0);
        float hourAngle = threePi - (5 * radPerSecMin * fhours);
        drawRadius(g, centerX, centerY, hourAngle, 0, hourRadius);
    }

    /**
     * draws the oval and the hour marks
     * @param g 
     */
    private void drawClockFace(Graphics g) {
        // clock face
        g.setColor(Color.lightGray);
        g.fillOval(spacing, spacing, size, size);
        g.setColor(Color.black);
        g.drawOval(spacing, spacing, size, size);
        
        // tic marks
        for (int sec = 0; sec < 60; sec++) {
            int ticStart;
            if (sec % 5 == 0) {
                ticStart = size / 2 - 10;
            } else {
                ticStart = size / 2 - 5;
            }
            drawRadius(g, centerX, centerY, radPerSecMin * sec, ticStart, size / 2);
        }
    }

    private void drawRadius(Graphics g, int x, int y, double angle,
            int minRadius, int maxRadius) {
        float sine = (float) Math.sin(angle);
        float cosine = (float) Math.cos(angle);
        int dxmin = (int) (minRadius * sine);
        int dymin = (int) (minRadius * cosine);
        int dxmax = (int) (maxRadius * sine);
        int dymax = (int) (maxRadius * cosine);
        g.drawLine(x + dxmin, y + dymin, x + dxmax, y + dymax);
    }
}
