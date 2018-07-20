package com.app.etow.ui.scheduled_trip;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.ScheduledTripAdapter;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseMVPDialogActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduledTripActivity extends BaseMVPDialogActivity implements ScheduledTripMVPView {

    @Inject
    ScheduledTripPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.img_filter)
    ImageView imgFilter;

    @BindView(R.id.rcv_scheduled_trip)
    RecyclerView rcvScheduledTrip;

    private ScheduledTripAdapter scheduledTripAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.scheduled_trips));
        imgFilter.setVisibility(View.VISIBLE);
        imgFilter.setImageResource(R.drawable.ic_refresh_red);

        scheduledTripAdapter = new ScheduledTripAdapter(this);
        scheduledTripAdapter.injectInto(rcvScheduledTrip);

        presenter.getListScheduledTrip();
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_scheduled_trip;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (scheduledTripAdapter != null) {
            scheduledTripAdapter.release();
        }
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(this, code);
    }


    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.img_filter)
    public void onClickFilter() {

    }

    @Override
    public void loadScheduledTrips(List<Trip> listScheduledTrip) {
        listScheduledTrip.add(new Trip(false));
        listScheduledTrip.add(new Trip(true));
        listScheduledTrip.add(new Trip(false));
        listScheduledTrip.add(new Trip(true));

        scheduledTripAdapter.setListData(listScheduledTrip);
    }
}
