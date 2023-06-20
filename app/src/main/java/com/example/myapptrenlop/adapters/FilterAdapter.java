package com.example.myapptrenlop.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapptrenlop.R;
import com.example.myapptrenlop.models.Filter;

import java.util.List;

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.FilterViewHolder> {

    private List<Filter> lFilters;

    public FilterAdapter(List<Filter> lFilters) {
        this.lFilters = lFilters;
        Log.i("init filter adater", "success");
    }

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View filterView = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_filter, parent, false);
        FilterViewHolder filterViewHolder = new FilterViewHolder(filterView);
        return filterViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int position) {
        String sFilter = lFilters.get(position).getFilterName();
        Boolean isSelected = lFilters.get(position).isSelected();
        filterViewHolder.tvFilterName.setText(sFilter);
        OnClickListenerFilter onClickListenerFilter  = new OnClickListenerFilter(lFilters, position);
        filterViewHolder.itemView.setOnClickListener(onClickListenerFilter);
        filterViewHolder.itemView.setSelected(isSelected);
    }

    @Override
    public int getItemCount() {
        return this.lFilters.size();
    }

    public class FilterViewHolder extends RecyclerView.ViewHolder {
        TextView tvFilterName;
        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFilterName = (TextView) itemView.findViewById(R.id.tvFilterName);

        }
    }

    public class OnClickListenerFilter implements View.OnClickListener {
        List<Filter> lFilters;

        int position;

        public OnClickListenerFilter(List<Filter> lFilters, int position) {
            this.lFilters = lFilters;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            for (Filter filter : lFilters
                 ) {
                filter.setSelected(false);
            }
            lFilters.get(position).setSelected(true);
            notifyDataSetChanged();
        }
    }


}
