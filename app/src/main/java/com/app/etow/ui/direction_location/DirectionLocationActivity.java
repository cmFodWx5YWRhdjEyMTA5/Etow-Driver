package com.app.etow.ui.direction_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Bundle;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.trip_summary.cash.TripSummaryCashActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DirectionLocationActivity extends BaseMVPDialogActivity implements DirectionLocationMVPView {

    @Inject
    DirectionLocationPresenter presenter;

    @BindView(R.id.tv_type_location)
    TextView tvTypeLocation;

    @BindView(R.id.tv_action)
    TextView tvAction;

    private int mTypeLocation;
    private Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        getDataIntent();
        initData();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTypeLocation = bundle.getInt(Constant.TYPE_LOCATION);
            mTrip = (Trip) bundle.get(Constant.OBJECT_TRIP);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_direction_location;
    }

    private void initData() {
        if (Constant.TYPE_PICK_UP == mTypeLocation) {
            tvTypeLocation.setText(getString(R.string.pick_up_location_2));
            tvAction.setText(getString(R.string.arrived_for_pick_up));
        } else {
            tvTypeLocation.setText(getString(R.string.drop_off_location_2));
            tvAction.setText(getString(R.string.journey_completed));
        }
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
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

    @OnClick(R.id.tv_action)
    public void onClickAction() {
        if (Constant.TYPE_PICK_UP == mTypeLocation) {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_ARRIVED);
        } else {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_JOURNEY_COMPLETED);
        }
    }

    @Override
    public void updateStatusTrip() {
        if (Constant.TYPE_PICK_UP == mTypeLocation) {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_ARRIVED);
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_DROP_OFF, mTrip);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
        } else {
            GlobalFuntion.startActivity(this, TripSummaryCashActivity.class);
        }
        finish();
    }
}
