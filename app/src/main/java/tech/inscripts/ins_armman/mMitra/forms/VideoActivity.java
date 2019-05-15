package tech.inscripts.ins_armman.mMitra.forms;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.MediaController;
import android.widget.VideoView;
import tech.inscripts.ins_armman.mMitra.R;

import static tech.inscripts.ins_armman.mMitra.data.database.DatabaseContract.VIDEO_PATH;


public class VideoActivity extends AppCompatActivity {

    VideoView videoView;
    String videoname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        Intent getMessage = getIntent();
        videoname = getMessage.getStringExtra("VideoKeyword");

        videoView = (VideoView) findViewById(R.id.videoView);

        //Creating MediaController
        MediaController mediaController = new MediaController(VideoActivity.this);
        mediaController.setAnchorView(videoView);

        //specify the location of media file
        Uri uri = Uri.parse(VIDEO_PATH + videoname + ".mp4");
        //Setting MediaController and URI, then starting the AnimView
        videoView.setMediaController(mediaController);
        //AnimView.setRotation(90f);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();

        if (savedInstanceState != null) {
            int a = savedInstanceState.getInt("cp");
            videoView.seekTo(a);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("cp", videoView.getCurrentPosition());
    }
}
