package com.movie.movie.chatting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.movie.movie.R;
import com.movie.movie.friends.MainActivity;
import com.movie.movie.like.LikeActivity;

import java.util.ArrayList;

public class ChattingActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<ChattingDatas> chattingDatas;
    ChattingAdapter chattingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting);

        ImageView go_friend = (ImageView)findViewById(R.id.go_friend);
        ImageView go_chat = (ImageView)findViewById(R.id.go_chat);
        ImageView go_like = (ImageView)findViewById(R.id.go_like);

        go_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
            }
        });
        go_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ChattingActivity.class));
            }
        });
        go_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LikeActivity.class));
            }
        });

        listView = (ListView) findViewById(R.id.listView);
        chattingDatas = new ArrayList<ChattingDatas>();
        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));
//        chattingDatas.add(new ChattingDatas(null,"title","content","4번째"));

        chattingAdapter = new ChattingAdapter(chattingDatas, getApplicationContext());
        listView.setAdapter(chattingAdapter);

    }
}
