package com.example.nipunac.bakingapp_v1.network;

import com.example.nipunac.bakingapp_v1.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkUtils {

    final static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/" ;

    public static void getRecipiesFromURL(final OnRequestFinishedListener listener){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        EndPoints retrofitEndPoint = retrofit.create(EndPoints.class);
        Call<List<Recipe>> call = retrofitEndPoint.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                listener.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }
}
