package com.pacosignes.projecte.articulos;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pacosignes.projecte.Api.APIService;
import com.pacosignes.projecte.Api.APIUtils;
import com.pacosignes.projecte.R;

import java.io.IOException;


import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentArticuloDetalle extends Fragment {

    private Articulo articulo;

    private ImageView ivArticulo;
    private TextView tvRefDetalle;
    private TextView tvDescDetalle;
    private ProgressBar pbArticulo;


    public FragmentArticuloDetalle(Articulo articulo) {
        this.articulo = articulo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.detalle_articulo,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ivArticulo=getActivity().findViewById(R.id.ivArticuloDetalle);
        tvDescDetalle=getActivity().findViewById(R.id.tvcvDescArticulo);
        tvRefDetalle=getActivity().findViewById(R.id.tvcvRefArticulo);
        pbArticulo=getActivity().findViewById(R.id.pbLoadingImageDetailArticulo);
        tvRefDetalle.setText(String.valueOf(articulo.getRef()));
        tvDescDetalle.setText(articulo.getDescripcion());
        APIService service= APIUtils.getAPIService();
        service.getImageArticulo(String.valueOf(articulo.getRef())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {

                    String encodedImage = response.body().string();
                    if(encodedImage.equals("notFound\r\n")) {
                        int id= getResources().getIdentifier("image_not_found","drawable",getActivity().getPackageName());
                        pbArticulo.setVisibility(View.INVISIBLE);
                        ivArticulo.setImageResource(id);
                    }else{
                        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.
                                decodeByteArray(decodedString, 0, decodedString.length);
                        pbArticulo.setVisibility(View.INVISIBLE);
                        ivArticulo.setImageBitmap(decodedByte);
                    }
                }catch (IOException ioe){

                }catch (NullPointerException npe){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }



}
