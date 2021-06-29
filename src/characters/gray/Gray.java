package characters.gray;

import java.awt.*;

import characters.knight.*;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.SpriteShape;
import skill.FireRing.FireRing;
import skill.Fire.Fire;
import skill.Fireball.Fireball;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class Gray extends Knight {

    public static final String AUDIO_CAST = "gray-cast";

    public Gray(int damage, Point location, Direction face) {
        super(damage, location, face);
        SpriteShape shape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 38), new Dimension(66, 135));
        SpriteShape crouchShape = new SpriteShape(new Dimension(146, 176), new Dimension(33, 88),
                new Dimension(66, 65));

        this.shape = shape;
        this.fsm = createTransitionTable();
        this.crouchShape = crouchShape;

        super.AUDIO_CAST = AUDIO_CAST;
    }

    private FiniteStateMachine createTransitionTable() {
        String filepath = "assets/character/gray/";
        FiniteStateMachine fsm = new FiniteStateMachine();

        ImageRenderer imageRenderer = new KnightImageRenderer(this);

        State idle = new WaitingPerFrame(4, new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
        State walking = new WaitingPerFrame(2,
                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
        State attacking = new WaitingPerFrame(3,
                new GrayAttacking(this, fsm, imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
        State jumping = new WaitingPerFrame(4,
                new Jumping(this, fsm, imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
        State crouch = new WaitingPerFrame(4,
                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));
        State skill = new WaitingPerFrame(7,
                new Cast(this, fsm, imageStatesFromFolder(filepath.concat("cast"), imageRenderer)));
        State kicking = new WaitingPerFrame(5,
                new GrayKicking(this, fsm, imageStatesFromFolder(filepath.concat("kick"), imageRenderer)));

        knightTransitionTable(fsm, idle, walking, attacking, jumping, crouch, skill, kicking);

        return fsm;

    }

    @Override
    public void skill(int id) {
        super.skill(id);
        switch (id) {
            case 1:
                spell = new Fireball(this, 1);
                break;
            case 2:
                spell = new Fire(this, 1);
                break;
            case 3:
                spell = new FireRing(this, 1);
                break;
        }
        world.addSprite(spell);
    }

}
