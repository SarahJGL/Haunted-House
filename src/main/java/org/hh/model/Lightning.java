package org.hh.model;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Lightning {
    private List<PVector> vectors;
    private boolean lightningFlash = false;
    private PApplet p;

    public Lightning(PApplet p){
        this.p = p;
    }

    public void setLightningFlash(boolean flash) {
        this.lightningFlash = flash;
    }

    /**
     * Draw lightning
     */
    public void draw() {
        drawLightning();

        if (this.lightningFlash) {
            p.filter(PApplet.INVERT);
        }
    }

    /**
     * Draw lightning at random times.
     * smaller input = more frequent lightning
     * larger input  = less frequent lightning
     * @param randDelay, min 5
     */
    public void draw(int randDelay) {
        if (p.random(randDelay) < 5) {
            drawLightning();

            if ( this.lightningFlash ) {
                p.filter(PApplet.INVERT);
            }
        }
    }

    private void drawLightning() {

        p.pushStyle();
        p.noStroke();

        // Vectors for top and bottom of lightning
        PVector topVector = new PVector(p.random(p.width), 0);
        PVector bottomVector = new PVector(p.random(p.width), p.height);

        // Get random points between
        vectors = lightningVectors(topVector.x, topVector.y, bottomVector.x, bottomVector.y);
        vectors.add(bottomVector);

        p.stroke(64, 46, 255, 32);
        p.strokeWeight(32);
        drawLightningLine(vectors);

        p.stroke(64, 64, 255, 32);
        p.strokeWeight(16);
        drawLightningLine(vectors);

        p.stroke(128, 128, 255, 32);
        p.strokeWeight(8);
        drawLightningLine(vectors);

        p.stroke(255, 255, 255, 255);
        p.strokeWeight(4);
        drawLightningLine(vectors);

        p.popStyle();
    }

    private List<PVector> lightningVectors(float startX, float startY, float endX, float endY) {

        List<PVector> lightningVectors = new ArrayList<>();

        int numPointsPerBias = 50;
        float xNegBias = 0;
        float xPosBias = 0;
        float lightningDx = 5;
        float lightningDy = 5;
        float currX = startX;
        float currY = startY;

        while(currY < p.height + 10){

            lightningVectors.add(new PVector(currX, currY));

            float xc = p.random(xNegBias - lightningDx, xPosBias + lightningDx);
            float yc = p.random(-2, lightningDy);

            /*
             * Add bias periodically so lightning isn't always vertical
             */
            if (lightningVectors.size() % numPointsPerBias == 0){
                float bias = p.random(-1, 1);
                if (bias > 0) {
                    xPosBias = bias * lightningDx;
                    xNegBias = 0;
                } else {
                    xPosBias = 0;
                    xNegBias = bias * lightningDx;
                }
            }

            currX += xc;
            currY += yc;
        }

        return lightningVectors;
    }

    private void drawLightningLine(List<PVector> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            PVector p1 = points.get(i);
            PVector p2 = points.get(i + 1);
            p.line(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
