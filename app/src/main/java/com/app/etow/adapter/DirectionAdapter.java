package com.app.etow.adapter;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/28
 */

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.base.BaseRecyclerViewAdapter;
import com.app.etow.adapter.base.Releasable;
import com.app.etow.direction.Step;
import com.app.etow.injection.ActivityContext;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class DirectionAdapter extends RecyclerView.Adapter<DirectionAdapter.TripUpcomingViewHolder>
        implements Releasable {

    private Context context;
    private List<Step> steps;
    private RecyclerView mRecyclerView;

    @Inject
    public DirectionAdapter(@ActivityContext Context context, List<Step> list) {
        this.context = context;
        this.steps = list;
    }

    @Override
    public TripUpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TripUpcomingViewHolder viewHolder = TripUpcomingViewHolder.create(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripUpcomingViewHolder holder, int position) {
        holder.bindData(context, steps.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == steps ? 0 : steps.size();
    }

    public void setListData(List<Step> list) {
        this.steps = list;
        notifyDataSetChanged();
    }

    public void injectInto(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(this);
    }

    @Override
    public void release() {
        context = null;
    }

    public static class TripUpcomingViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Step> {

        @BindView(R.id.tv_direction)
        TextView tvDirection;

        public TripUpcomingViewHolder(View itemView) {
            super(itemView);
        }

        public static TripUpcomingViewHolder create(ViewGroup parent) {
            return new TripUpcomingViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_direction, parent, false));
        }

        @Override
        public void bindData(Context context, Step step, int position) {
            if (step != null) {
                tvDirection.setText(step.distance.text + " " + step.maneuver);
            }
        }
    }
}