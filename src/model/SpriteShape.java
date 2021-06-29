package model;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class SpriteShape {
    public Dimension size;// character size x, y
    public Dimension bodyOffset; // character box x, y
    public Dimension bodySize; // character box width, height

    public SpriteShape(Dimension size,
                       Dimension bodyOffset, Dimension bodySize) {
        this.size = size;
        this.bodyOffset = bodyOffset;
        this.bodySize = bodySize;
    }
}
