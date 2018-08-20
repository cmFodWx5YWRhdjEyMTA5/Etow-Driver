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
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.base.BaseRecyclerViewAdapter;
import com.app.etow.adapter.base.Releasable;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.injection.ActivityContext;
import com.app.etow.models.Trip;
import com.app.etow.ui.trip_upcoming_detail.TripUpcomingDetailActivity;
import com.app.etow.utils.DateTimeUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class TripUpcomingAdapter extends RecyclerView.Adapter<TripUpcomingAdapter.TripUpcomingViewHolder>
        implements Releasable {

    private Context context;
    private List<Trip> listTripUpcoming;
    private RecyclerView mRecyclerView;

    @Inject
    public TripUpcomingAdapter(@ActivityContext Context context, List<Trip> list) {
        this.context = context;
        this.listTripUpcoming = list;
    }

    @Override
    public TripUpcomingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TripUpcomingViewHolder viewHolder = TripUpcomingViewHolder.create(parent);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TripUpcomingViewHolder holder, int position) {
        holder.bindData(context, listTripUpcoming.get(position), position);
    }

    @Override
    public int getItemCount() {
        return null == listTripUpcoming ? 0 : listTripUpcoming.size();
    }

    public void setListData(List<Trip> list) {
        this.listTripUpcoming = list;
        notifyDataSetChanged();
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

    public static class TripUpcomingViewHolder extends BaseRecyclerViewAdapter.BaseViewHolder<Trip> {

        @BindView(R.id.tv_date)
        TextView tvDate;

        @BindView(R.id.tv_time)
        TextView tvTime;

        @BindView(R.id.tv_trip_no)
        TextView tvTripNo;

        @BindView(R.id.tv_trip_amount)
        TextView tvTripAmount;

        @BindView(R.id.tv_pick_up)
        TextView tvPickUp;

        @BindView(R.id.tv_drop_off)
        TextView tvDropOff;

        @BindView(R.id.tv_view_details)
        TextView tvViewDetails;

        public TripUpcomingViewHolder(View itemView) {
            super(itemView);
        }

        public static TripUpcomingViewHolder create(ViewGroup parent) {
            return new TripUpcomingViewHolder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_trip_upcoming, parent, false));
        }

        @Override
        public void bindData(Context context, Trip trip, int position) {
            if (trip != null) {
                tvDate.setText(DateTimeUtils.convertTimeStampToFormatDate2(trip.getPickup_date()));
                tvTime.setText(DateTimeUtils.convertTimeStampToFormatDate3(trip.getPickup_date()));
                tvTripNo.setText(trip.getId() + "");
                tvTripAmount.setText(trip.getPrice() + " " + context.getString(R.string.unit_price));
                tvPickUp.setText(trip.getPick_up());
                tvDropOff.setText(trip.getDrop_off());

                tvViewDetails.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bundle bundle = new Bundle();
                        bundle.putSerializable(Constant.OBJECT_TRIP, trip);
                        GlobalFuntion.startActivity(context, TripUpcomingDetailActivity.class, bundle);
                    }
                });
            }
        }
    }
}