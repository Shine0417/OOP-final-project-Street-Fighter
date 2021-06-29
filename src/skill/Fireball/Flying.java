package skill.Fireball;

import java.awt.Dimension;
import java.util.List;

import fsm.CyclicSequence;
import fsm.State;
import media.AudioPlayer;
import model.Sprite;
import model.World;

import java.awt.*;

public class Flying extends CyclicSequence {
    public static final String AUDIO_FIREBALL_HIT = "fireball_hit";

    protected final Fireball spell;

    public Flying(Fireball fireball, List<? extends State> states) {
        super(states);
        this.spell = fireball;
    }

    @Override
    public void update() {
        super.update();
        effectDamage();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Rectangle damageArea = damageArea();
        g.setColor(Color.BLUE);
        g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

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
            AudioPlayer.playSounds(AUDIO_FIREBALL_HIT);
        } else {
            spell.getWorld().move(spell, spell.getDirection().translate());
            // AudioPlayer.playSounds(FIREBALL_HIT);
        }
    }

    protected Rectangle damageArea() {
        return spell.getBody();// box width, box height
    }
}
