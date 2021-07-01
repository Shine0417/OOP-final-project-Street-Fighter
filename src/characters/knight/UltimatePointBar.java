package characters.knight;

import java.awt.*;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public class UltimatePointBar extends HealthPointBar {
    private int up_to_add = 50;

    public UltimatePointBar(int up) {
        super(up);
        this.hp = 0;
    }

    public void setUp(int up) {
        setHp(up);
    }

    public boolean max(){
        return hp == maxHp;
    }


    public void addUp() {
        if (hp + up_to_add < maxHp) {
            setHp(hp + up_to_add);
        } else {
            setHp(maxHp);
        }
    }

    @Override
    public void render(Graphics g) {
        Rectangle range = getRange();
        int width = (int) (hp * range.width / maxHp);
        g.setColor(Color.gray);
        g.fillRect(range.x, range.y, range.width, range.height);
        g.setColor(Color.cyan);

        if (owner.getTeam() == 1)
            g.fillRect(range.x, range.y, width, range.height);
        else
            g.fillRect(range.x + range.width - width, range.y, width, range.height);
    }

    @Override
    public Rectangle getRange() {
        if (owner.getTeam() == 1) {
            return new Rectangle(10, 60, 400, 15);
        } else {
            return new Rectangle(890, 60, 400, 15);
        }

    }
}
