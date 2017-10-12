package com.movie.movie.first;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.movie.movie.ApplicationController;
import com.movie.movie.NetworkService;
import com.movie.movie.R;
import com.movie.movie.friends.MainActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class LoginActivity extends AppCompatActivity {

    EditText edt_id;
    EditText edt_pwd;
    ImageView btn_login;
    ImageView btn_signup;
    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        edt_id = (EditText)findViewById(R.id.edt_id);
        edt_pwd = (EditText)findViewById(R.id.edt_pwd);
        btn_login = (ImageView)findViewById(R.id.btn_login);
        btn_signup = (ImageView)findViewById(R.id.btn_signup);

        service = ApplicationController.getInstance().getNetworkService();

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplication(),SignupActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("butn","진입");
//                RequestBody id = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edt_id.getText()));
//                RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edt_pwd.getText()));

                loginData data = new loginData();
                data.id = String.valueOf(edt_id.getText());
                data.password = String.valueOf(edt_pwd.getText());

                Call<loginresult> GetLogin = service.getLogin(data);
                GetLogin.enqueue(new Callback<loginresult>(){

                    @Override
                    public void onResponse(Call<loginresult> call, Response<loginresult> response) {
                        if(response.isSuccessful()){
                            Log.i("hi","success");
                            if(response.body().message.equals("ok")){
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                intent.putExtra("name",response.body().name);
                                startActivity(intent);
                                finish();
                            }
                            else if(response.body().message.equals("fail")){
                                makeText(getApplicationContext(),"로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<loginresult> call, Throwable t) {
                        makeText(getApplicationContext(),"통신에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}
