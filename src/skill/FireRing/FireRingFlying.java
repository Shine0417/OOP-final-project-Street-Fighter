package skill.FireRing;

import java.util.List;

import java.awt.Rectangle;
import java.util.List;

import fsm.State;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;

public class FireRingFlying extends Flying {

    public FireRingFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void effectDamage() {
        World world = spell.getWorld();
        Rectangle damageArea = damageArea();
        var sprites = world.getSprites(damageArea);
        boolean hasClash = false;
        for (Sprite sprite : sprites) {
            if (spell.getTeam() != sprite.getTeam()) {
                sprite.onDamaged(damageArea, spell.getDamage());
                hasClash = true;
            }
        }

        // Add Sound TODO
        if (hasClash) {
            spell.hit();
            // AudioPlayer.playSounds(FIREBALL_HIT);
        } else {
            // AudioPlayer.playSounds(FIREBALL_HIT);
        }
    }

    @Override
    protected void onSequenceEnd() {
        // TODO Auto-generated method stub
        super.onSequenceEnd();
        spell.hit();
    }

    protected Rectangle damageArea() {
        Rectangle rec = new Rectangle();
        if (currentPosition < 10)
            rec.width = spell.getBody().width + World.MULTIPLY * 90 * currentPosition;
        else
            rec.width = spell.getBody().width + World.MULTIPLY * 90 * (9 + currentPosition / 9);

        rec.height = spell.getBody().height / 2;
        rec.x = (int) spell.getLocation().getX() + spell.getBodySize().width * 3 / 2 - rec.width / 2;
        rec.y = (int) spell.getLocation().getY() + spell.getBodySize().height + rec.height * 3;

        return rec;// box width, box height
    }
}
