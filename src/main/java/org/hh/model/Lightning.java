package org.hh.model;

import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;
import java.util.List;

public class Lightning {
    private float chaos = 0.25f;
    private List<PVector> vectors;
    private boolean lightningFlash = false;
    private PApplet p;

    public Lightning(PApplet p){
        this.p = p;
    }

    public void setLightningFlash(boolean flash) {
        this.lightningFlash = flash;
    }

    // Draw lightning immediately when called
    public void draw() {
        drawLightning();

        if ( this.lightningFlash ) {
            p.filter(PApplet.INVERT);
        }
    }

    // Draw lightning at random times.
    //   smaller input = more frequent lightning
    //   larger input  = less frequent lightning
    public void draw( int randDelay ) {
        if ( p.random( randDelay ) < 5 ) {
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
        vectors = lightningVectors(topVector.x, topVector.y, bottomVector.x, bottomVector.y, chaos);
        vectors.add(bottomVector);

        p.stroke(64, 46, 255, 32);
        p.strokeWeight(32);
        drawChaoticLine(vectors);

        p.stroke(64, 64, 255, 32);
        p.strokeWeight(16);
        drawChaoticLine(vectors);

        p.stroke(128, 128, 255, 32);
        p.strokeWeight(8);
        drawChaoticLine(vectors);

        p.stroke(255, 255, 255, 255);
        p.strokeWeight(4);
        drawChaoticLine(vectors);

        p.popStyle();
    }

    private List<PVector> lightningVectors(float startX, float startY, float endX, float endY, float chaos) {

        List<PVector> lightningVectors = new ArrayList<>();

        // TODO: Fix my simplistic algorithm
        float currX = startX;
        float currY = startY;
//        //float dx = (endX - currX) / 2;
//
//        while(currY < p.height + 40){
//
//            lightningVectors.add(new PVector(currX, currY));
//
//            float ch = p.random(-chaos, chaos);
//            float xc = (40 * ch);
//            float yc = PApplet.abs(40 * ch);
//
//            currX += xc;
//            currY += yc;
//            //dx = (endX - currX) / 2;
//        }
//
//        return lightningVectors;

        // A wayyyy better algorithm:
        // https://developer.download.nvidia.com/SDK/10/direct3d/Source/Lightning/doc/lightning_doc.pdf
        float dx = endX - currX;
        float dy = endY - currY;
        float magnitude = PApplet.sqrt((dx * dx) + (dy * dy));

        if (magnitude > 10) {
            float ch = (p.randomGaussian() * chaos) / 2.0f;

            float xc = ((currX+endX)/2) - dy*ch;
            float yc = ((currY+endY)/2) + dx*ch;
            lightningVectors.addAll(lightningVectors(currX, currY, xc, yc, chaos));
            lightningVectors.addAll(lightningVectors(xc, yc, endX, endY, chaos));
        } else {
            lightningVectors.add(new PVector(currX, currY));
        }

        return lightningVectors;
    }

    private void drawChaoticLine(List<PVector> points) {
        for (int i = 0; i < points.size() - 1; i++) {
            PVector p1 = points.get(i);
            PVector p2 = points.get(i+1);
            p.line(p1.x, p1.y, p2.x, p2.y);
        }
    }
}
