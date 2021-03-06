package com.example.android.realtorlistingsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.android.realtorlistingsapp.data.RealtorListing;

import org.w3c.dom.Text;

public class RepoDetailActivity extends AppCompatActivity {
    public static final String EXTRA_GITHUB_REPO = "GitHubRepo";

    private static final String TAG = RepoDetailActivity.class.getSimpleName();

    private Toast errorToast;

    private RealtorListing repo;

    private BookmarkedReposViewModel viewModel;
    private boolean isBookmarked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_detail);

        this.isBookmarked = false;
        this.viewModel = new ViewModelProvider(
                this,
                new ViewModelProvider.AndroidViewModelFactory(getApplication())
        ).get(BookmarkedReposViewModel.class);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_GITHUB_REPO)) {
            this.repo = (RealtorListing)intent.getSerializableExtra(EXTRA_GITHUB_REPO);
            Log.d(TAG, "Got repo with name: " + repo.id);

           // TextView repoNameTV = findViewById(R.id.tv_repo_name);
           // TextView repoStarsTV = findViewById(R.id.tv_repo_stars);
           // TextView repoDescriptionTV = findViewById(R.id.tv_repo_description);
            TextView repoAddressTV= findViewById(R.id.tv_address);
            TextView repoBathTV= findViewById(R.id.tv_bath);
            TextView repoRoomTV= findViewById(R.id.tv_room);
            TextView repoPriceTV= findViewById(R.id.tv_price);

           // ImageView repoPhotoTV= findViewById(R.id.tv_photo);
            if(repo.line == null){
                repo.line = repo.address.line;
            }
            repoAddressTV.setText(repo.line);
            repoBathTV.setText(String.valueOf(repo.baths));
            repoRoomTV.setText(String.valueOf(repo.beds));
            repoPriceTV.setText(String.valueOf(repo.price));

           // repoPhotoTV.setImageURI(Uri.parse(repo.img));
            ImageView repoPhotoTV = findViewById(R.id.tv_photo);
            Glide.with(this)
                    .load(repo.img)
                    .into(repoPhotoTV);
           // repoNameTV.setText(repo.id);
           // repoStarsTV.setText(String.valueOf(repo.beds));
          //  repoDescriptionTV.setText(repo.id);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.repo_detail, menu);

        this.viewModel.getBookmarkedRepoByName(this.repo.id).observe(
                this,
                new Observer<RealtorListing>() {
                    @Override
                    public void onChanged(RealtorListing repo) {
                        MenuItem menuItem = menu.findItem(R.id.action_bookmark);
                        if (repo == null) {
                            isBookmarked = false;
                            menuItem.setIcon(R.drawable.ic_heart_outline_foreground);
                        } else {
                            isBookmarked = true;
                            menuItem.setIcon(R.drawable.ic_heart_foreground);
                        }
                    }
                }
        );

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_view_on_web:
                viewRepoOnWeb();
                return true;
            case R.id.action_share:
                shareRepo();
                return true;
            case R.id.action_bookmark:
                toggleRepoBookmark(item);
                return true;
            case R.id.action_email:
                email();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * This method toggles the "bookmarked" state of the repo.  For now, it's set up to just
     * toggle the action bar icon representing the bookmark action from "checked" to "unchecked",
     * but we'll eventually set it up to save the bookmarked repo in an SQLite database.
     */
    private void toggleRepoBookmark(MenuItem menuItem) {
        if (this.repo != null) {
            this.isBookmarked = !this.isBookmarked;
            menuItem.setChecked(this.isBookmarked);
            if (this.isBookmarked) {
                menuItem.setIcon(R.drawable.ic_heart_foreground);
                this.viewModel.insertBookmarkedRepo(this.repo);
            } else {
                menuItem.setIcon(R.drawable.ic_heart_outline_foreground);
                this.viewModel.deleteBookmarkedRepo(this.repo);
            }
        }
    }

    private void viewRepoOnWeb() {
        if (this.repo != null) {
            Uri githubRepoUri = Uri.parse(this.repo.url);
            Intent intent = new Intent(Intent.ACTION_VIEW, githubRepoUri);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException e) {
                if (this.errorToast != null) {
                    this.errorToast.cancel();
                }
                this.errorToast = Toast.makeText(this, "Error...", Toast.LENGTH_LONG);
                this.errorToast.show();
            }
        }
    }

    private void shareRepo() {
        if (this.repo != null) {
            String shareText = getString(
                    R.string.share_repo_text,
                    this.repo.id,
                    this.repo.url
            );
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.setType("text/plain");

            Intent chooserIntent = Intent.createChooser(intent, null);
            startActivity(chooserIntent);
        }
    }

    private void email() {

        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse(
                "mailto:"+
                        "?subject=" + Uri.encode("Information about " + repo.address.line) +
                        "&body=" + Uri.encode("Hi " + repo.agents.get(0).name + ", <br> <br> I'm interested in the property you have listed at " + repo.address.line + ". It's listed at $" + repo.price +". Could you please respond with additional information about the property? <br> <br> Thank you!")
        ));
        startActivity(emailIntent);
    }


}