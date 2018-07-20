package com.app.etow.ui.main.get_in_touch;

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

import com.app.etow.R;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPFragmentWithDialog;
import com.app.etow.ui.feedback.FeedbackActivity;
import com.app.etow.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class GetInTouchFragment extends BaseMVPFragmentWithDialog implements GetInTouchMVPView {

    @Inject
    GetInTouchPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_get_in_touch, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this, view);
        presenter.initialView(this);
        ((MainActivity) getActivity()).showAndHiddenItemToolbar(getString(R.string.get_in_touch),false);
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

    @OnClick(R.id.img_feedback)
    public void onClickImageFeedback() {
        GlobalFuntion.startActivity(getActivity(), FeedbackActivity.class);
    }
}
