// SequentialSoundPlayer.java
package com.battlemath.utils.audio;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.Log;

import com.battlemath.R;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.Random;

public class SequentialSoundPlayer {
    private static final String TAG = "SequentialSoundPlayer";
    private static SequentialSoundPlayer instance;
    private final Random random = new Random();
    private final Context appContext;
    private final Queue<SoundItem> queue = new ArrayDeque<>();
    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private static final List<Integer> soundResources = Arrays.asList(
            R.raw.random_ac,
            R.raw.random_laser,
            R.raw.random_laser2,
            R.raw.random_mg,
            R.raw.random_uac
    );

    private SequentialSoundPlayer(Context context) {
        this.appContext = context.getApplicationContext();
    }

    public static synchronized SequentialSoundPlayer getInstance(Context context) {
        if (instance == null) {
            instance = new SequentialSoundPlayer(context);
        }
        return instance;
    }

    // wrapper per item in queue
    private static class SoundItem {
        final int resId;
        final float volume; // 0..1

        SoundItem(int resId, float volume) {
            this.resId = resId;
            this.volume = Math.max(0f, Math.min(1f, volume));
        }
    }

    public synchronized void enqueue(int resId, float volume) {
        queue.offer(new SoundItem(resId, volume));
        if (!isPlaying) {
            playNext();
        }
    }

    public synchronized void playNow(int resId, float volume) {
        queue.clear();
        stopCurrent();
        queue.offer(new SoundItem(resId, volume));
        playNext();
    }

    private synchronized void playNext() {
        if (SoundManager.getIsMuted()) {
            queue.clear();
            isPlaying = false;
            stopCurrent();
            return;
        }

        SoundItem item = queue.poll();
        if (item == null) {
            isPlaying = false;
            return;
        }

        isPlaying = true;
        try {
            mediaPlayer = MediaPlayer.create(appContext, item.resId);
            if (mediaPlayer == null) {
                Log.e(TAG, "MediaPlayer.create returned null for resId=" + item.resId);
                // prova il prossimo
                isPlaying = false;
                playNext();
                return;
            }

            // Imposta volume (stereo)
            mediaPlayer.setVolume(item.volume, item.volume);

            mediaPlayer.setOnCompletionListener(mp -> {
                try {
                    mp.release();
                } catch (Exception e) { /* ignore */ }
                mediaPlayer = null;
                // audio finito -> riproduci il prossimo
                synchronized (SequentialSoundPlayer.this) {
                    isPlaying = false;
                    playNext();
                }
            });

            mediaPlayer.setOnErrorListener((mp, what, extra) -> {
                Log.e(TAG, "MediaPlayer error what=" + what + " extra=" + extra);
                try { mp.release(); } catch (Exception ignored) {}
                mediaPlayer = null;
                synchronized (SequentialSoundPlayer.this) {
                    isPlaying = false;
                    playNext();
                }
                return true;
            });

            // Start only if not muted (we already checked, but double-check)
            if (!SoundManager.getIsMuted()) {
                mediaPlayer.start();
            } else {
                // se mute, rilascio e passo al prossimo
                try { mediaPlayer.release(); } catch (Exception ignored) {}
                mediaPlayer = null;
                isPlaying = false;
                playNext();
            }
        } catch (Exception e) {
            Log.e(TAG, "Failed to play sound res:" + item.resId, e);
            if (mediaPlayer != null) {
                try { mediaPlayer.release(); } catch (Exception ignored) {}
                mediaPlayer = null;
            }
            isPlaying = false;
            playNext();
        }
    }

    public synchronized void stopAndClear() {
        queue.clear();
        stopCurrent();
        isPlaying = false;
    }

    private synchronized void stopCurrent() {
        if (mediaPlayer != null) {
            try {
                if (mediaPlayer.isPlaying()) mediaPlayer.stop();
            } catch (Exception ignored) {}
            try { mediaPlayer.release(); } catch (Exception ignored) {}
            mediaPlayer = null;
        }
    }

    public synchronized int pendingCount() {
        return queue.size();
    }

    public void playRandomSound() {
        int randomIndex = random.nextInt(soundResources.size());
        int soundRes = soundResources.get(randomIndex);
        enqueue(soundRes, 1.0f); // volume pieno
    }

}
