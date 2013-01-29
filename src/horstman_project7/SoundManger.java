/*
 * Clayton horstman
 * your lab section:13		
 * your lab TA’s name: vinh nquyen
 * your instructor’s name:Dr DeJongh
 */
package horstman_project7;

import java.util.ArrayList;

/**
 *
 * @author viro
 */
/*
 * manages the sound for the game
 */
public abstract class SoundManger {

    public ArrayList<Sound> sounds = new ArrayList<Sound>();

    public SoundManger() {
        initSounds();

    }

    public abstract void initSounds();

    public void addSound(Sound sound) {
        sounds.add(sound);
    }

    public void removeSound(Sound sound) {
        sounds.remove(sound);
    }

    public void playSound(String name) {
        boolean played = false;
        for (Sound s : sounds) {
            if (s.name.equals(name)) {
                s.play();

            }


        }
    }

    public void loopSound(String name) {
        for (Sound s : sounds) {
            if (s.name.equals(name)) {
                s.loop();
            }

        }
    }

    public void stopSound(String name) {
        for (Sound s : sounds) {
            if (s.name.equals(name)) {
                s.stop();
            }

        }
    }

    public void stopAllSounds() {
        for (Sound s : sounds) {
            s.stop();
        }

    }
}
