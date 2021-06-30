package skill.ImageRenderer;

import fsm.ImageRenderer;
import fsm.ImageState;
import model.Direction;
import model.HealthPointSprite;
import model.World;

import java.awt.*;
import java.util.List;

import characters.knight.Knight;

public class LongSkillImageRenderer implements ImageRenderer {
    protected HealthPointSprite knight;

    public LongSkillImageRenderer(HealthPointSprite knight) {
        this.knight = knight;
    }

    @Override
    public void render(Image image, Graphics g) {
        Direction face = knight.getFace();
        Rectangle range = knight.getRange();
        Rectangle body = knight.getBody();
        if (face == Direction.LEFT) {
            g.drawImage(image, range.x + range.width * 1 / 2, range.y,
                    -World.MULTIPLY * knight.getWorld().getWidth() * 3 / 2, World.MULTIPLY * range.height, null);
        } else {
            g.drawImage(image, range.x + range.width * 1 / 2, range.y,
                    World.MULTIPLY * knight.getWorld().getWidth() * 3 / 2, World.MULTIPLY * range.height, null);
        }
        if (World.SHOW_BOX) {
            g.setColor(Color.RED);
            g.drawRect(body.x, body.y, body.width, body.height);
        }
    }
}
