package controller;

import model.World;

/**
 * @author - johnny850807@gmail.com (Waterball)
 */
public abstract class GameLoop {
    private boolean running;
    private View view;
    private Thread thread;

    public void setView(View view) {
        this.view = view;
    }
    public View getView() {
        return view;
    }
    public void start() {
        thread = new Thread(this::gameLoop);
        thread.start();
    }

    private void gameLoop() {
        running = true;
        while (running) {
            World world = getWorld();
            world.update();
            view.render(world);
            delay(15);
        }
    }

    protected abstract World getWorld();

    public void stop() {
        running = false;
        thread.interrupt();
    }

    private void delay(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public interface View {

        void render(World world);
    }
}
