package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;
import characters.knight.MagicPointBar;
import characters.knight.UltimatePointBar;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class UltimatePointSprite extends MagicPointSprite {

    protected UltimatePointBar upBar;

    public UltimatePointSprite(int hp, int mp,int up) {
        super(hp,mp);
        this.upBar = new UltimatePointBar(up);
        upBar.setOwner(this);
    }

    // not sure
    public void onMpDamaged(Rectangle damageArea, int damage) {
        upBar.onDamaged(damageArea, damage);
        if (upBar.isDead()) {
            // MP 0!
            // world.removeSprite(this);
            // AudioPlayer.playSounds(AUDIO_DIE);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        upBar.render(g);
    }

}
