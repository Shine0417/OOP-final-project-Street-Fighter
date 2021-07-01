package characters.knight;

import model.Sprite;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class HealthPointBar extends Sprite {
    protected final int maxHp;
    protected Sprite owner;
    protected int hp;

    public HealthPointBar(int hp) {
        this.maxHp = this.hp = hp;
    }

    public void setOwner(Sprite owner) {
        this.owner = owner;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void update() {
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * range.width / maxHp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, range.width, range.height);
        g.setColor(Color.GREEN);

        if (owner.getTeam() == 1)
            g.fillRect(range.x, range.y, width, range.height);
        else
            g.fillRect(range.x + range.width - width, range.y, width, range.height);
    }

    @Override
    public void onDamaged(Rectangle damageArea, int damage) {
        this.hp = Math.max(hp - damage, 0);
    }

    @Override
    public Rectangle getRange() {
        // return new Rectangle(owner.getX(), owner.getY() - 30, (int)
        // owner.getRange().getWidth(), 10);
        if (owner.getTeam() == 1) {
            return new Rectangle(10, 10, 450, 15);
        } else {
            return new Rectangle(840, 10, 450, 15);
        }

    }

    @Override
    public Dimension getBodyOffset() {
        return new Dimension();
    }

    @Override
    public Dimension getBodySize() {
        return new Dimension();
    }

    public boolean isDead() {
        return hp <= 0;
    }
}
