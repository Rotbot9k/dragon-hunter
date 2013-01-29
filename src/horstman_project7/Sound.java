/*
 * Clayton horstman
 * your lab section:13		
 * your lab TA’s name: vinh nquyen
 * your instructor’s name:Dr DeJongh
 */
package horstman_project7;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;

/**
 *
 * @author viro
 */
public class Sound {

    private static Sound staticSound = new Sound();
    public String name;
    public AudioClip sound;

    private Sound() {
    }

    public Sound(String name, URL url) {
        this.name = name;
        try {
            sound = Applet.newAudioClip(url);
        } catch (Exception e) {
            e.printStackTrace(System.err);
        }

    }

    public void play() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (sound != null) {
                    sound.play();
                }
            }
        }).start();
    }
    /*
     * loops the sound loaded
     */

    public void loop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (sound != null) {
                    sound.loop();
                }
            }
        }).start();
    }

    public void stop() {
        if (sound != null) {
            sound.stop();
        }
    }
    /*
     * loads the audio 
     */

    public static URL getURL(String fileName) {


        return staticSound.getClass().getResource("Sounds/" + fileName);
    }
}
