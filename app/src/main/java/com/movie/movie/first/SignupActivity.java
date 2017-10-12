package com.movie.movie.first;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.movie.movie.ApplicationController;
import com.movie.movie.NetworkService;
import com.movie.movie.R;
import com.movie.movie.resultMessage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.makeText;

public class SignupActivity extends AppCompatActivity {

    final int REQ_CODE_SELECT_IMAGE = 100;

    EditText edt_id;
    EditText edt_email;
    EditText edt_pwd;
    EditText edt_pwd_0;
    EditText edt_name;
    String id;
    String user_email;

    String imgUrl = "";
    Uri data;

    //    ImageView setpicture;
    ImageView email_test;
    ImageView number_test;
    ImageView btn_signup;

    NetworkService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        edt_name = (EditText) findViewById(R.id.edt_name);
        edt_id = (EditText)findViewById(R.id.edt_id);
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_pwd = (EditText)findViewById(R.id.edt_pwd);
        edt_pwd_0 = (EditText)findViewById(R.id.edt_pwd_0);
//        ImageView setpicture = (ImageView)findViewById(R.id.setpicture);
        ImageView email_test = (ImageView)findViewById(R.id.email_test);
        ImageView id_test = (ImageView)findViewById(R.id.id_test);
        ImageView btn_signup = (ImageView)findViewById(R.id.btn_signup);

        service = ApplicationController.getInstance().getNetworkService();

        email_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<resultMessage> EmailCheck = service.emailCheck(String.valueOf(edt_email.getText()));
                EmailCheck.enqueue(new Callback<resultMessage>() {
                    @Override
                    public void onResponse(Call<resultMessage> call, Response<resultMessage> response) {
                        if(response.isSuccessful()){
                            if(response.body().message.equals("yes")){
                                user_email = String.valueOf(edt_email.getText());
                                makeText(getApplicationContext(),"email이 사용가능합니다.", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.body().message.equals("no")){
                                makeText(getApplicationContext(),"email이 중복되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<resultMessage> call, Throwable t) {

                    }
                });
            }
        });

//        setpicture.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_PICK);
//                intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
//            }
//        });

        id_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<resultMessage> IDCHECK = service.idCheck(String.valueOf(edt_id.getText()));
                IDCHECK.enqueue(new Callback<resultMessage>() {
                    @Override
                    public void onResponse(Call<resultMessage> call, Response<resultMessage> response) {
                        if(response.isSuccessful()){
                            if(response.body().message.equals("no user")){
                                id = String.valueOf(edt_id.getText());
                                makeText(getApplicationContext(),"id가 사용가능합니다.", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.body().message.equals("no")){
                                makeText(getApplicationContext(),"id가 중복되었습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<resultMessage> call, Throwable t) {
                        makeText(getApplicationContext(),"통신 error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(id.equals(String.valueOf(edt_id.getText()))&&user_email.equals(String.valueOf(edt_email.getText()))) {
                    Log.i(id,user_email);
                    if(String.valueOf(edt_pwd.getText()).equals(String.valueOf(edt_pwd_0.getText()))){
                        RequestBody userid = RequestBody.create(MediaType.parse("multipart/form-data"), id);
                        RequestBody email = RequestBody.create(MediaType.parse("multipart/form-data"), user_email);
                        RequestBody password = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edt_pwd.getText()));
                        RequestBody name = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(edt_name.getText()));
                        MultipartBody.Part img;

                        if (imgUrl == "") {
                            img = null;
                        } else{
                            BitmapFactory.Options options = new BitmapFactory.Options();
//                       options.inSampleSize = 4; //얼마나 줄일지 설정하는 옵션 4--> 1/4로 줄이겠다

                            InputStream in = null; // here, you need to get your context.
                            try {
                                in = getContentResolver().openInputStream(data);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }
                        /*inputstream 형태로 받은 이미지로 부터 비트맵을 만들어 바이트 단위로 압축
                        그이우 스트림 배열에 담아서 전송합니다.
                         */

                            Bitmap bitmap = BitmapFactory.decodeStream(in, null, options); // InputStream 으로부터 Bitmap 을 만들어 준다.
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
                            // 압축 옵션( JPEG, PNG ) , 품질 설정 ( 0 - 100까지의 int형 ), 압축된 바이트 배열을 담을 스트림
                            RequestBody photoBody = RequestBody.create(MediaType.parse("image/jpg"), baos.toByteArray());

                            File photo = new File(imgUrl); // 가져온 파일의 이름을 알아내려고 사용합니다

                            Log.i("QWE", imgUrl);

                            // MultipartBody.Part 실제 파일의 이름을 보내기 위해 사용!!
                            img = MultipartBody.Part.createFormData("image", photo.getName(), photoBody);


                        }

                        Call<resultMessage> PostSignup = service.postSignup(userid,password,email,name,img);
                        PostSignup.enqueue(new Callback<resultMessage>() {
                            @Override
                            public void onResponse(Call<resultMessage> call, Response<resultMessage> response) {
                                if(response.isSuccessful()){
                                    if(response.body().message.equals("ok")){
                                        makeText(getApplicationContext(),"회원가입에 성공하였습니다.", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                        finish();
                                    }
                                    else{
                                        makeText(getApplicationContext(),"회원가입에 실패하였습니다.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<resultMessage> call, Throwable t) {
                                makeText(getApplicationContext(),"통신 error", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                } else {
                    makeText(getApplicationContext(),"id와 전화번호 중복체크를 해주세요.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public String getImageNameToUri(Uri data) {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        imgUrl = imgPath;
        return imgName;
    }
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == REQ_CODE_SELECT_IMAGE) {
//            if (resultCode == Activity.RESULT_OK) {
//                try {
//                    //Uri에서 이미지 이름을 얻어온다.
//                    String name_Str = getImageNameToUri(data.getData());
//                    this.data = data.getData();
//
//                    Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//
//                    ImageView image = (ImageView) findViewById(R.id.user_img);
//
//                    //배치해놓은 ImageView에 set
//                    image.setImageBitmap(image_bitmap);
//
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                imgUrl = "";
//            }
//        }
//    }
}