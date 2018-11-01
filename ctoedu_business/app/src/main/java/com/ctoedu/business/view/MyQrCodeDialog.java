package com.ctoedu.business.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ctoedu.business.R;
import com.ctoedu.business.manager.UserManager;
import com.ctoedu.business.util.Util;
import com.ctoedu.sdk.adutil.Utils;


public class MyQrCodeDialog extends Dialog {

    private Context mContext;

    /**
     * UI
     */
    private ImageView mQrCodeView;
    private TextView mTickView;
    private TextView mCloseView;

    public MyQrCodeDialog(Context context) {
        super(context, 0);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_mycode_layout);
        initView();
    }

    private void initView() {

        mQrCodeView = (ImageView) findViewById(R.id.qrcode_view);
        mTickView = (TextView) findViewById(R.id.tick_view);
        mCloseView = (TextView) findViewById(R.id.close_view);
        mCloseView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        String name = UserManager.getInstance().getUser().data.name;
        mQrCodeView.setImageBitmap(Util.createQRCode(
                Utils.dip2px(mContext, 200),
                Utils.dip2px(mContext, 200),
                name));
        mTickView.setText(name + mContext.getString(R.string.personal_info));
    }
}
