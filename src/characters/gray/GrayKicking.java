package characters.gray;

import fsm.State;
import fsm.StateMachine;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import characters.knight.Knight;
import characters.knight.Attacking;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GrayKicking extends Attacking {

    public static final String AUDIO_KICK = "gray-kick";

    public GrayKicking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
        AUDIO = AUDIO_KICK;
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(7));
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(World.MULTIPLY * 0, World.MULTIPLY * 50), // box offset x, y
                new Dimension(World.MULTIPLY * 155, World.MULTIPLY * 78));// box width, box height
    }
}
