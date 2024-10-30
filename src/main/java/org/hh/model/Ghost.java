package org.hh.model;

import processing.core.PApplet;
import processing.core.PImage;

import java.util.Random;

public class Ghost {

    private static final int DEFAULT_GHOST_Y = 0;
    private static final int DEFAULT_GHOST_SPEED = 10;

    private PImage ghostImg;
    private String direction;
    private int x;
    private int y;
    private int speed = 5;
    private int transparency = 125;
    private PApplet p;

    public Ghost(PApplet p) {
        this(p, new Random().nextBoolean() ? "left" : "right");
    }
    public Ghost(PApplet p, String direction) {
        this(p, DEFAULT_GHOST_Y, DEFAULT_GHOST_SPEED, direction);
    }
    public Ghost(PApplet p, int y, int speed, String direction) {
        this.p = p;
        ghostImg = p.loadImage( "images/ghost.png" );
        ghostImg.resize(150, 250);

        this.direction = direction;
        this.speed = speed;
        this.x = -ghostImg.width;
        this.y = y;
    }

    // 0 = most transparent; 100 = most opaque
    public void setGhostTransparency( int transparency ){
        // Normalize value from 0-255
        this.transparency = ( 255 - ( ( transparency * 255 ) / 100 ) );
    }

    public void draw() {

        p.pushMatrix();

        // Makes ghost transparent
        p.tint(255, this.transparency);

        if( direction.equalsIgnoreCase("left") ){
            // Ghost goes right to left

            // Flip on X axis
            p.scale(-1,1);
            p.image(ghostImg, -this.x, this.y);

            this.x -= speed;

            if( this.x < -ghostImg.width ){
                this.x = p.width + ghostImg.width;

            }
        } else {
            // Ghost goes left to right

            p.image( ghostImg, this.x, this.y );

            this.x += speed;

            if ( this.x > p.width ) {
                this.x = -ghostImg.width;
            }
        }

        p.popMatrix();
    }
}
