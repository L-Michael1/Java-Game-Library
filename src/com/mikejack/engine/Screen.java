package com.mikejack.engine;

import java.awt.image.DataBufferInt;

import com.mikejack.graphics.Font;
import com.mikejack.graphics.Sprite;

public class Screen {

    private GameContainer gc;
    private int pW, pH;
    private int pixels[];

    private Font font = Font.STANDARD;
    
    public Screen(GameContainer gc) {
	this.gc = gc;
	pW = gc.getImageWidth();
	pH = gc.getImageHeight();
	pixels = ((DataBufferInt) gc.getImage().getRaster().getDataBuffer()).getData();
    }

    private void setPixel(int x, int y, int colour) {
	if (x < 0 || x >= pW || y < 0 || y >= pH) {
	    return;
	}
	pixels[x + y * pW] = colour;
    }
    
    
    public void drawText(String text, int offX, int offY, int colour) {
    	
    	text = text.toUpperCase();
    	int offset = 0;
    	
    	for(int i = 0; i < text.length(); i++) {
    		int unicode = text.codePointAt(i) - 32;
    		
    		for(int y = 0; y < font.getFontImage().getHeight(); y++) {
    			
    			for(int x = 0; x < font.getWidths()[unicode]; x++) {
    				
    				if(font.getFontImage().getPixels()[(x + font.getOffsets()[unicode]) + y * font.getFontImage().getWidth()] == 0xffffffff) {
    					setPixel(x + offX + offset, y + offY , colour);
    				}
    			}
    		}
    		
    		offset += font.getWidths()[unicode];
    	}
    	
    }
    

    public void drawSprite(Sprite sprite, int offX, int offY) {
	int newX = 0;
	int newY = 0;
	int newWidth = sprite.getWidth();
	int newHeight = sprite.getHeight();

	// Off Screen
	if (offX < -newWidth)return;
	if (offY < -newHeight)return;
	if (offX >= pW) return;
	if (offY >= pH) return;
	
	// Clipping Sprites
	if (offX < 0) newX -= offX;
	if (offY < 0) newY -= offY;
	if (offX + newWidth >= pW) newWidth -= newWidth + offX - pW;
	if (offY + newHeight >= pH) newHeight -= newHeight + offY - pH;
	
	for (int y = newY; y < newHeight; y++) {
	    for (int x = newX; x < newWidth; x++) {
		setPixel(x + offX, y + offY, sprite.getPixels()[x + y * sprite.getWidth()]);
	    }
	}
    }

    public void fillRect(int offX, int offY, int width, int height, int colour) {
	for (int y = 0; y < height; y++) {
	    for (int x = 0; x < width; x++) {
		setPixel(x + offX, y + offY, colour);
	    }
	}
    }

    public void clear() {
	for (int i = 0; i < pixels.length; i++) {
	    pixels[i] = 0xff000000;
	}

    }

}
