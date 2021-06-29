package characters.knight;

import java.util.List;

import fsm.CyclicSequence;
import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Injured extends Sequence {
    protected final Knight knight;
    private final StateMachine stateMachine;
    protected Set<Integer> damagingStateNumbers;

    public Injured(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
        knight.triggerWalk();
    }
}
