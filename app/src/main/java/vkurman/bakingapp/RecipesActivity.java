package vkurman.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.adapters.RecipesAdapter;
import vkurman.bakingapp.models.Recipe;

public class RecipesActivity extends AppCompatActivity {

    @BindView(R.id.rv_recipes) RecyclerView mRecyclerView;

    private RecipesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);

        // Binding views
        ButterKnife.bind(this);

        // use a grid layout manager
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecipesAdapter(new ArrayList<Recipe>());
        mRecyclerView.setAdapter(mAdapter);
    }
}
