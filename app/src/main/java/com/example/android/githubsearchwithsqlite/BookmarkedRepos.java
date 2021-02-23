package com.example.android.githubsearchwithsqlite;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.android.githubsearchwithsqlite.data.GitHubRepo;

public class BookmarkedRepos extends AppCompatActivity implements GitHubSearchAdapter.OnSearchResultClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bookmarked_repos);

        RecyclerView bookmarkedReposRV = findViewById(R.id.rv_bookmarked_repos);
        bookmarkedReposRV.setLayoutManager(new LinearLayoutManager(this));
        bookmarkedReposRV.setHasFixedSize(true);

        GitHubSearchAdapter adapter = new GitHubSearchAdapter(this);
        bookmarkedReposRV.setAdapter(adapter);
    }

    @Override
    public void onSearchResultClicked(GitHubRepo repo) {
        Intent intent = new Intent(this, RepoDetailActivity.class);
        intent.putExtra(RepoDetailActivity.EXTRA_GITHUB_REPO, repo);
        startActivity(intent);
    }
}