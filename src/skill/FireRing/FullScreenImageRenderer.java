package skill.FireRing;

import fsm.ImageRenderer;
import fsm.ImageState;
import model.Direction;
import model.HealthPointSprite;

import java.awt.*;
import java.util.List;

import characters.knight.Knight;

public class FullScreenImageRenderer implements ImageRenderer {
    protected HealthPointSprite knight;

    public FullScreenImageRenderer(HealthPointSprite knight) {
        this.knight = knight;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = knight.getFace();
        Rectangle range = knight.getRange();
        Rectangle body = knight.getBody();
        g.drawImage(image, (int) (range.x - range.width * 7/2), (int) (range.y+body.getHeight()), range.width * 8,
                range.height, null);

        g.setColor(Color.RED);
        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
