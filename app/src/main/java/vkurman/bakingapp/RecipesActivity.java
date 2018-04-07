package vkurman.bakingapp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import vkurman.bakingapp.adapters.RecipesAdapter;
import vkurman.bakingapp.loaders.RecipesLoader;
import vkurman.bakingapp.models.Recipe;

public class RecipesActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Recipe>> {

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

        // Setting loaders
        getSupportLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @NonNull
    @Override
    public Loader<List<Recipe>> onCreateLoader(int id, @Nullable Bundle args) {
        return new RecipesLoader(this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<Recipe>> loader, List<Recipe> data) {
        if(data == null) {
            return;
        }

        if(mAdapter == null) {
            mAdapter = new RecipesAdapter(data);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.updateData(data);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<Recipe>> loader) {}
}
