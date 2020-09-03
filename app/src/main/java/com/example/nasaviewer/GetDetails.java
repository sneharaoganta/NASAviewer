package com.example.nasaviewer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.net.URI;
import java.net.URISyntaxException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetDetails extends AppCompatActivity {

    String date,api_key,vid;
    TextView Result,Descrip,Date,vid_link;
    ImageView picture;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        date = getIntent().getExtras().getString("Date");
        setContentView(R.layout.activity_get_details);
        Date=findViewById(R.id.dateview);
        Result=findViewById(R.id.result);
        Descrip=findViewById(R.id.description);
        vid_link=findViewById(R.id.VidLink);



        api_key="ircRyFMqll4Z1GfwFyFvfP2U8xkjCgrVv1HD6aod";
        picture=findViewById(R.id.imageview);
        picture.setClickable(false);

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi =retrofit.create(JsonPlaceHolderApi.class);

        Call<info> call = jsonPlaceHolderApi.getinfo(api_key,date);
        call.enqueue(new Callback<info>() {
            private Object infor;

            @Override
            public void onResponse(Call<info> call, Response<info> response) {
                if(!response.isSuccessful()){
                    Result.setText("Code :"+ response.code());
                    return;
                }




                infor = response.body();
                    Result.setText(((info) infor).getTitle());
                    Descrip.setText(((info) infor).getExplanation());
                    Date.setText("Date= "+ ((info) infor).getDate());
                    if(((info) infor).getMedia_type().equals("video")){
                        vid_link.setText(((info) infor).getUrl());
                        picture.setImageResource(R.drawable.ic_play);
                        picture.setClickable(true);
                        picture.setScaleType(ImageView.ScaleType.FIT_XY);
                        picture.setAdjustViewBounds(true);
                        picture.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(((info) infor).getUrl())));
                            }
                        });


                    }
                    else{
                        Glide.with(GetDetails.this).load(((info) infor).getUrl()).into(picture);

                    }



                }




            @Override
            public void onFailure(Call<info> call, Throwable t) {
                Result.setText(t.getMessage());

            }
        });
    }
}
