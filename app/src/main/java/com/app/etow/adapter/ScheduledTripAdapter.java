package com.app.etow.adapter;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/28
 */

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.base.BaseRecyclerViewAdapter;
import com.app.etow.adapter.base.Releasable;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.injection.ActivityContext;
import com.app.etow.models.Trip;
import com.app.etow.ui.scheduled_trip.detail.ScheduledTripDetailActivity;
import com.app.etow.utils.DateTimeUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class ScheduledTripAdapter extends RecyclerView.Adapter<ScheduledTripAdapter.ScheduledTripViewHolder>
        implements Releasable {

    private Context context;
    private List<Trip> listScheduledTrip;
    private RecyclerView mRecyclerView;

    @Inject
    public ScheduledTripAdapter(@ActivityContext Context context, List<Trip> list) {
        this.context = context;
        this.listScheduledTrip = list;
    }

    @Override
    public ScheduledTripViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ScheduledTripViewHolder viewHolder = ScheduledTripViewHolder.create(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScheduledTripViewHolder holder, int position) {
        holder.bindData(context, listScheduledTrip.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listScheduledTrip ? 0 : listScheduledTrip.size();
    }

    public void injectInto(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setFocusable(false);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(this);
    }

    @Override
    public void release() {
        context = null;
    }

    public static class ScheduledTripViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Trip> {

        @BindView(R.id.layout_title)
        RelativeLayout layoutTitle;

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_title_pick_up)
        TextView tvTitlePickUp;

        @BindView(R.id.tv_pick_up)
        TextView tvPickUp;

        @BindView(R.id.tv_title_drop_off)
        TextView tvTitleDropOff;

        @BindView(R.id.tv_drop_off)
        TextView tvDropOff;

        @BindView(R.id.tv_view_details)
        TextView tvViewDetails;

        @BindView(R.id.tv_status)
        TextView tvStatus;

        public ScheduledTripViewHolder(View itemView) {
            super(itemView);
        }

        public static ScheduledTripViewHolder create(ViewGroup parent) {
            return new ScheduledTripViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_scheduled_trip, parent, false));
        }

        @Override
        public void bindData(Context context, Trip trip, int position) {
            if (trip != null) {
                if (Constant.TRIP_STATUS_NEW == trip.getStatus()) {
                    layoutTitle.setBackgroundResource(R.color.black);
                    tvTitlePickUp.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                    tvPickUp.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    tvTitleDropOff.setTextColor(context.getResources().getColor(R.color.textColorSecondary));
                    tvDropOff.setTextColor(context.getResources().getColor(R.color.textColorPrimary));
                    tvViewDetails.setBackgroundResource(R.drawable.bg_black_corner_radius_6);
                    tvViewDetails.setTextColor(context.getResources().getColor(R.color.white));
                    tvStatus.setBackgroundResource(R.drawable.bg_black_corner_radius_6);
                    tvStatus.setTextColor(context.getResources().getColor(R.color.white));
                    tvStatus.setText(context.getString(R.string.open));
                    tvViewDetails.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable(Constant.OBJECT_TRIP, trip);
                            GlobalFuntion.startActivity(context, ScheduledTripDetailActivity.class, bundle);
                        }
                    });
                } else {
                    layoutTitle.setBackgroundResource(R.color.background_trip_assigned);
                    tvTitlePickUp.setTextColor(context.getResources().getColor(R.color.text_trip_assigned));
                    tvPickUp.setTextColor(context.getResources().getColor(R.color.text_trip_assigned));
                    tvTitleDropOff.setTextColor(context.getResources().getColor(R.color.text_trip_assigned));
                    tvDropOff.setTextColor(context.getResources().getColor(R.color.text_trip_assigned));
                    tvViewDetails.setBackgroundResource(R.drawable.bg_grey_corner_radius_6);
                    tvViewDetails.setTextColor(context.getResources().getColor(R.color.background_trip_assigned));
                    tvStatus.setBackgroundResource(R.drawable.bg_grey_corner_radius_6);
                    tvStatus.setTextColor(context.getResources().getColor(R.color.background_trip_assigned));
                    tvStatus.setText(context.getString(R.string.assigned));
                }
                tvDate.setText(DateTimeUtils.convertTimeStampToFormatDate2(trip.getPickup_date()));
                tvTime.setText(DateTimeUtils.convertTimeStampToFormatDate3(trip.getPickup_date()));
                tvPickUp.setText(trip.getPick_up());
                tvDropOff.setText(trip.getDrop_off());
            }
        }
    }
}