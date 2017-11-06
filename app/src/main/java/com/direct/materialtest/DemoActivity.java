package com.direct.materialtest;

import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.direct.materialtest.adapter.DemoMusicAdapter;
import com.direct.materialtest.base.BaseActivity;
import com.direct.materialtest.music.bean.MusicMedia;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;

import java.io.IOException;
import java.util.ArrayList;

public class DemoActivity extends BaseActivity {
    private int count=1;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    private DemoMusicAdapter demoMusicAdapter;
    private XRecyclerView demorecyclerview;
    public static ArrayList<MusicMedia> musicList = new ArrayList<>();; //音乐信息列表


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        SetToolBar(R.id.toobar, R.id.toobar_title, "音乐");
        init();
    }

    private void init() {
        demoMusicAdapter=new DemoMusicAdapter(this,scanAllAudioFiles());
        demorecyclerview= (XRecyclerView) findViewById(R.id.demo_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        demorecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        demorecyclerview.setLoadingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        demorecyclerview.setArrowImageView(R.drawable.iconfont_downgrey);
        demorecyclerview.setLayoutManager(layoutManager);
        demorecyclerview.setAdapter(demoMusicAdapter);
        demoMusicAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder,  int position) {
                MusicMedia musicMedia=musicList.get(position-1);
                playmusic(position);
                //Toast.makeText(DemoActivity.this, musicMedia.getUrl(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });

        demorecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        musicList.clear();
                        scanAllAudioFiles();
                        demoMusicAdapter.notifyDataSetChanged();
                        demorecyclerview.refreshComplete();
                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            //musicList.clear();
                            //scanAllAudioFiles();
                            demorecyclerview.setNoMore(true);
                            demoMusicAdapter.notifyDataSetChanged();
                        }
                    }, 1000);

            }
        });


    }

   private void playmusic(int position){
       MusicMedia musicMedia=musicList.get(position-1);
        try {
            mediaPlayer.setDataSource(musicMedia.getUrl());
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /*加载媒体库里的音频*/
    public ArrayList<MusicMedia> scanAllAudioFiles(){
        /*查询媒体数据库
        参数分别为（路径，要查询的列名，条件语句，条件参数，排序）
        视频：MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        图片;MediaStore.Images.Media.EXTERNAL_CONTENT_URI
         */
        Cursor cursor = getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        //遍历媒体数据库
        if(cursor.moveToFirst()){
            while (!cursor.isAfterLast()) {
                //歌曲编号
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                //歌曲标题
                String tilte = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                //歌曲的专辑名：MediaStore.Audio.Media.ALBUM
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
                //歌曲的专辑id
                int albumId = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                //歌曲的歌手名： MediaStore.Audio.Media.ARTIST
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                //歌曲文件的路径 ：MediaStore.Audio.Media.DATA
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                //歌曲的总播放时长 ：MediaStore.Audio.Media.DURATION
                int duration = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                //歌曲文件的大小 ：MediaStore.Audio.Media.SIZE
                Long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));


                if (size >1024*800){//大于800K
                    MusicMedia musicMedia = new MusicMedia();
                    musicMedia.setId(id);
                    musicMedia.setArtist(artist);
                    musicMedia.setSize(size);
                    musicMedia.setTitle(tilte);
                    musicMedia.setTime(duration);
                    musicMedia.setUrl(url);
                    musicMedia.setAlbum(album);
                    musicMedia.setAlbumId(albumId);

                    musicList.add(musicMedia);

                }
                cursor.moveToNext();
            }
        }
        return musicList;
    }




    private Toolbar SetToolBar(int Id, int titleId, String titleString) {
        Toolbar toolbar = (Toolbar) findViewById(Id);
        TextView toobar_title = (TextView) findViewById(titleId);
        toobar_title.setText(titleString);
        //toolbar.setTitle("外婆家的小院");//设置标题
        //toolbar.setSubtitle("外婆家的小院");//设置副标题
        //toolbar.setSubtitleTextColor(Color.BLACK);  //设置副标题字体颜色
        setSupportActionBar(toolbar);   //必须使用
        //设置Menu菜单位置显示在toobar下面
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }

        return toolbar;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
