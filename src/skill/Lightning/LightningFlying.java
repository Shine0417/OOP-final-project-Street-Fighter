package skill.Lightning;

import java.awt.Rectangle;
import java.util.List;

import fsm.State;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;

public class LightningFlying extends Flying {

    public static final String AUDIO_LIGHTNING_FLY = "lightning-fly";

    public LightningFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
    }


    @Override
    public void update() {
        // TODO Auto-generated method stub
        super.update();
        AudioPlayer.playSounds(AUDIO_LIGHTNING_FLY);
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