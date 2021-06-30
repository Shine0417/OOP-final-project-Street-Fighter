package controller;

import characters.knight.Knight;
import model.Direction;
import java.util.List;
import model.World;
import views.Canvas;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private static List<Knight> team1, team2;
    private static int current1, current2;
    private static World world;

    public Game(World world, List<Knight> team1, List<Knight> team2, int first_rival_id) {
        this.team1 = team1;
        this.team2 = team2;
        this.current1 = this.current2 = first_rival_id;
        this.world = world;
        Game.world.setGame(this);
    }

    public void moveKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
    }

    public void changeKnight(int playerNumber) {
        if (getPlayer(playerNumber).isJumping())
            return;
        if (playerNumber == 1) {
            int anotherPlayer = (current1 + 1) % team1.size();
            if (!team1.get(anotherPlayer).isDead()) {
                changeKnight(team1.get(current1), team1.get(anotherPlayer));
                current1 = anotherPlayer;
            }
        } else if (playerNumber == 2) {
            int anotherPlayer = (current2 + 1) % team2.size();
            if (!team2.get(anotherPlayer).isDead()) {
                changeKnight(team2.get(current2), team2.get(anotherPlayer));
                current2 = anotherPlayer;
            }
        }
    }

    public void changeKnight(Knight oldKnight, Knight newKnight) {
        newKnight.setLocation(oldKnight.getLocation());
        newKnight.setFace(oldKnight.getFace());
        newKnight.getDirections().clear();
        if (!oldKnight.getDirections().isEmpty()) {
            newKnight.getDirections().add(newKnight.getFace());
            newKnight.triggerWalk();
        }

        oldKnight.resetFsm();

        world.removeSprite(oldKnight);
        world.addSprite(newKnight);
    }

    public void jumpKnight(int playerNumber) {
        getPlayer(playerNumber).jump();
    }

    public void crouchKnight(int playerNumber) {
        getPlayer(playerNumber).crouch();
    }

    public void stopCrouchKnight(int playerNumber) {
        getPlayer(playerNumber).stopCrouch();
    }

    public void stopKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).stop(direction);
    }

    public void attack(int playerNumber) {
        getPlayer(playerNumber).attack();
    }

    public void kick(int playerNumber) {
        getPlayer(playerNumber).kick();
    }

    public void skill(int playerNumber, int skill_id) {
        getPlayer(playerNumber).skill(skill_id);
    }

    public Knight getPlayer(int playerNumber) {
        return playerNumber == 1 ? team1.get(current1) : team2.get(current2);
    }

    @Override
    protected World getWorld() {
        return world;
    }

    public Boolean checkGameOver() {
        if ((team1.stream().filter(knight -> !knight.isDead()).count() == 0)
                || (team2.stream().filter(knight -> !knight.isDead()).count() == 0)) {
            System.out.println("GameOver");
            stop();
            ((Canvas)getView()).gameOver();
            return true;
        }
        return false;
    }

}
