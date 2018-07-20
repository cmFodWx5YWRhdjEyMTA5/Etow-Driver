package com.app.etow.ui.view_map_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.direction_location.DirectionLocationActivity;
import com.app.etow.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewMapLocationActivity extends BaseMVPDialogActivity implements ViewMapLocationMVPView {

    @Inject
    ViewMapLocationPresenter presenter;

    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_type_location)
    TextView tvTypeLocation;

    @BindView(R.id.tv_time_to_location)
    TextView tvTimeToLocation;

    @BindView(R.id.tv_get_direction)
    TextView tvGetDirection;

    private String mTitleToobar;
    private boolean mIsShowDistance;
    private int mTypeLocation;

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
            mTitleToobar = bundle.getString(Constant.TITLE_TOOLBAR);
            mIsShowDistance = bundle.getBoolean(Constant.IS_SHOW_DISTANCE);
            mTypeLocation = bundle.getInt(Constant.TYPE_LOCATION);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_view_map_location;
    }

    private void initData() {
        if (!StringUtil.isEmpty(mTitleToobar)) {
            layoutHeader.setVisibility(View.VISIBLE);
            tvTitleToolbar.setText(mTitleToobar);
        } else {
            layoutHeader.setVisibility(View.GONE);
        }

        if (Constant.TYPE_PICK_UP == mTypeLocation) {
            tvTypeLocation.setText(getString(R.string.pick_up_location_2));
        } else {
            tvTypeLocation.setText(getString(R.string.drop_off_location_2));
        }

        if (mIsShowDistance) {
            tvTimeToLocation.setVisibility(View.VISIBLE);
            tvGetDirection.setVisibility(View.VISIBLE);

            String timeToLocation = "";
            if (Constant.TYPE_PICK_UP == mTypeLocation) {
                timeToLocation = "<font color=#9E9E9D>" + getString(R.string.estimated_time_pick_up_location)
                        + "</font> <b><font color=#121315>"
                        + 13 + "</font></b> <font color=#9E9E9D>"
                        + getString(R.string.min) + "</font>";
            } else {
                timeToLocation = "<font color=#9E9E9D>" + getString(R.string.estimated_time_drop_off_location)
                        + "</font> <b><font color=#121315>"
                        + 13 + "</font></b> <font color=#9E9E9D>"
                        + getString(R.string.min) + "</font>";
            }
            tvTimeToLocation.setText(Html.fromHtml(timeToLocation));
        } else {
            tvTimeToLocation.setVisibility(View.GONE);
            tvGetDirection.setVisibility(View.GONE);
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

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_get_direction)
    public void onClickGetDirection() {
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TYPE_LOCATION, mTypeLocation);
        GlobalFuntion.startActivity(this, DirectionLocationActivity.class, bundle);
    }
}
