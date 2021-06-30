package characters.knight;

import fsm.FiniteStateMachine;
import fsm.State;
import media.AudioPlayer;
import model.Direction;
import model.UltimatePointSprite;
import model.SpriteShape;
import skill.Fireball.Fireball;

import java.awt.*;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import static characters.knight.Knight.Event.*;
import static model.Direction.LEFT;

import static fsm.FiniteStateMachine.Transition.from;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Knight extends UltimatePointSprite {
    public static final int KNIGHT_HP = 500;
    public static final int KNIGHT_MP = 200;
    public static final int KNIGHT_UP = 200;
    public String AUDIO_INJURED;
    public String AUDIO_DEAD;

    protected String AUDIO_CAST;
    protected SpriteShape shape;
    protected SpriteShape crouchShape;
    protected FiniteStateMachine fsm;
    private final Set<Direction> directions = new CopyOnWriteArraySet<>();
    private final int damage;
    protected Fireball spell;
    // todo add acceleration(gravity easier)

    public enum Event {
        WALK, STOP, ATTACK, DAMAGED, CRUOCH, JUMP, STOP_CROUCH, SKILL, KICK, INJURED, DEAD
    }

    public Knight(int damage, Point location, Direction face) {
        super(KNIGHT_HP, KNIGHT_MP,KNIGHT_UP);
        this.face = face;
        this.damage = damage;
        this.location = location;
    }

    protected void knightTransitionTable(FiniteStateMachine fsm, State idle, State walking, State attacking,
            State jumping, State crouch, State casting, State kicking, State injured, State dead) {
        fsm.setInitialState(idle);

        // Attack, walking and idle
        // Kick
        fsm.addTransition(from(idle).when(Event.WALK).to(walking));
        fsm.addTransition(from(walking).when(Event.STOP).to(idle));
        fsm.addTransition(from(idle).when(Event.ATTACK).to(attacking));
        fsm.addTransition(from(walking).when(Event.ATTACK).to(attacking));
        fsm.addTransition(from(idle).when(Event.KICK).to(kicking));
        fsm.addTransition(from(walking).when(Event.KICK).to(kicking));

        // Jump
        fsm.addTransition(from(walking).when(Event.JUMP).to(jumping));
        fsm.addTransition(from(idle).when(Event.JUMP).to(jumping));

        // Crouch
        fsm.addTransition(from(idle).when(Event.CRUOCH).to(crouch));
        fsm.addTransition(from(walking).when(Event.CRUOCH).to(crouch));
        fsm.addTransition(from(crouch).when(Event.STOP_CROUCH).to(idle));

        // Skill
        fsm.addTransition(from(idle).when(Event.SKILL).to(casting));
        fsm.addTransition(from(walking).when(Event.SKILL).to(casting));
        fsm.addTransition(from(crouch).when(Event.SKILL).to(casting));
        fsm.addTransition(from(attacking).when(Event.SKILL).to(casting));
        fsm.addTransition(from(kicking).when(Event.SKILL).to(casting));

        // Injured
        fsm.addTransition(from(idle).when(Event.INJURED).to(injured));
        fsm.addTransition(from(walking).when(Event.INJURED).to(injured));
        fsm.addTransition(from(crouch).when(Event.INJURED).to(injured));
        fsm.addTransition(from(attacking).when(Event.INJURED).to(injured));
        fsm.addTransition(from(kicking).when(Event.INJURED).to(injured));
        fsm.addTransition(from(casting).when(Event.INJURED).to(injured));
        // fsm.addTransition(from(jumping).when(Event.INJURED).to(injured)); TODO add jump-> change hero, injured
        
        // dead
        fsm.addTransition(from(idle).when(Event.DEAD).to(dead));
        fsm.addTransition(from(walking).when(Event.DEAD).to(dead));
        fsm.addTransition(from(crouch).when(Event.DEAD).to(dead));
        fsm.addTransition(from(attacking).when(Event.DEAD).to(dead));
        fsm.addTransition(from(kicking).when(Event.DEAD).to(dead));
        fsm.addTransition(from(casting).when(Event.DEAD).to(dead));
        fsm.addTransition(from(injured).when(Event.DEAD).to(dead));
        // fsm.addTransition(from(jumping).when(Event.DEAD).to(dead)); TODO add jump-> change hero, injured
    }

    public void addUp() {
        upBar.addUp();
    }

    public void attack() {
        fsm.trigger(ATTACK);
    }

    public void kick() {
        fsm.trigger(KICK);
    }

    public int getDamage() {
        return damage;
    }

    public void move(Direction direction) {
        if (direction == LEFT || direction == Direction.RIGHT) {
            face = direction;
        }
        if (!directions.contains(direction)) {
            this.directions.add(direction);
            fsm.trigger(WALK);
        }
    }

    public void triggerWalk() {
        if (!directions.isEmpty()) {
            fsm.trigger(WALK);
        }
    }

    public void jump() {
        fsm.trigger(JUMP);
    }

    public void crouch() {
        fsm.trigger(CRUOCH);
    }

    public void stopCrouch() {
        fsm.trigger(STOP_CROUCH);
        triggerWalk();
    }

    public void stop(Direction direction) {
        directions.remove(direction);
        if (directions.isEmpty()) {
            fsm.trigger(STOP);
        }
    }

    public void skill(int id) {
        fsm.trigger(SKILL);
    }

    public void triggerSpell() {
        spell.done();
    }

    public void update() {
        super.update();
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        super.render(g);
        fsm.render(g);
    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        hpBar.onDamaged(damageArea, damage);
        if (hpBar.isDead()) {
            fsm.trigger(DEAD);
            AudioPlayer.playSounds(AUDIO_DEAD);
        }
        else {
            AudioPlayer.playSounds(AUDIO_INJURED);
            fsm.trigger(INJURED);
        }
    }


    public Set<Direction> getDirections() {
        return directions;
    }

    @Override
    public Point getLocation() {
        return location;
    }

    @Override
    public Rectangle getRange() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return new Rectangle(location, shape.size);
        else
            return new Rectangle(location, crouchShape.size);
    }

    @Override
    public Dimension getBodyOffset() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return shape.bodyOffset;
        else
            return crouchShape.bodyOffset;
    }

    @Override
    public Dimension getBodySize() {
        if (!fsm.currentState().toString().equals("Crouch"))
            return shape.bodySize;
        else
            return crouchShape.bodySize;
    }

    public boolean isDead(){
        return this.hpBar.isDead();
    }

    public void resetFsm(){
        fsm.reset();
    }

    public boolean isJumping(){
        return fsm.currentState().toString().equals("Jumping");
    }
}
