package characters.nazi;

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

public class Nazi extends Knight {
        public static final int DAMAGE = 90;
        public static final String AUDIO_CAST = "emily-cast";
        public static final String AUDIO_INJURED = "emily-injured";
        public static final String AUDIO_DEAD = "emily-dead";

        public Nazi(Point location, Direction face) {
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
                String filepath = "assets/character/nazi/";
                FiniteStateMachine fsm = new FiniteStateMachine();

                ImageRenderer imageRenderer = new KnightImageRenderer(this);

                State idle = new WaitingPerFrame(4,
                                new Idle(imageStatesFromFolder(filepath.concat("idle"), imageRenderer)));
                State walking = new WaitingPerFrame(3,
                                new Walking(this, imageStatesFromFolder(filepath.concat("walking"), imageRenderer)));
                State attacking = new WaitingPerFrame(8, new NaziAttacking(this, fsm,
                                imageStatesFromFolder(filepath.concat("attack"), imageRenderer)));
                State jumping = new WaitingPerFrame(4, new Jumping(this, fsm,
                                imageStatesFromFolder(filepath.concat("jumping"), imageRenderer)));
                State crouch = new WaitingPerFrame(4,
                                new Crouch(this, imageStatesFromFolder(filepath.concat("crouch"), imageRenderer)));
                State casting = new WaitingPerFrame(12,
                                new Cast(this, fsm, imageStatesFromFolder(filepath.concat("cast"), imageRenderer)));
                State kicking = new WaitingPerFrame(10, new NaziKicking(this, fsm,
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
                                        mpBar.onDamaged(null, Fireball.FIREBALL_MP);
                                        spell = new LightningBolt(this, 1);
                                } else {
                                        return;
                                }
                                break;
                        case 2:
                                if (mpBar.getHp() >= Fireball.FIREBALL_MP) {
                                        mpBar.onDamaged(null, Fireball.FIREBALL_MP);
                                        spell = new Fireball(this, 100);
                                } else {
                                        return;
                                }
                                break;
                        case 3:
                                if (mpBar.getHp() >= Fireball.FIREBALL_MP && upBar.max()) {
                                        mpBar.onDamaged(null, Fireball.FIREBALL_MP);
                                        upBar.setHp(0);
                                        spell = new Fireball(this, 1);
                                        spell.shape = new SpriteShape(
                                                        new Dimension(World.MULTIPLY * 2 * 146,
                                                                        World.MULTIPLY * 2 * 176),
                                                        new Dimension(World.MULTIPLY * 2 * 86, World.MULTIPLY * 2 * 60),
                                                        new Dimension(World.MULTIPLY * 2 * 36,
                                                                        World.MULTIPLY * 2 * 55));
                                        spell.setLocation(new Point(getLocation().x, getLocation().y/2));

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
                return "Nazi";
        }
}
