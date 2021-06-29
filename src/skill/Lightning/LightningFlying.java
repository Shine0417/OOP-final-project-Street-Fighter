package skill.Lightning;

import java.awt.Rectangle;
import java.util.List;

import fsm.State;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;

public class LightningFlying extends Flying {

    public LightningFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
    }
    @Override
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
            // AudioPlayer.playSounds(FIREBALL_HIT);
        }
    }

    @Override
    protected void onSequenceEnd() {
        // TODO Auto-generated method stub
        super.onSequenceEnd();
        spell.hit();
    }
}