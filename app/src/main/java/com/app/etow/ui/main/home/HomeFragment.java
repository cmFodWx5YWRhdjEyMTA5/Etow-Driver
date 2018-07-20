package com.app.etow.ui.main.home;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPFragmentWithDialog;
import com.app.etow.ui.incoming_request.IncomingRequestActivity;
import com.app.etow.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseMVPFragmentWithDialog implements HomeMVPView {

    @Inject
    HomePresenter presenter;

    @BindView(R.id.tv_online)
    TextView tvOnline;

    @BindView(R.id.tv_offline)
    TextView tvOffline;

    private boolean mIsOnline = true;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this, view);
        presenter.initialView(this);
        ((MainActivity) getActivity()).showAndHiddenItemToolbar("", true);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroyView();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(getActivity(), code);
    }

    @OnClick(R.id.layout_main)
    public void onClickLayoutMain() {
        GlobalFuntion.startActivity(getActivity(), IncomingRequestActivity.class);
    }

    @OnClick(R.id.tv_online)
    public void onClickOnline() {
        if (!mIsOnline) {
            mIsOnline = true;
            tvOnline.setBackgroundResource(R.drawable.bg_black_corner_left_bottom);
            tvOffline.setBackgroundResource(R.drawable.bg_grey_corner_right_bottom);
            tvOnline.setTextColor(getResources().getColor(R.color.white));
            tvOffline.setTextColor(getResources().getColor(R.color.textColorSecondary));
        }
    }

    @OnClick(R.id.tv_offline)
    public void onClickOffline() {
        if (mIsOnline) {
            mIsOnline = false;
            tvOnline.setBackgroundResource(R.drawable.bg_grey_corner_left_bottom);
            tvOffline.setBackgroundResource(R.drawable.bg_red_corner_right_bottom);
            tvOnline.setTextColor(getResources().getColor(R.color.textColorSecondary));
            tvOffline.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
