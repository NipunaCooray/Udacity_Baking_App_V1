package com.example.nipunac.bakingapp_v1.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.nipunac.bakingapp_v1.model.Ingredient;

import java.util.ArrayList;

public class WidgetUpdateService extends IntentService {

    public static final String WIDGET_UPDATE_ACTION = "com.example.nipunac.bakingapp_v1.widget.WidgetUpdateService";

    private ArrayList<Ingredient> mIngredients;



    public WidgetUpdateService()
    {
        super("WidgetServiceUpdate");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null && intent.getAction().equals(WIDGET_UPDATE_ACTION))
        {
            Bundle bundle = intent.getBundleExtra("bundle");

            if (bundle != null)
            {
                mIngredients = bundle.getParcelableArrayList("ingredients");

            }

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, RecipeListWidget.class));
            RecipeListWidget.updateAppWidget(this, appWidgetManager, appWidgetIds,mIngredients);
        }
    }
}
