package media;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class MusicPlayer implements Runnable {
    private String filepath;
    private Clip music;

    public MusicPlayer(String filepath) {
        this.filepath = filepath;
    }

    @Override
    public void run() {
        try {
            File file = new File(filepath);
            if (file.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

                music = AudioSystem.getClip(); // Use Clip for playback
                music.open(audioStream);

                FloatControl gainControl = (FloatControl) music.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-10.0f);

                music.start();
                music.loop(Clip.LOOP_CONTINUOUSLY);
                Thread.sleep(100000);
            } else {
                synchronized (System.out) {
                    System.out.println("Err Music File doesnt exist");
                }
            }
        } catch (Exception e) {
            System.out.println("Exception!!");
            music.stop();
            synchronized (System.out) {
                System.out.println(e);
            }
        }
    }
}
