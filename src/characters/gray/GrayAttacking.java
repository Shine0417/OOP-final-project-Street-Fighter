package characters.gray;

import fsm.State;
import fsm.StateMachine;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import characters.knight.Attacking;
import characters.knight.Knight;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GrayAttacking extends Attacking {

    public static final String AUDIO_ATTACK = "gray-attack";

    public GrayAttacking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
        AUDIO = AUDIO_ATTACK;
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(2));
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(World.MULTIPLY * 80, World.MULTIPLY * 65), // box offset x, y
                new Dimension(World.MULTIPLY * 60, World.MULTIPLY * 40));// box width, box height
    }
}
