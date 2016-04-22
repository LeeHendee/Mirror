package com.my.mirror.activity;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.my.mirror.R;
import com.my.mirror.base.BaseActivity;
import com.my.mirror.bean.AllGoodsListData;
import com.my.mirror.adapter.PicturesListViewAdapter;

import java.util.List;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by dllo on 16/3/29.
 */
public class PicturesActivity extends BaseActivity  {
    private ListView mListView;
    private PicturesListViewAdapter mAdapter;
    private List<AllGoodsListData.DataEntity.ListEntity.WearVideoEntity>  picturesData;
    private JCVideoPlayer jPlayer;
    private ImageView coverIv,playIv;

    @Override
    protected int getLayout() {
        return R.layout.activity_pictures;
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        picturesData = (List<AllGoodsListData.DataEntity.ListEntity.WearVideoEntity>) intent.getSerializableExtra("video_picture");
        mAdapter = new PicturesListViewAdapter(this,picturesData);
        mListView.setAdapter(mAdapter);

        //插入视频的头布局:
        View videoHeadView = LayoutInflater.from(this).inflate(R.layout.video_headview,null);
        coverIv = (ImageView) videoHeadView.findViewById(R.id.iv_video_cover);
        playIv = (ImageView) videoHeadView.findViewById(R.id.iv_video_play);

        //JCVideo 视频播放;
        jPlayer = (JCVideoPlayer) videoHeadView.findViewById(R.id.jcv_id);
        for (int i = 0; i < 5; i++) {
            if (Integer.parseInt(picturesData.get(i).getType()) == 8) {
                jPlayer.setUp(picturesData.get(i).getData(), "");
            }

            if (Integer.valueOf(picturesData.get(i).getType()) == 9) {
                Picasso.with(this).load(Uri.parse(picturesData.get(i).getData())).into(coverIv);
                jPlayer.ivThumb.setVisibility(View.VISIBLE);

            }
            coverIv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playIv.setVisibility(View.INVISIBLE);
                    coverIv.setVisibility(View.INVISIBLE);
                    jPlayer.ivStart.performClick();
                }
            });

        }

        mListView.addHeaderView(videoHeadView);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PicturesActivity.this, SinglePictureActivity.class);
                if (Integer.valueOf(picturesData.get(position).getType()) == 5) {
                    String singleUrl = picturesData.get(position).getData();
                    intent.putExtra("bitmap_url", singleUrl);
                }
                startActivity(intent);
            }
        });

    }

    @Override
    protected void initView() {
        mListView = findId(R.id.lv_pictures);

    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
