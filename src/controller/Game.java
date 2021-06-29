package controller;

import characters.knight.Knight;
import model.Direction;
import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class Game extends GameLoop {
    private final Knight p1;
    private final Knight p2;
    private final World world;

    public Game(World world, Knight p1, Knight p2) {
        this.p1 = p1;
        this.p2 = p2;
        this.world = world;
    }

    public void moveKnight(int playerNumber, Direction direction) {
        getPlayer(playerNumber).move(direction);
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
        return playerNumber == 1 ? p1 : p2;
    }

    @Override
    protected World getWorld() {
        return world;
    }

}
