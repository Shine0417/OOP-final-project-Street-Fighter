import controller.Game;
import media.AudioPlayer;
import model.Direction;
import model.HealthPointSprite;
import model.World;
import skill.Fireball.Flying;
import skill.IceWall.IceWallFlying;
import skill.Lightning.LightningFlying;
import views.GameView;
import views.Intro;
import views.StageMenu;
import views.CharacterMenu;
import views.GameOver;

import java.awt.event.*;

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

import javax.swing.JButton;
import javax.swing.JPanel;

import views.Canvas;
import static media.AudioPlayer.addAudioByFilePath;

/**
 * Demo route: Main, GameView, Game, GameLoop, World, Sprite, Knight,
 * FiniteStateMachine
 * 
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Main {
    public static JPanel cards;
    public static CardLayout layout;
    final static String INTRO_PANEL = "Card with Intro panel";
    final static String CHACRACTER_MENU_PANEL = "Card with Character Selection panel";
    final static String STAGE_MENU_PANEL = "Card with Stage Selection panel";
    final static String GAME_PANEL = "Card with GamePlay panel";
    final static String GAME_OVER_PANEL = "Card with GameOver panel";
    
    public static String background_path = "assets/background/intro/1.jpg";

    public static void main(String[] args) {
        addAudio();
        Thread musicThread = AudioPlayer.playLoopMusic("assets/music/background.wav");
        GameView view = new GameView(); // view

        cards = new JPanel();
        layout = new CardLayout();
        cards.setLayout(layout);

        // TODO Intro Panel
        Intro intro = new Intro(cards);

        // TODO Knight Selection Panel
        List<Knight> team1 = new ArrayList<>();
        List<Knight> team2 = new ArrayList<>();

        Canvas canvas = new Canvas(cards, view, team1, team2, background_path);
        CharacterMenu characterMenu = new CharacterMenu(cards, team1, team2);
        StageMenu stageMenu = new StageMenu(cards, canvas);

        GameOver gameOver = new GameOver(cards);

        // Main Card Layout
        cards.add(intro, INTRO_PANEL);
        cards.add(characterMenu, CHACRACTER_MENU_PANEL);
        cards.add(stageMenu, STAGE_MENU_PANEL);
        cards.add(canvas, GAME_PANEL);
        cards.add(gameOver, GAME_OVER_PANEL);

        view.add(cards, BorderLayout.CENTER);
        view.setVisible(true);
    }

    private static void addAudio() {
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
