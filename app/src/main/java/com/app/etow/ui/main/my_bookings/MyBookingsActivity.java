package com.app.etow.ui.main.my_bookings;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.TripCompletedAdapter;
import com.app.etow.adapter.TripUpcomingAdapter;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.ui.base.BaseMVPDialogActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyBookingsActivity extends BaseMVPDialogActivity implements MyBookingsMVPView {

    @Inject
    MyBookingsPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.img_filter)
    ImageView imgFilter;

    @BindView(R.id.tv_completed)
    TextView tvCompleted;

    @BindView(R.id.tv_upcoming)
    TextView tvUpcoming;

    @BindView(R.id.rcv_completed)
    RecyclerView rcvCompleted;

    @BindView(R.id.rcv_upcoming)
    RecyclerView rcvUpcoming;

    @BindView(R.id.img_back)
    ImageView imgBack;

    private boolean mIsTabCompleted = true;
    private String mFilterType = Constant.FILTER_NONE;

    private TripCompletedAdapter tripCompletedAdapter;
    private TripUpcomingAdapter tripUpcomingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.my_bookings));
        if (!DataStoreManager.getPrefLanguage()) {
            imgBack.setImageResource(R.drawable.ic_back_black);
        } else {
            imgBack.setImageResource(R.drawable.ic_back_black_right);
        }
        imgFilter.setVisibility(View.VISIBLE);

        tripCompletedAdapter = new TripCompletedAdapter(this, presenter.getListTripCompleted());
        tripCompletedAdapter.injectInto(rcvCompleted);

        tripUpcomingAdapter = new TripUpcomingAdapter(this, presenter.getListTripUpcoming());
        tripUpcomingAdapter.injectInto(rcvUpcoming);

        presenter.getTripCompleted(this, tripCompletedAdapter, Constant.FILTER_NONE);
        presenter.getTripUpcoming(this, tripUpcomingAdapter, Constant.FILTER_NONE);
    }

    @Override
    protected boolean bindView() {
        return false;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_my_bookings;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();

        if (tripCompletedAdapter != null) {
            tripCompletedAdapter.release();
        }

        if (tripUpcomingAdapter != null) {
            tripUpcomingAdapter.release();
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
        showDialogFilter();
    }

    @OnClick(R.id.tv_completed)
    public void onClickSelectTabCompleted() {
        if (!mIsTabCompleted) {
            mIsTabCompleted = true;
            tvCompleted.setBackgroundResource(R.color.black);
            tvCompleted.setTextColor(getResources().getColor(R.color.white));
            tvUpcoming.setBackgroundResource(R.color.button_grey);
            tvUpcoming.setTextColor(getResources().getColor(R.color.textColorSecondary));
            rcvCompleted.setVisibility(View.VISIBLE);
            rcvUpcoming.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.tv_upcoming)
    public void onClickSelectTabUpcoming() {
        if (mIsTabCompleted) {
            mIsTabCompleted = false;
            tvUpcoming.setBackgroundResource(R.color.black);
            tvUpcoming.setTextColor(getResources().getColor(R.color.white));
            tvCompleted.setBackgroundResource(R.color.button_grey);
            tvCompleted.setTextColor(getResources().getColor(R.color.textColorSecondary));
            rcvCompleted.setVisibility(View.GONE);
            rcvUpcoming.setVisibility(View.VISIBLE);
        }
    }

    public void showDialogFilter() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_filter);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);

        // Get view
        final RadioGroup rdgOption = dialog.findViewById(R.id.rdg_option);
        final RadioButton rdbCash = dialog.findViewById(R.id.rdb_cash);
        final RadioButton rdbCard = dialog.findViewById(R.id.rdb_card);
        final TextView tvClear = dialog.findViewById(R.id.tv_clear);
        final TextView tvDone = dialog.findViewById(R.id.tv_done);

        if (Constant.FILTER_NONE.equals(mFilterType)) {
            rdgOption.clearCheck();
        } else if (Constant.FILTER_CASH.equals(mFilterType)) {
            rdbCash.setChecked(true);
            rdbCard.setChecked(false);
        } else {
            rdbCash.setChecked(false);
            rdbCard.setChecked(true);
        }
        rdgOption.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == -1) {
                    mFilterType = Constant.FILTER_NONE;
                    tvClear.setTextColor(getResources().getColor(R.color.textColorSecondary));
                } else {
                    if (checkedId == R.id.rdb_cash) {
                        mFilterType = Constant.FILTER_CASH;
                    } else {
                        mFilterType = Constant.FILTER_CARD;
                    }
                    tvClear.setTextColor(getResources().getColor(R.color.textColorPrimary));
                }
            }
        });

        tvDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                presenter.listTripCompleted.clear();
                presenter.listTripUpcoming.clear();
                if (rdgOption.getCheckedRadioButtonId() == -1) {
                    presenter.getTripCompleted(MyBookingsActivity.this, tripCompletedAdapter, Constant.FILTER_NONE);
                    presenter.getTripUpcoming(MyBookingsActivity.this, tripUpcomingAdapter, Constant.FILTER_NONE);
                } else if (rdgOption.getCheckedRadioButtonId() == R.id.rdb_cash) {
                    presenter.getTripCompleted(MyBookingsActivity.this, tripCompletedAdapter, Constant.FILTER_CASH);
                    presenter.getTripUpcoming(MyBookingsActivity.this, tripUpcomingAdapter, Constant.FILTER_CASH);
                } else if (rdgOption.getCheckedRadioButtonId() == R.id.rdb_card) {
                    presenter.getTripCompleted(MyBookingsActivity.this, tripCompletedAdapter, Constant.FILTER_CARD);
                    presenter.getTripUpcoming(MyBookingsActivity.this, tripUpcomingAdapter, Constant.FILTER_CARD);
                }
            }
        });

        tvClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rdgOption.clearCheck();
            }
        });

        dialog.show();
    }
}
