package com.battlemath.utils.audio;


import android.content.Context;
import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.util.Log;

import com.battlemath.R;
import com.battlemath.constants.Messages;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class SoundManager {

    private static boolean isMuted = false;
    private static SoundPool soundPool;
    private static final List<Integer> soundResources = Arrays.asList(
            R.raw.random_ac,
            R.raw.random_laser,
            R.raw.random_laser2,
            R.raw.random_mg,
            R.raw.random_uac
    );
    private static final Random random = new Random();

    public static void initSoundPool(Context context) {
        if (soundPool == null) {
            soundPool = new SoundPool.Builder()
                    .setMaxStreams(5)
                    .setAudioAttributes(new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_MEDIA)
                            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .build())
                    .build();
        }
    }

    public static void releaseSoundPool() {
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }

    public static SoundPool getSoundPool(Context context) {
        if (null == soundPool) {
            initSoundPool(context);
        }
        return soundPool;
    }

    public static boolean getIsMuted() {
        return isMuted;
    }

    public static void setIsMuted(boolean muted) {
        isMuted = muted;
    }


    public static void saveMuteState(Context context, boolean isMuted) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("isMuted", isMuted);
        editor.apply();
    }

    public static boolean loadMuteState(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return prefs.getBoolean("isMuted", false);
    }

    public static void playRandomSound(Context context) {
        if (soundPool != null) {
            int randomIndex = random.nextInt(soundResources.size());
            soundPool.load(context, soundResources.get(randomIndex), 1);
            soundPool.setOnLoadCompleteListener((pool, sampleId, status) -> {
                if (status == 0) {
                    pool.play(sampleId, 1, 1, 0, 0, 1);
                }
            });
        } else {
            Log.e("SoundManager", Messages.ERROR_SOUNDPOOL_NOT_INIT);
        }
    }

}
