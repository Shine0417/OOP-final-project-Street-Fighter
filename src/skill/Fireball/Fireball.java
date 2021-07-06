package skill.Fireball;

import java.awt.*;
import characters.knight.Knight;
import fsm.FiniteStateMachine;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;
import model.World;
import skill.ImageRenderer.SkillImageRenderer;

import static fsm.FiniteStateMachine.Transition.from;
import static skill.Fireball.Fireball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Fireball extends HealthPointSprite {
    public int FIREBALL_HP;
    public static int FIREBALL_MP = 100;
    private Direction direction;
    private Knight caster;
    public SpriteShape shape = new SpriteShape(new Dimension(World.MULTIPLY * 146, World.MULTIPLY * 176),
            new Dimension(World.MULTIPLY * 86, World.MULTIPLY * 60),
            new Dimension(World.MULTIPLY * 36, World.MULTIPLY * 55));
    protected FiniteStateMachine fsm;
    protected int damage;

    public enum Event {
        DONE, HIT
    }

    public Fireball(Knight caster, int hp) {
        super(hp);
        this.FIREBALL_HP = hp;
        this.caster = caster;
        face = this.direction = caster.getFace();
        this.location = new Point(caster.getLocation().x, caster.getLocation().y);
        this.fsm = new FiniteStateMachine();
        init();
    }

    protected void init() {

        this.damage = 100;

        String filepath = "assets/skill/fireball/";
        SkillImageRenderer imageRenderer = new SkillImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("casting"), imageRenderer)));
        State flying = new WaitingPerFrame(4,
                new Flying(this, imageStatesFromFolder(filepath.concat("flying"), imageRenderer)));
        State triggered = new WaitingPerFrame(4,
                new Triggered(this, imageStatesFromFolder(filepath.concat("trigger"), imageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }

    public void done() {
        fsm.trigger(DONE);
    }

    public void hit() {
        fsm.trigger(HIT);
    }

    public void update() {
        fsm.update();
    }

    @Override
    public void render(Graphics g) {
        // super.render(g);
        fsm.render(g);
        // Rectangle range = getRange();
        // g.setColor(Color.RED);
        // g.fillRect(range.x, range.y, (int) getRange().getWidth(), range.height);
        // fsm.render(g);
    }

    public Rectangle getRange() {
        return new Rectangle(location, shape.size);
    }

    public Dimension getBodyOffset() {
        return shape.bodyOffset;
    }

    public Dimension getBodySize() {
        return shape.bodySize;
    }

    public Knight getCaster() {
        return caster;
    }

    public int getDamage() {
        return damage;
    }

    public Direction getDirection() {
        return direction;
    }
}
