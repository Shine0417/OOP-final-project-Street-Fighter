package characters.knight;

import java.util.List;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;

import java.util.Set;

public class Dead extends Sequence {
    protected final Knight knight;
    protected Set<Integer> damagingStateNumbers;

    public Dead(Knight knight, List<? extends State> states) {
        super(states);
        this.knight = knight;
    }

    @Override
    protected void onSequenceEnd() {
        if(knight.getWorld().getGame().checkGameOver()){
            // Do GAMEOVER
            knight.getWorld().removeSprite(knight);
        }
        else {
            knight.getWorld().getGame().changeKnight(knight.getTeam());
        }
        
    }
}
