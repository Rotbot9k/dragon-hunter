
package horstman_project7;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;

/**
 *
 * @author viro
 */
/*
 * loads buffered images in a nice easy way to be used in other places in the game
 */
public class BufferedImageLoader {

    public BufferedImage loadImage(String pathRelativeToThis) throws IOException {
        URL url = this.getClass().getResource("images/" + pathRelativeToThis);
        BufferedImage image = ImageIO.read(url);
        return image;
    }
}
