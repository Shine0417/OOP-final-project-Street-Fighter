package skill.Fireball;

import java.util.List;

import fsm.ImageState;
import fsm.Sequence;

public class Triggered extends Sequence{
    private final Fireball spell;

    public Triggered(Fireball spell, List<ImageState> states) {
        super(states);
        this.spell = spell;
    }

    @Override
    protected void onSequenceEnd() {
        spell.onDamaged(spell.getBody(), spell.FIREBALL_HP);// dunno
        // TODO Auto-generated method stub
    }
    
}
