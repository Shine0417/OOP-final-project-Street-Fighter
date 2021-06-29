package characters.gray;

import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

import characters.knight.Knight;
import characters.knight.Attacking;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class GrayKicking extends Attacking {

    public GrayKicking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(7));
    }

    @Override
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(0, 50), // box offset x, y
                new Dimension(155, 78));// box width, box height
    }
}
