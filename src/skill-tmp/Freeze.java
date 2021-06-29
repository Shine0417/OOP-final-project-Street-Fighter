Skip to content
Fundamental-OOP
/
final-project-b07902091
Private
Code
Issues
Pull requests
1
Actions
More
final-project-b07902091/src/characters/gray/GrayKicking.java /
@Shine0417
Shine0417 done combo and skill(temp)
 History
 1 contributor
32 lines (25 sloc)  764 Bytes
package characters.<<character name>>;

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

public class Freeze extends Attacking {
    //  Sound Effect 
    public static final String AUDIO_FREEZE = "freeze";

    // variables 
    private final Knight attacker;
    private final Knight target;
    
    private final Integer damage = 20;
    private final Integer cost = 40;

    private final StateMachine stateMachine;
    private final Set<Integer> damagingStateNumbers = new HashSet<>(List.of(6));

    // specified the target and attacker 
    public Freeze(Knight attacker,Knight target, StateMachine stateMachine, List<? extends State> states) {
        super(attacker, stateMachine, states);
        this.target = target;
        init();
    }

    // decide the frame that deal damage 
    private void init() {
        damagingStateNumbers = new HashSet<>(List.of(7));
    }

    @Override
    // if attacker is alive
    // cast the skill
    public void update() {
        if (attacker.isAlive()) {
            super.update();
            if (damagingStateNumbers.contains(currentPosition)) {
                effectDamage();
            }
        }
    }

    @Override
    // draw the bound box of the skill
    public void render(Graphics g) {
        super.render(g);
         Rectangle damageArea = damageArea();
         g.setColor(Color.BLUE);
         g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    @Override
    // check the casted skill status
    private void effectDamage() {
        World world = attacker.getWorld();
        Rectangle damageArea = damageArea();
        var sprites = world.getSprites(damageArea);
        boolean hasClash = false;
        // deal skill effect to self 
        for (Sprite sprite : sprites) {
            if (attacker == sprite) {
                // healing
                attacker.getHeal(damage);
                // deduct MP
                attacker.useMP(cost);
                hasClash = true;
            }
        }
        if (hasClash) {
            AudioPlayer.playSounds(AUDIO_FREEZE);
        } 
    }

    //  determine the skill area 
    @Override
    protected Rectangle damageArea() {
        return  attacker.getArea(new Dimension(33, 38),
                new Dimension(66,105));
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }


}
© 2021 GitHub, Inc.
Terms
Privacy
Security
Status
Docs
Contact GitHub
Pricing
API
Training
Blog
About
