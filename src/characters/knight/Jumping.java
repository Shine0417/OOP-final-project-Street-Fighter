package characters.knight;

import java.awt.Dimension;
import java.util.List;

import fsm.Sequence;
import fsm.ImageState;
import fsm.StateMachine;
import model.Direction;
import model.World;

public class Jumping extends Sequence {
    private final Knight knight;
    private final StateMachine stateMachine;
    private final Integer acceleration = 30;
    private final int START_DROP = 8; // frames
    private final int START_JUMP = 4;

    public Jumping(Knight knight, StateMachine stateMachine, List<ImageState> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            super.update();
            if (currentPosition >= START_JUMP && currentPosition < START_DROP)
                knight.getWorld().move(knight,
                        new Dimension(0, -World.MULTIPLY * (acceleration * (7 - currentPosition))));
            else if (currentPosition >= START_DROP)
                knight.getWorld().move(knight, new Dimension(0, World.MULTIPLY * acceleration * (currentPosition - 8)));

            for (int i = 0; i < 4; i++)
                for (Direction direction : knight.getDirections()) {
                    knight.getWorld().move(knight, direction.translate());
                }
        }
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
        knight.triggerWalk();
    }

    @Override
    public String toString() {
        return "Jumping";
    }
}
