package characters.knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.Sprite;
import model.World;
import skill.Fireball.Fireball;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Attacking extends Sequence {
    public static final String AUDIO_MISS = "knight-miss";

    protected String AUDIO;
    protected final Knight knight;
    private final StateMachine stateMachine;
    protected Set<Integer> damagingStateNumbers;

    public Attacking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            super.update();
            if (damagingStateNumbers.contains(currentPosition)) {
                effectDamage();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        Rectangle damageArea = damageArea();
        g.setColor(Color.BLUE);
        g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    protected void effectDamage() {
        World world = knight.getWorld();
        Rectangle damageArea = damageArea();
        var sprites = world.getSprites(damageArea);
        boolean hasClash = false;
        for (Sprite sprite : sprites) {
            if (knight.getTeam() != sprite.getTeam()) {
                sprite.onDamaged(damageArea, knight.getDamage());
                hasClash = true;
            }
        }
        if (hasClash) {
            AudioPlayer.playSounds(AUDIO);
        } else {
            AudioPlayer.playSounds(AUDIO_MISS);
        }
    }

    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(World.MULTIPLY * 87, World.MULTIPLY * 70), // box offset x, y
                new Dimension(World.MULTIPLY * 55, World.MULTIPLY * 88));// box width, box height
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
        knight.triggerWalk();
    }

    @Override
    public String toString() {
        return "Attacking";
    }
}
