package com.app.etow.ui.feedback;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.widget.EditText;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedbackActivity extends BaseMVPDialogActivity implements FeedbackMVPView {

    @Inject
    FeedbackPresenter presenter;

    @BindView(R.id.tv_message)
    TextView tvMessage;

    @BindView(R.id.edt_comment)
    EditText edtComment;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.get_in_touch));

        String textMessage = "<font color=#9E9E9D>" + getString(R.string.get_in_touch_with_us_on)
                + "</font> <b><font color=#121315>"
                + getString(R.string.feedback_email_us) + "</font></b>";
        tvMessage.setText(Html.fromHtml(textMessage));
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_feedback;
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
        GlobalFuntion.hideSoftKeyboard(this);
    }

    @OnClick(R.id.tv_send)
    public void onClickSendFeedback() {
        if (StringUtil.isEmpty(edtComment.getText().toString().trim())) {
            showAlert(getString(R.string.please_enter_comment));
        } else {
            presenter.sendFeedback(edtComment.getText().toString().trim());
        }
    }

    @OnClick(R.id.tv_message)
    public void onClickSendEmail() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", getString(R.string.feedback_email_us), null));
        startActivity(Intent.createChooser(emailIntent, "Send Email"));
    }

    @Override
    public void getStatusFeedback() {
        showAlert(getString(R.string.msg_send_feedback_successfully));
        edtComment.setText("");
    }
}
