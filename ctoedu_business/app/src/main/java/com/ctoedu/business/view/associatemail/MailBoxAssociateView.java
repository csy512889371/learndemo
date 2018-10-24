package com.ctoedu.business.view.associatemail;

import android.content.Context;
import android.util.AttributeSet;

/**
 * 本在邮箱联想控件，输入@符后开始联想
 */
public class MailBoxAssociateView extends android.support.v7.widget.AppCompatMultiAutoCompleteTextView {
    public MailBoxAssociateView(Context context) {
        super(context);
    }

    public MailBoxAssociateView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MailBoxAssociateView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean enoughToFilter() {
        return getText().toString().contains("@") && getText().toString().indexOf("@") > 0;
    }
}