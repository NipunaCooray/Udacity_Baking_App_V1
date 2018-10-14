package com.example.nipunac.bakingapp_v1.network;

import com.example.nipunac.bakingapp_v1.model.Recipe;

import java.util.List;

import retrofit2.Response;

public interface OnRequestFinishedListener {
    void onFailure(String message);

    void onResponse(Response<List<Recipe>> response);
}
