package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;
import characters.knight.MagicPointBar;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class MagicPointSprite extends HealthPointSprite {

    protected MagicPointBar mpBar;

    public MagicPointSprite(int hp, int mp) {
        super(hp);
        this.mpBar = new MagicPointBar(mp);
        mpBar.setOwner(this);
    }

    // not sure
    public void onMpDamaged(Rectangle damageArea, int damage) {
        mpBar.onDamaged(damageArea, damage);
        if (mpBar.isDead()) {
            // MP 0!
            // world.removeSprite(this);
            // AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        mpBar.render(g);
    }

}
