
package horstman_project7;

import java.awt.image.BufferedImage;

/**
 *
 * @author viro
 */
/*
 * manages the spritesheets for the game
 */
public class SpriteSheet {

    public BufferedImage spriteSheet;

    public SpriteSheet(BufferedImage ss) {
        this.spriteSheet = ss;
    }

    public BufferedImage grabSprite(int x, int y, int width, int height) {

        BufferedImage sprite = spriteSheet.getSubimage(x, y, width, height);
        return sprite;

    }
}
