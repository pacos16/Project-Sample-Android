package com.pacosignes.projecte;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.pacosignes.projecte.Api.APIService;
import com.pacosignes.projecte.Api.APIUtils;
import com.pacosignes.projecte.articulos.Articulo;
import com.pacosignes.projecte.articulos.FragmentArticulos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private APIService apiService;
    private ImageView ivSplashLogo;
    private ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        actionBar=getSupportActionBar();
        actionBar.hide();
        ivSplashLogo=findViewById(R.id.ivSplashLogo);
        Animation splashAnim= AnimationUtils.loadAnimation(this,R.anim.splash_anim);
        ivSplashLogo.startAnimation(splashAnim);
        apiService= APIUtils.getAPIService();

        apiService.getArticulos().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ArrayList<Articulo> articulos=new ArrayList<>();
                try {
                    JSONTokener tokener = new JSONTokener(response.body().string());
                    JSONArray jsonArray=new JSONArray(tokener);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        try{
                            String refString= jsonObject.getString("ref");
                            String descripcion= jsonObject.getString("descripcion");
                            int ref=Integer.parseInt(refString);
                            if(ref>200 && ref<700 ){
                                articulos.add(new Articulo(ref,descripcion,null));
                            }
                            Collections.sort(articulos);
                        }catch (NumberFormatException nfe){

                        }
                    }


                    try{
                        Thread.sleep(1000);
                    }catch (InterruptedException ie){

                    }

                    FragmentArticulos fragmentArticulos=new FragmentArticulos(articulos,actionBar);
                    getSupportFragmentManager().beginTransaction().replace(R.id.clSplahWrapper,fragmentArticulos).commit();

                }catch (IOException ioe){

                }catch (JSONException jsone){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }






}
