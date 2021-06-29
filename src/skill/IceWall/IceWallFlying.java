package skill.IceWall;

import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.Receiver;
import javax.swing.text.Position;

import fsm.ImageState;
import fsm.State;
import model.Direction;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;
import java.awt.*;

public class IceWallFlying extends Flying {
    private final int WALL_SIZE = 4;

    public IceWallFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void update() {
        super.update();
        effectDamage();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        Image image = ((ImageState) states.get(currentPosition)).getImage();
        for (int i = 1; i < WALL_SIZE + 1; i++) {
            Rectangle range = spell.getRange();
            if (spell.getDirection() == Direction.LEFT) {
                g.drawImage(image, range.x + range.width * (i + 2) / 2, range.y, -range.width, range.height, null);
            } else {
                g.drawImage(image, range.x + range.width * i / 2, range.y, range.width, range.height, null);
            }
        }
    }

    protected void effectDamage() {
        World world = spell.getWorld();
        Rectangle damageArea = damageArea();
        var sprites = world.getSprites(damageArea);
        boolean hasClash = false;
        for (Sprite sprite : sprites) {
            if (spell != sprite && spell.getCaster() != sprite) {
                sprite.onDamaged(damageArea, spell.getDamage());
                hasClash = true;
            }
        }

        // Add Sound TODO
        if (hasClash) {
            spell.hit();
            // AudioPlayer.playSounds(FIREBALL_HIT);
        } else {
            if (spell.getDirection() == Direction.LEFT)
                spell.getWorld().move(spell, new Dimension(-spell.getBodySize().width, 0));
            else
                spell.getWorld().move(spell, new Dimension(spell.getBodySize().width, 0));
            // AudioPlayer.playSounds(FIREBALL_HIT);
        }
    }

    protected Rectangle damageArea() {
        Rectangle rec = new Rectangle();
        Rectangle range = spell.getRange();
        Rectangle body = spell.getBody();
        rec.width = range.width * (WALL_SIZE * 5 / 8) + body.width;
        rec.height = body.height;

        if (spell.getDirection() == Direction.LEFT)
            rec.x = body.x;
        else
            rec.x = body.x - rec.width / 12;
        rec.y = body.y;

        return rec;// box width, box height
    }

}
