package skill.ImageRenderer;

import fsm.ImageRenderer;
import fsm.ImageState;
import model.Direction;
import model.HealthPointSprite;

import java.awt.*;
import java.util.List;

import characters.knight.Knight;

public class SkillImageRenderer implements ImageRenderer {
    protected HealthPointSprite knight;

    public SkillImageRenderer(HealthPointSprite knight) {
        this.knight = knight;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = knight.getFace();
        Rectangle range = knight.getRange();
        Rectangle body = knight.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width, range.y, -range.width, range.height, null);
        } else {
            g.drawImage(image, range.x, range.y, range.width, range.height, null);
        }
        g.setColor(Color.RED);
        g.drawRect(body.x, body.y, body.width, body.height);
    }
}
