package characters.gray;

import java.awt.*;

import characters.knight.*;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.SpriteShape;
import model.World;
import skill.FireRing.FireRing;
import skill.Fire.Fire;
import skill.Fireball.Fireball;

import static utils.ImageStateUtils.imageStatesFromFolder;

public class Gray extends Knight {

        public static final String AUDIO_CAST = "gray-cast";
        public static final String AUDIO_INJURED = "gray-injured";
        public static final String AUDIO_DEAD = "gray-dead";

        public Gray(int damage, Point location, Direction face) {
                super(damage, location, face);
                SpriteShape shape = new SpriteShape(new Dimension(World.MULTIPLY * 146, World.MULTIPLY * 176),
                                new Dimension(World.MULTIPLY * 33, World.MULTIPLY * 38),
                                new Dimension(World.MULTIPLY * 66, World.MULTIPLY * 135));
                SpriteShape crouchShape = new SpriteShape(new Dimension(World.MULTIPLY * 146, World.MULTIPLY * 176),
                                new Dimension(World.MULTIPLY * 33, World.MULTIPLY * 88),
                                new Dimension(World.MULTIPLY * 66, World.MULTIPLY * 65));

                this.shape = shape;
                this.fsm = createTransitionTable();
                this.crouchShape = crouchShape;

                super.AUDIO_CAST = AUDIO_CAST;
                super.AUDIO_INJURED = AUDIO_INJURED;
                super.AUDIO_DEAD = AUDIO_DEAD;
        }

        private FiniteStateMachine createTransitionTable() {
                String filepath = "assets/character/gray/";
                FiniteStateMachine fsm = new FiniteStateMachine();

                ImageRenderer imageRenderer = new KnightImageRenderer(this);

                State idle = new WaitingPerFrame(4,
                                new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
                State walking = new WaitingPerFrame(2,
                                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
                State attacking = new WaitingPerFrame(3, new GrayAttacking(this, fsm,
                                imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
                State jumping = new WaitingPerFrame(4, new Jumping(this, fsm,
                                imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
                State crouch = new WaitingPerFrame(4,
                                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));
                State casting = new WaitingPerFrame(7,
                                new Cast(this, fsm, imageStatesFromFolder(filepath.concat("cast"), imageRenderer)));
                State kicking = new WaitingPerFrame(5, new GrayKicking(this, fsm,
                                imageStatesFromFolder(filepath.concat("kick"), imageRenderer)));
                State injured = new WaitingPerFrame(20, new Injured(this, fsm,
                                imageStatesFromFolder(filepath.concat("injured"), imageRenderer)));
                State dead = new WaitingPerFrame(40,
                                new Dead(this, imageStatesFromFolder(filepath.concat("dead"), imageRenderer)));

                knightTransitionTable(fsm, idle, walking, attacking, jumping, crouch, casting, kicking, injured, dead);

                return fsm;

        }

        @Override
        public void skill(int id) {
                System.out.print(fsm.currentState().toString());
                if (fsm.currentState().toString().equals("Skill"))
                        return;
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
                spell.setTeam(getTeam());
                world.addSprite(spell);
                super.skill(id);
        }

        public String toString() {
                return "Gray";
        }
}
