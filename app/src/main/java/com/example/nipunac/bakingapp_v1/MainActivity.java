package com.example.nipunac.bakingapp_v1;


import android.content.Intent;
import android.os.Handler;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.nipunac.bakingapp_v1.adapters.MainScreenAdapter;
import com.example.nipunac.bakingapp_v1.idlingresource.SimpleIdlingResource;
import com.example.nipunac.bakingapp_v1.model.Recipe;
import com.example.nipunac.bakingapp_v1.network.NetworkUtils;
import com.example.nipunac.bakingapp_v1.network.OnRequestFinishedListener;
import com.example.nipunac.bakingapp_v1.widget.WidgetUpdateService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnRequestFinishedListener, MainScreenAdapter.RecepiItemOnClickHandler{

    private final static String TAG = "MainActivity";
    ArrayList<Recipe> mRecipes;

    private RecyclerView mRecyclerView;
    private MainScreenAdapter mMainScreenAdapter;

    @Nullable
    private SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource()
    {
        if (mIdlingResource == null)
        {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getIdlingResource();

        mRecyclerView = (RecyclerView) findViewById(R.id.RecipeRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);

        mMainScreenAdapter = new MainScreenAdapter(this);
        mRecyclerView.setAdapter(mMainScreenAdapter);

        NetworkUtils.getRecipiesFromURL(this);

        Log.d(TAG, ""+WidgetUpdateService.class);

        if(savedInstanceState == null){
            if (mIdlingResource != null) {
                mIdlingResource.setIdleState(false);
            }
        }
    }


    @Override
    public void onFailure(String message) {
        Toast.makeText(MainActivity.this, "No internet connection !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(Response<List<Recipe>> response) {
        mRecipes = (ArrayList<Recipe>) response.body();
        mMainScreenAdapter.setRecipes(mRecipes);


    }

    @Override
    public void onClick(Recipe selectedRecipe) {


        Intent RecipeDetailsActivityIntent = new Intent(MainActivity.this, RecipeDetailsActivity.class);
        Bundle bundle=new Bundle();
        bundle.putString("recipe_name",selectedRecipe.getName());
        bundle.putParcelableArrayList("ingredients", (ArrayList<? extends Parcelable>) selectedRecipe.getIngredients());
        bundle.putParcelableArrayList("steps", (ArrayList<? extends Parcelable>) selectedRecipe.getSteps());


        RecipeDetailsActivityIntent.putExtra("selected_recipe",bundle);

        startActivity(RecipeDetailsActivityIntent);

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);


    }

    @Override
    protected void onStart() {
        super.onStart();

        //This is the testing code

        if (mIdlingResource != null)
        {
            final Handler handler = new Handler();
            Runnable runnable = new Runnable()
            {
                @Override
                public void run()
                {
                    long futureTime = System.currentTimeMillis() + 5000;
                    while (System.currentTimeMillis() < futureTime)
                    {
                        synchronized (this)
                        {
                            try
                            {
                                wait(futureTime - System.currentTimeMillis());
                                if (mIdlingResource != null)
                                {
                                    mIdlingResource.setIdleState(true);
                                }

                            } catch (InterruptedException e)
                            {
                                e.printStackTrace();
                            }
                        }
                    }
                    handler.sendEmptyMessage(0);
                }
            };
            Thread testThread = new Thread(runnable);
            testThread.start();
        }
    }


}
