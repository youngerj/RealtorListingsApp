package com.example.android.realtorlistingsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.realtorlistingsapp.data.RealtorListing;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RealtorSearchAdapter extends RecyclerView.Adapter<RealtorSearchAdapter.SearchResultViewHolder> {
    private List<RealtorListing> searchResultsList;
    private OnSearchResultClickListener resultClickListener;

    interface OnSearchResultClickListener {
        void onSearchResultClicked(RealtorListing repo);
    }

    public RealtorSearchAdapter(OnSearchResultClickListener listener) {
        this.resultClickListener = listener;
    }

    public void updateSearchResults(List<RealtorListing> searchResultsList) {
        this.searchResultsList = searchResultsList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (this.searchResultsList != null) {
            return this.searchResultsList.size();
        } else {
            return 0;
        }
    }

    @NonNull
    @Override
    public SearchResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.search_result_item, parent, false);
        return new SearchResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchResultViewHolder holder, int position) {
        holder.bind(this.searchResultsList.get(position));
    }

    class SearchResultViewHolder extends RecyclerView.ViewHolder {
        private TextView searchResultTV;
        private TextView roomTV;
        private TextView bathTV;
        private TextView priceTV;

        SearchResultViewHolder(View itemView) {
            super(itemView);
            this.searchResultTV = itemView.findViewById(R.id.tv_search_result);
            this.roomTV = itemView.findViewById(R.id.tv_room);
            this.bathTV = itemView.findViewById(R.id.tv_bath);
            this.priceTV = itemView.findViewById(R.id.tv_price);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resultClickListener.onSearchResultClicked(
                            searchResultsList.get(getAdapterPosition())
                    );
                }
            });
        }

        void bind(RealtorListing repo) {
            if(repo.line == null){
                repo.line = repo.address.line;
            }
            this.searchResultTV.setText(repo.line);
            this.roomTV.setText("Beds: " + String.valueOf(repo.beds));
            this.bathTV.setText("Baths: " +String.valueOf(repo.baths));
            this.priceTV.setText("Price: $" + String.valueOf(repo.price));
        }
    }
}
