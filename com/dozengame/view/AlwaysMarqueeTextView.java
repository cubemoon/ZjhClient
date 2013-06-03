package com.dozengame.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
/**
 * ʵ�����ǹ������ı���ͼ
 * @author hewengao
 *
 */
public class AlwaysMarqueeTextView extends TextView {
	public AlwaysMarqueeTextView(Context context) {
		super(context);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
	}

	public boolean isFocused() {
		return true;
	}

}
