import controller.Game;
import model.Direction;
import model.HealthPointSprite;
import model.World;
import views.GameView;

import java.awt.*;
import java.io.File;

import characters.emily.Emily;
import characters.gray.Gray;
import characters.knight.Attacking;
import characters.knight.Knight;
import characters.knight.KnightCollisionHandler;
import characters.knight.Walking;

import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight, FiniteStateMachine
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    public static void main(String[] args) {
        addAudioByFilePath(Walking.AUDIO_STEP1, new File("assets/character/knight/audio/step1.wav"));
        addAudioByFilePath(Walking.AUDIO_STEP2, new File("assets/character/knight/audio/step2.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_1, new File("assets/character/knight/audio/sword-clash1.wav"));
        addAudioByFilePath(Attacking.AUDIO_SWORD_CLASH_2, new File("assets/character/knight/audio/sword-clash2.wav"));
        addAudioByFilePath(HealthPointSprite.AUDIO_DIE, new File("assets/character/knight/audio/die.wav"));

        // initialization procedure
        Knight p1 = new Gray(100, new Point(300, 300), Direction.RIGHT);
        Knight p2 = new Emily(150, new Point(700, 300), Direction.LEFT);

        World world = new World(new KnightCollisionHandler(), p1, p2);  // model
        Game game = new Game(world, p1, p2);  // controller
        GameView view = new GameView(game);  // view
        game.start();  // run the game and the game loop
        view.launch(); // launch the GUI
    }
}
