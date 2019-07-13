package com.mikejack.graphics;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Sprite {

    public static final Sprite DEFAULT = new Sprite("/defaultSprite.png");
    private int width, height;
    private int pixels[];
    
    public Sprite(String path) {
	BufferedImage image = null;
	try {
	    image = ImageIO.read(Image.class.getResourceAsStream(path));
	} catch (IOException e) {
	    e.printStackTrace();
	}
	width = image.getWidth();
	height = image.getHeight();
	pixels = image.getRGB(0, 0, width, height, null, 0, width);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getPixels() {
        return pixels;
    }
    
}