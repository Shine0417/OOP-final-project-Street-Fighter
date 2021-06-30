package skill.FireRing;

import characters.knight.Knight;
import skill.Fireball.Fireball;

import characters.knight.Knight;
import skill.Lightning.Lightning;
import skill.Lightning.LightningFlying;

import java.awt.*;
import characters.knight.Knight;
import fsm.FiniteStateMachine;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.HealthPointSprite;
import model.SpriteShape;
import skill.Fireball.Casting;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;
import skill.Fireball.Triggered;
import skill.ImageRenderer.FullScreenImageRenderer;
import skill.ImageRenderer.LongSkillImageRenderer;
import skill.ImageRenderer.SkillImageRenderer;

import static fsm.FiniteStateMachine.Transition.from;
import static skill.Fireball.Fireball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class FireRing extends Fireball {

    public FireRing(Knight caster, int hp) {
        super(caster, hp);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void init() {
        this.damage = 200;

        String filepath = "assets/skill/fireRing/";
        FullScreenImageRenderer ScreenimageRenderer = new FullScreenImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("casting"), ScreenimageRenderer)));
        State flying = new WaitingPerFrame(3,
                new FireRingFlying(this, imageStatesFromFolder(filepath.concat("flying"), ScreenimageRenderer)));
        State triggered = new WaitingPerFrame(3,
                new Triggered(this, imageStatesFromFolder(filepath.concat("trigger"), ScreenimageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }
}
