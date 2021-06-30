package skill.IceWall;

import characters.knight.Knight;
import fsm.State;
import fsm.WaitingPerFrame;
import skill.Fireball.Casting;
import skill.Fireball.Fireball;
import skill.Fireball.Triggered;
import skill.ImageRenderer.SkillImageRenderer;

import static fsm.FiniteStateMachine.Transition.from;
import static skill.Fireball.Fireball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

import java.awt.Dimension;

import model.SpriteShape;

public class IceWall extends Fireball {

    public IceWall(Knight caster, int hp) {
        super(caster, hp);//default hp 1000
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void init() {
        this.damage = 200;

        String filepath = "assets/skill/iceWall/";
        SkillImageRenderer imageRenderer = new SkillImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("casting"), imageRenderer)));
        State flying = new WaitingPerFrame(12,
                new IceWallFlying(this, imageStatesFromFolder(filepath.concat("flying"), imageRenderer)));
        State triggered = new WaitingPerFrame(3,
                new Triggered(this, imageStatesFromFolder(filepath.concat("trigger"), imageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }
}
