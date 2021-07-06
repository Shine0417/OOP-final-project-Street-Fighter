package characters.alita;

import java.awt.*;

import characters.knight.*;
import fsm.FiniteStateMachine;
import fsm.ImageRenderer;
import fsm.State;
import fsm.WaitingPerFrame;
import model.Direction;
import model.SpriteShape;
import model.World;
import skill.Fire.Fire;
import skill.Fireball.Fireball;
import skill.IceWall.IceWall;
import skill.Lightning.Lightning;
import skill.LightningBolt.LightningBolt;

import static utils.ImageStateUtils.imageStatesFromFolder;
import static characters.knight.Knight.Event.*;

public class Alita extends Knight {
        public static final int DAMAGE = 80;
        public static final String AUDIO_CAST = "emily-cast";
        public static final String AUDIO_INJURED = "emily-injured";
        public static final String AUDIO_DEAD = "emily-dead";

        public Alita(Point location, Direction face) {
                super(DAMAGE, location, face);
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
                String filepath = "assets/character/alita/";
                FiniteStateMachine fsm = new FiniteStateMachine();

                ImageRenderer imageRenderer = new KnightImageRenderer(this);

                State idle = new WaitingPerFrame(4,
                                new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
                State walking = new WaitingPerFrame(2,
                                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
                State attacking = new WaitingPerFrame(8, new AlitaAttacking(this, fsm,
                                imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
                State jumping = new WaitingPerFrame(4, new Jumping(this, fsm,
                                imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
                State crouch = new WaitingPerFrame(4,
                                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));
                State casting = new WaitingPerFrame(12,
                                new Cast(this, fsm, imageStatesFromFolder(filepath.concat("cast"), imageRenderer)));
                State kicking = new WaitingPerFrame(10, new AlitaKicking(this, fsm,
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
                if (fsm.currentState().toString().equals("Skill"))
                        return;
                switch (id) {
                        case 1:
                                if (mpBar.getHp() >= Fireball.FIREBALL_MP) {
                                        mpBar.onDamaged(null, 50);
                                        spell = new LightningBolt(this, 1);
                                } else {
                                        return;
                                }
                                break;
                        case 2:
                                if (mpBar.getHp() >= Fireball.FIREBALL_MP) {
                                        mpBar.onDamaged(null, 100);
                                        spell = new Fireball(this, 100);
                                } else {
                                        return;
                                }
                                break;
                        case 3:
                                if (upBar.max()) {
                                        upBar.setHp(0);
                                        spell = new LightningBolt(this, 1);

                                        Fireball spell2 = new Fireball(this, 1);
                                        spell2.setTeam(getTeam());
                                        world.addSprite(spell2);
                                        spell2.done();
                                } else {
                                        return;
                                }
                                break;
                }

                spell.setTeam(getTeam());
                world.addSprite(spell);
                super.skill(id);
        }

        public String toString() {
                return "Alita";
        }
}
