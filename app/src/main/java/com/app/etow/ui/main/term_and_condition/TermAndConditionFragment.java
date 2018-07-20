package com.app.etow.ui.main.term_and_condition;

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
import com.app.etow.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class TermAndConditionFragment extends BaseMVPFragmentWithDialog implements TermAndConditionMVPView {

    @Inject
    TermAndConditionPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_terms_and_conditions, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this, view);
        presenter.initialView(this);
        ((MainActivity)getActivity()).showAndHiddenItemToolbar(getString(R.string.terms_and_conditions),false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroyView();
    }

    @Override
    protected void initToolbar() {}

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(getActivity(), code);
    }
}
