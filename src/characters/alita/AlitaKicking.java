package characters.alita;

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
public class AlitaKicking extends Attacking {

    public AlitaKicking(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(knight, stateMachine, states);
        init();
    }

    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(2));
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
    protected Rectangle damageArea() {
        return knight.getArea(new Dimension(World.MULTIPLY * 80, World.MULTIPLY * 65), // box offset x, y
        new Dimension(World.MULTIPLY * 60, World.MULTIPLY * 30));// box width, box height
    }
}
