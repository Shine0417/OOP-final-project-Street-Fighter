package skill.Fireball;

import java.util.List;

import fsm.CyclicSequence;
import fsm.State;

public class Casting extends CyclicSequence{

    private final Fireball spell;


    public Casting(Fireball fireball, List<? extends State> states) {
        super(states);
        this.spell = fireball;
    }

    @Override
    public String toString() {
        return "Casting";
    }
    
}
