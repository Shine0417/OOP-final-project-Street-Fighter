package skill.LightningBolt;


import java.awt.*;
import characters.knight.Knight;
import fsm.State;
import fsm.WaitingPerFrame;
import model.SpriteShape;
import skill.Fireball.Casting;
import skill.Fireball.Fireball;
import skill.Fireball.Triggered;
import skill.ImageRenderer.SkillImageRenderer;

import static fsm.FiniteStateMachine.Transition.from;
import static skill.Fireball.Fireball.Event.*;
import static utils.ImageStateUtils.imageStatesFromFolder;

public class LightningBolt extends Fireball {

    public LightningBolt(Knight caster, int hp) {
        super(caster, hp);// default 1
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void init() {
        this.damage = 200;

        String filepath = "assets/skill/lightningball/";
        SkillImageRenderer imageRenderer = new SkillImageRenderer(this);

        State casting = new WaitingPerFrame(3,
                new Casting(this, imageStatesFromFolder(filepath.concat("casting"), imageRenderer)));
        State flying = new WaitingPerFrame(4,
                new LightningBoltFlying(this, imageStatesFromFolder(filepath.concat("flying"), imageRenderer)));
        State triggered = new WaitingPerFrame(4,
                new Triggered(this, imageStatesFromFolder(filepath.concat("trigger"), imageRenderer)));

        fsm.setInitialState(casting);
        fsm.addTransition(from(casting).when(DONE).to(flying));
        fsm.addTransition(from(flying).when(HIT).to(triggered));
    }
}
