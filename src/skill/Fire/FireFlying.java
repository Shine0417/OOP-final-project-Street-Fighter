package skill.Fire;

import java.awt.Rectangle;
import java.util.List;

import fsm.State;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;

public class FireFlying extends Flying {

    public FireFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
    }


    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
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
            // AudioPlayer.playSounds(AUDIO_LIGHTNING_FLY);
        } else {
            // AudioPlayer.playSounds(AUDIO_LIGHTNING_FLY);
        }
    }

    @Override
    protected void onSequenceEnd() {
        // TODO Auto-generated method stub
        super.onSequenceEnd();
        spell.hit();
    }
}