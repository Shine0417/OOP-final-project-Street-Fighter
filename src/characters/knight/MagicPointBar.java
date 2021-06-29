package characters.knight;


import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class MagicPointBar extends HealthPointBar {

    public MagicPointBar(int mp) {
        super(mp);
    }

    public void setMp(int mp) {
        setHp(mp);
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * owner.getRange().getWidth() / maxHp);
        g.setColor(Color.RED);
        g.fillRect(range.x, range.y, (int) owner.getRange().getWidth(), range.height);
        g.setColor(Color.BLUE);
        g.fillRect(range.x, range.y, width, range.height);
    }

    @Override
    public Rectangle getRange() {
        return new Rectangle(owner.getX(), owner.getY() - 15, (int) owner.getRange().getWidth(), 10);
    }
}
