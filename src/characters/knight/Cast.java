package characters.knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;

import java.awt.*;
import java.util.HashSet;
import java.util.List;

public class Cast extends Sequence {
    private final Knight knight;
    private final StateMachine stateMachine;

    public Cast(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = knight;
        this.stateMachine = stateMachine;
    }
    @Override
    public void update(){
        super.update();
        if(currentPosition == 1)
            AudioPlayer.playSounds(knight.AUDIO_CAST);
    }
    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
        knight.triggerWalk();
        knight.triggerSpell();
    }

    @Override
    public String toString() {
        return "Skill";
    }
}
