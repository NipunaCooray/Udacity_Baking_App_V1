package com.example.nipunac.bakingapp_v1.network;

import com.example.nipunac.bakingapp_v1.model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface EndPoints {

    //https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json

    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();


}
