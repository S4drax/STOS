package commhandler;

import lombok.Getter;

@Getter
public class SoundBase {
    SoundPlayer soundPlayer = new SoundPlayer();

    Runnable soundHonk = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/hev_logon.wav");
        }
    };

    Runnable hum = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/hum.wav");
        }
    };

    Runnable sLaunch = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/login.wav");
        }
    };

    Runnable sCorrect = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/correct.wav");
        }
    };
    Runnable sIncorrect = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/incorrect.wav");
        }
    };
    Runnable sLocked = new Runnable(){
        public void run(){
            soundPlayer.playSound(System.getProperty("user.dir")+"/sounds/locked.wav");
        }
    };
}
