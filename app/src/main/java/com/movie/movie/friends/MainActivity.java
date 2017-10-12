package com.movie.movie.friends;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.movie.movie.R;
import com.movie.movie.chatting.ChattingActivity;
import com.movie.movie.like.LikeActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<FriendDatas> friendDatas;
    FriendAdapter friendAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        friendDatas = new ArrayList<FriendDatas>();
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

        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","exmaple"));
//        friendDatas.add(new FriendDatas(null,"이거","잘되네"));




        friendAdapter = new FriendAdapter(friendDatas, getApplicationContext());
        listView.setAdapter(friendAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
            }
        });
    }
}
