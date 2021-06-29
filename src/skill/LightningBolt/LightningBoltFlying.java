package skill.LightningBolt;

import java.awt.Rectangle;
import java.util.List;

import fsm.State;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;
import skill.Lightning.LightningFlying;

public class LightningBoltFlying extends Flying {

    public LightningBoltFlying(Fireball fireball, List<? extends State> states) {
        super(fireball, states);
    }

    @Override
    public void update() {
        super.update();
        for (int i = 0; i < 3; i++)
            spell.getWorld().move(spell, spell.getDirection().translate());
        if (currentPosition == 1)
            AudioPlayer.playSounds(LightningFlying.AUDIO_LIGHTNING_FLY);
    }
}