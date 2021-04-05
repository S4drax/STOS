package commhandler;

import lombok.Getter;

@Getter
public class SoundBase {
    SoundPlayer soundPlayer = new SoundPlayer();

    Runnable soundHonk = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/hev_logon.wav");
        }
    };

    Runnable hum = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/hum.wav");
        }
    };

    Runnable sLaunch = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/login.wav");
        }
    };

    Runnable sCorrect = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/correct.wav");
        }
    };
    Runnable sIncorrect = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/incorrect.wav");
        }
    };
    Runnable sLocked = new Runnable(){
        public void run(){
            soundPlayer.playSound("sounds/locked.wav");
        }
    };
}
