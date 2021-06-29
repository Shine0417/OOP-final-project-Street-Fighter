package characters.knight;

import java.util.List;

import fsm.CyclicSequence;
import fsm.ImageState;
import model.Direction;

public class Crouch extends CyclicSequence {
    private final Knight knight;

    public Crouch(Knight knight, List<ImageState> states) {
        super(states);
        this.knight = knight;
    }

    @Override
    public void update() {
        if (knight.isAlive()) {
            super.update();
            for (Direction direction : knight.getDirections()) {
                knight.getWorld().move(knight, direction.translate());
            }
        }
    }

    @Override
    public String toString() {
        return "Crouch";
    }
}
