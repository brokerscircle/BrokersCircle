package brokerscirlce.com.util;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;

import java.io.File;
import java.io.IOException;
import java.util.Random;


public class AudioRecording {

    static Context  mContext;
    static String audioSavePathInDevice = null;
    static MediaRecorder mediaRecorder ;
    Random random ;
    String RandomAudioFileName = "voicerecording";
    public static final int RequestPermissionCode = 1;
    static MediaPlayer mediaPlayer;

    public static void startRecording() {

        //Check if playing already then stop playing
        mediaPlayer = new MediaPlayer();
        mediaPlayer.stop();

        audioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/AudioRecording.3gp";
        MediaRecorderReady();
        try {
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void stopRecording() {
        mediaRecorder.stop();
    }

    private static void MediaRecorderReady() {

        mediaRecorder=new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(audioSavePathInDevice);
    }


    public static MediaPlayer playRecording(Context mContext) throws IOException {
        Uri myUri = Uri.fromFile(new File(audioSavePathInDevice));
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(mContext, myUri);
        mediaPlayer.prepare();
        mediaPlayer.start();


//        try {
//            mediaPlayer.setDataSource(audioSavePathInDevice);
//            mediaPlayer.prepare();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        mediaPlayer.start();
        //return mediaPlayer.getCurrentPosition();
        return mediaPlayer;
    }

    public static void PauseRecording() {
        if(mediaPlayer != null){
            mediaPlayer.stop();
            mediaPlayer.release();
            MediaRecorderReady();
        }
    }


}
