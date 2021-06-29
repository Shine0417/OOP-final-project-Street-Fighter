import controller.Game;
import model.Direction;
import model.HealthPointSprite;
import model.World;
import skill.Fireball.Flying;
import skill.IceWall.IceWallFlying;
import skill.Lightning.LightningFlying;
import views.GameView;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

import characters.emily.Emily;
import characters.emily.EmilyAttacking;
import characters.gray.Gray;
import characters.gray.GrayAttacking;
import characters.gray.GrayKicking;
import characters.knight.Attacking;
import characters.knight.Knight;
import characters.knight.KnightCollisionHandler;
import characters.knight.Walking;
import java.util.List;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight,
 * FiniteStateMachine
 * 
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    public static void main(String[] args) {
        addAudio();

        List<Knight> team1 = new ArrayList<>();
        List<Knight> team2 = new ArrayList<>();
        // initialization procedure
        Knight p1 = new Gray(100, new Point(300, 300), Direction.RIGHT);
        Knight p3 = new Emily(100, new Point(300, 300), Direction.RIGHT);
        team1.add(p1);
        team1.add(p3);
        p1.setTeam(1);
        p3.setTeam(1);

        Knight p2 = new Emily(150, new Point(700, 300), Direction.LEFT);
        Knight p4 = new Gray(150, new Point(700, 300), Direction.LEFT);
        team2.add(p2);
        team2.add(p4);
        p2.setTeam(2);
        p4.setTeam(2);

        World world = new World(new KnightCollisionHandler(), p1, p2); // model
        Game game = new Game(world, team1, team2); // controller
        GameView view = new GameView(game); // view
        game.start(); // run the game and the game loop
        view.launch(); // launch the GUI
    }

    public static void addAudio() {
        addAudioByFilePath(EmilyAttacking.AUDIO_ATTACK, new File("assets/character/emily/audio/attack.wav"));
        addAudioByFilePath(GrayAttacking.AUDIO_ATTACK, new File("assets/character/gray/audio/attack.wav"));
        addAudioByFilePath(GrayKicking.AUDIO_KICK, new File("assets/character/gray/audio/kick.wav"));
        addAudioByFilePath(Attacking.AUDIO_MISS, new File("assets/character/knight/audio/miss.wav"));
        addAudioByFilePath(Emily.AUDIO_CAST, new File("assets/character/emily/audio/cast.wav"));
        addAudioByFilePath(Gray.AUDIO_CAST, new File("assets/character/gray/audio/cast.wav"));

        addAudioByFilePath(Emily.AUDIO_DEAD, new File("assets/character/emily/audio/dead.wav"));
        addAudioByFilePath(Gray.AUDIO_DEAD, new File("assets/character/gray/audio/dead.wav"));
        addAudioByFilePath(Emily.AUDIO_INJURED, new File("assets/character/emily/audio/injured.wav"));
        addAudioByFilePath(Gray.AUDIO_INJURED, new File("assets/character/gray/audio/injured.wav"));

        addAudioByFilePath(Flying.AUDIO_FIREBALL_HIT, new File("assets/skill/fireball/audio/trigger.wav"));
        addAudioByFilePath(IceWallFlying.AUDIO_ICEWALL_HIT, new File("assets/skill/iceWall/audio/trigger.wav"));
        addAudioByFilePath(LightningFlying.AUDIO_LIGHTNING_FLY, new File("assets/skill/lightning/audio/flying.wav"));

    }
}
