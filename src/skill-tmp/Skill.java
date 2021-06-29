package characters.knight;

import fsm.Sequence;
import fsm.State;
import fsm.StateMachine;
import media.AudioPlayer;
import model.Sprite;
import model.World;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Skill extends Sequence {
    // Setting the sound effect for skills 
    // Basic attack
    public static final String AUDIO_BasicAtk = "basic-atk";
    // Fireball
    public static final String AUDIO_Fireball = "fireball";
    // Lightning
    public static final String AUDIO_Lightning = "lightning";
    // Atomic Boom
    public static final String AUDIO_AtomicBoom = "atomic-boom";
    // Freeze
    public static final String AUDIO_Freeze = "freeze";



    // Determine the attacker, target and used skill
        // public Skill(Knight knight, StateMachine stateMachine, List<? extends State> states) {
        //     super(states);
        //     this.knight = knight;
        //     this.stateMachine = stateMachine;
        // }        
    // Since one player can have more than one character
    // We need to specify the target 
    private final Knight attacker;
    private final Knight target;
    private final Integer skill_id;
    private final StateMachine stateMachine;
    private final Set<Integer> damagingStateNumbers = new HashSet<>(List.of(6));

    public Skill(Knight attacker, Knight target, Integer skill_id, StateMachine stateMachine, List<? extends State> states) {
        super(states);
        this.knight = attacker;
        this.knight = target;
        this.skill_id = skill_id;
        this.stateMachine = stateMachine;
    }

    @Override
    // if the attacker is alive, do the attack action
    // pass the skill id to specified the skill used
    public void update() {
        if (attacker.isAlive()) {
            super.update();
            if (damagingStateNumbers.contains(currentPosition)) {
                effectDamage(skill_id);
            }
        }
    }

    @Override
    // Draw the bound box for the skill used
    // given the skill id
    public void render(Graphics g, Integer skill_id) {
        super.render(g);
         Rectangle damageArea = damageArea(skill_id);
         g.setColor(Color.BLUE);
         g.drawRect(damageArea.x, damageArea.y, damageArea.width, damageArea.height);
    }

    // specified the skill_id to find the damage area
    // specified the target 
    // deal damage due to used skill
    // play music only when attack is valid 
    private void effectDamage(Integer skill_id) {
        World world = attacker.getWorld();
        Rectangle damageArea = damageArea(skill_id);
        var sprites = world.getSprites(damageArea);
        boolean hasClash = false;
        for (Sprite sprite : sprites) {
            if (target == sprite) {
                sprite.onDamaged(damageArea, getSkillDamage());
                hasClash = true;
            }
        }
        if (hasClash) {
            playEffectSound(skill_id);
        } 
    }

    private Integer getSkillDamage(Integer skill_id){
        Integer dmg;
        switch (skill_id){
            // Basic Atk
            case 0:
                dmg = attacker.getDamage();
            // Fireball
            case 1:
            	dmg = 40; 
            // Lightning
            case 2:
            	dmg = 15;
            // Atomic Boom
            case 3:
            	dmg = 50;
            // Freeze
            case 4:
            	dmg = 40;
        }
        return dmg;
    }

    private Integer getSkillCost(Integer skill_id){
        Integer cost;
        switch (skill_id){
            // Basic Atk
            case 0:
                cost = 0;
            // Fireball
            case 1:
                cost = 20; 
            // Lightning
            case 2:
                cost = 30;
            // Atomic Boom
            case 3:
                cost = 50;
            // Freeze
            case 4:
                cost = 0;
        }
        return cost;
    }

    private void playEffectSound(Integer skill_id){
        switch (skill_id){
            // Basic Atk
            case 0:
                AudioPlayer.playSounds(AUDIO_BasicAtk);
            // Fireball
            case 1:
                AudioPlayer.playSounds(AUDIO_Fireball);
            // Lightning
            case 2:
                AudioPlayer.playSounds(AUDIO_Lightning);
            // Atomic Boom
            case 3:
                AudioPlayer.playSounds(AUDIO_AtomicBoom);
            // Freeze
            case 4:
                AudioPlayer.playSounds(AUDIO_Freeze);
        }
    }

    // Determinethe damage area of the choosen skill by skill id
    private Rectangle damageArea(Integer skill_id) {
        Rectangle tmp;
        tmp = knight.getArea(new Dimension(100, 50),
                new Dimension(1000, 100));
        switch (skill_id){
            // Basic Atk
            case 0:
                // cover small area of attack
               tmp = attacker.getArea(new Dimension(87, 70),
                new Dimension(55, 88));
            // Fireball
            case 1:
            // Lightning
            case 2:
                // cover the whole area of the character face
                tmp = attacker.getArea(new Dimension(100, -350),
                new Dimension(1500, 500));
            // Atomic Boom
            case 3:
                // cover the whole area of the character face
                tmp = attacker.getArea(new Dimension(100, -350),
                new Dimension(1500, 500));
            // Freeze
            case 4:
                // only cover self bounding box
                tmp = attacker.getArea(new Dimension(33, 38),
                new Dimension(66,105));
        }
        return tmp;
    }

    @Override
    protected void onSequenceEnd() {
        currentPosition = 0;
        stateMachine.reset();
    }
}
