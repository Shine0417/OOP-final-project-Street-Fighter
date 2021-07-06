package skill.Fire;

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
import model.World;
import skill.Fireball.Casting;
import skill.Fireball.Fireball;
import skill.Fireball.Flying;
import skill.Fireball.Triggered;
import skill.ImageRenderer.LongSkillImageRenderer;
import skill.ImageRenderer.SkillImageRenderer;

import static fsm.FiniteStateMachine.Transition.from;
import static skill.Fireball.Fireball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class Fire extends Lightning {

    public Fire(Knight caster, int hp) {
        super(caster, hp);// default hp 1000
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void init() {

        this.damage = 150;

        String filepath = "assets/skill/fire/";
        SkillImageRenderer imageRenderer = new SkillImageRenderer(this);
        LongSkillImageRenderer longimageRenderer = new LongSkillImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("casting"), imageRenderer)));
        State flying = new WaitingPerFrame(6,
                new FireFlying(this, imageStatesFromFolder(filepath.concat("flying"), longimageRenderer)));
        State triggered = new WaitingPerFrame(3,
                new Triggered(this, imageStatesFromFolder(filepath.concat("trigger"), longimageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }

}
