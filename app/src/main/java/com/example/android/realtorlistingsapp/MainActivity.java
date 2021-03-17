package com.example.android.realtorlistingsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.realtorlistingsapp.data.RealtorListing;
import com.example.android.realtorlistingsapp.data.LoadingStatus;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements RealtorSearchAdapter.OnSearchResultClickListener,
            SharedPreferences.OnSharedPreferenceChangeListener,
            NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView searchResultsRV;
    private EditText searchBoxET;
    private ProgressBar loadingIndicatorPB;
    private TextView errorMessageTV;
    private DrawerLayout drawerLayout;

    private RealtorSearchAdapter githubSearchAdapter;
    private RealtorSearchViewModel githubSearchViewModel;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.searchBoxET = findViewById(R.id.et_search_box);
        this.searchResultsRV = findViewById(R.id.rv_search_results);
        this.loadingIndicatorPB = findViewById(R.id.pb_loading_indicator);
        this.errorMessageTV = findViewById(R.id.tv_error_message);
        this.drawerLayout = findViewById(R.id.drawer_layout);

        this.searchResultsRV.setLayoutManager(new LinearLayoutManager(this));
        this.searchResultsRV.setHasFixedSize(true);

        this.githubSearchAdapter = new RealtorSearchAdapter(this);
        this.searchResultsRV.setAdapter(this.githubSearchAdapter);

        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        this.githubSearchViewModel = new ViewModelProvider(this).get(RealtorSearchViewModel.class);

        this.githubSearchViewModel.getSearchResults().observe(
                this,
                new Observer<List<RealtorListing>>() {
                    @Override
                    public void onChanged(List<RealtorListing> realtorListings) {
                        githubSearchAdapter.updateSearchResults(realtorListings);
                    }
                }
        );

        this.githubSearchViewModel.getLoadingStatus().observe(
                this,
                new Observer<LoadingStatus>() {
                    @Override
                    public void onChanged(LoadingStatus loadingStatus) {
                        if (loadingStatus == LoadingStatus.LOADING) {
                            loadingIndicatorPB.setVisibility(View.VISIBLE);
                        } else if (loadingStatus == LoadingStatus.SUCCESS) {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            searchResultsRV.setVisibility(View.VISIBLE);
                            errorMessageTV.setVisibility(View.INVISIBLE);
                        } else {
                            loadingIndicatorPB.setVisibility(View.INVISIBLE);
                            searchResultsRV.setVisibility(View.INVISIBLE);
                            errorMessageTV.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );

        NavigationView navigationView = findViewById(R.id.nv_nav_drawer);
        navigationView.setNavigationItemSelectedListener(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);

        Button searchButton = (Button)findViewById(R.id.btn_search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchQuery = searchBoxET.getText().toString();
                if (!TextUtils.isEmpty(searchQuery)) {
                    String sort = sharedPreferences.getString(
                            getString(R.string.pref_sort_key),
                            getString(R.string.pref_sort_default)
                    );
                    // Change to new prefs
//                    String language = sharedPreferences.getString(
//                            getString(R.string.pref_language_key),
//                            getString(R.string.pref_language_default)
//                    );
//                    String user = sharedPreferences.getString(
//                            getString(R.string.pref_user_key),
//                            ""
//                    );
//                    boolean inName = sharedPreferences.getBoolean(
//                            getString(R.string.pref_in_name_key),
//                            true
//                    );
//                    boolean inDescription = sharedPreferences.getBoolean(
//                            getString(R.string.pref_in_description_key),
//                            true
//                    );
//                    boolean inReadme = sharedPreferences.getBoolean(
//                            getString(R.string.pref_in_readme_key),
//                            false
//                    );
                    githubSearchViewModel.loadSearchResults(
                            searchQuery, "Portland", "OR", "relevance", "3", "2", "0", "10000000"
                    );
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        this.drawerLayout.closeDrawers();
        switch (item.getItemId()) {
            case R.id.nav_search:
                return true;
            case R.id.nav_bookmarked_repos:
                Intent bookmarkedReposIntent = new Intent(this, BookmarkedRepos.class);
                startActivity(bookmarkedReposIntent);
                return true;
            case R.id.nav_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (TextUtils.equals(key, getString(R.string.pref_in_name_key))
            || TextUtils.equals(key, getString(R.string.pref_in_description_key))
            || TextUtils.equals(key, getString(R.string.pref_in_readme_key))) {
            Log.d(TAG, "shared preference changed, key: " + key + ", value: " + sharedPreferences.getBoolean(key, false));
        } else {
            Log.d(TAG, "shared preference changed, key: " + key + ", value: " + sharedPreferences.getString(key, ""));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case android.R.id.home:
                this.drawerLayout.openDrawer(GravityCompat.START);

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSearchResultClicked(RealtorListing repo) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }
}