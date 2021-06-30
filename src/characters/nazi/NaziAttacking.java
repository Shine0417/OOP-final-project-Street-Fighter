package characters.nazi;

import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import characters.knight.Knight;
import characters.knight.Attacking;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class NaziAttacking extends Attacking {

    public static final String AUDIO_ATTACK = "emily-attack";

    public NaziAttacking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
        AUDIO = AUDIO_ATTACK;
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(1));
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(World.MULTIPLY * 80, World.MULTIPLY * 25), // box offset x, y
        new Dimension(World.MULTIPLY * 60, World.MULTIPLY * 120));// box width, box height
    }
}
