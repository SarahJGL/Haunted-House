package org.hh.house;

import org.hh.model.Ghost;
import org.hh.model.Lightning;
import org.hh.model.Pumpkin;
import processing.core.PApplet;
import processing.core.PImage;

public class HauntedHouse extends PApplet {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private PImage scaryHouse;

    /*
     * TODO: 1. Create an instance variable for a Ghost. Do not initialize it.
     *  Hint: look in the model package for the Ghost class
     */
    private Ghost ghost;

    /*
     * TODO: 2. Create an instance variable for a Pumpkin. Do not initialize it.
     */
    private Pumpkin pumpkin;

    /*
     * TODO: 3. Create an instance variable for Lightning. Do not initialize it.
     */
    private Lightning lightning;

    public void settings() {
        size(WIDTH, HEIGHT);
    }

    public void setup() {
        this.scaryHouse = loadImage("images/scaryHouse1.jpg");
        this.scaryHouse.resize(WIDTH, HEIGHT);

        /*
         * TODO: 4. Assign your ghost instance variable to a new Ghost object
         *  E.g. this.ghost = new Ghost(this);
         */
        this.ghost = new Ghost(this);

        /*
         * TODO: 5. Assign your pumpkin instance variable to a new Pumpkin object
         */
        this.pumpkin = new Pumpkin(this);
        this.pumpkin.bounce();

        /*
         * TODO: 6. Assign your lightning instance variable to a new Lightning object
         */
        this.lightning = new Lightning(this);
    }

    public void draw() {

        drawBackground(this.scaryHouse);

        /*
         * TODO: 7. Call the ghost's draw() method
         */
        ghost.draw();

        /*
         * TODO: 8. Call the pumpkin's draw() method
         */
        pumpkin.draw();

        /*
         * TODO: 9. Call the ghost's draw() method
         */
        if(mousePressed){
            lightning.draw();
        }

        /*
         * TODO: 10. Only draw lightning if the mouseIsPressed
         *  E.g. if(mousePressed){
         */
    }




    // ---------------------------------------------------------
    // Here are some other methods you can try:
    //  pumpkin.setPumpkinColor();
    //  pumpkin.bounce();
    //  pumpkin.stop();
    //  pumpkin.setBounceHeight();
    //  pumpkin.moveRight();
    //  pumpkin.moveLeft();
    //  ghost.setGhostTransparency();
    //  lightning.setLightningFlash();
    // ---------------------------------------------------------

    void drawBackground(PImage bgImage) {
        background(bgImage);
        drawFloor();
    }

    void drawFloor() {
        int floorHeight = 30;

        this.pushStyle();

        fill(10, 10, 30);
        rect(0, height - floorHeight, width, floorHeight);

        this.popStyle();
    }

    // Call this method at the very bottom of the draw() method!
    void drawGrayscale(boolean grayscaleEnabled) {
        if (grayscaleEnabled) {
            filter(GRAY);
        }
    }
}
