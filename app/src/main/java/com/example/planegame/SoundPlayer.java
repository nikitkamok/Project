package com.example.planegame;

import android.content.Context;
import android.media.MediaPlayer;

public class SoundPlayer {

    private static SoundPlayer instance;
    private MediaPlayer mediaPlayer;

    private SoundPlayer() {}

    public static SoundPlayer getInstance() {
        if(instance == null) {
            instance = new SoundPlayer();
        }
        return instance;
    }

    //Запускаем музыку
    public void playSound(Context context, int resId) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = MediaPlayer.create(context, resId);
        mediaPlayer.setLooping(true); // Повторять воспроизведение
        mediaPlayer.start();
    }
    //Останавливаем музыку
    public void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
    //Регулируем громкость
    public void setVolume(float volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume, volume);
        }
    }
    //Проверяем играет ли музыка
    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }
}