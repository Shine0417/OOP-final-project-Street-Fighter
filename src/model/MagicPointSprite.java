package model;

import media.AudioPlayer;

import java.awt.*;

import characters.knight.HealthPointBar;
import characters.knight.MagicPointBar;
import java.time.LocalTime;
import java.time.ZoneId;
/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class MagicPointSprite extends HealthPointSprite {

    protected MagicPointBar mpBar;
    private int last_update ;
    public MagicPointSprite(int hp, int mp) {
        super(hp);
        this.mpBar = new MagicPointBar(mp);
        mpBar.setOwner(this);
        last_update = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
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

    @Override
    public void update(){
        int temp;
        temp = LocalTime.now(ZoneId.of("Asia/Taipei")).toSecondOfDay();
        if(temp - last_update > 1){
            
            last_update = temp;
            mpBar.addMp();
        }
    }

}
