package net.innlab.hlv.ui;

import net.innlab.hlv.util.Util;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HotLabelView extends LinearLayout {

	private Context mContext;
	// 当前显示每行item总宽度，计算宽度的游标
	private int currentWidth = 0;
	// 推荐每行父布局
	private LinearLayout itemLayout;
	// 推荐每行的父布局宽度
	private int layoutWidth;
	// 每行父布局的布局参数
	private LinearLayout.LayoutParams paramsLin;
	// 默认是单选
	private MODE mCurrentMode = MODE.SINGLE;
	private boolean[] mChildPosArry = null;
	private TextView[] mChildViews = null;
	private OnChildViewSelectedListener mListener = null;

	public HotLabelView(Context context) {
		this(context, null);
	}

	public HotLabelView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public HotLabelView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.mContext = context;
		init();
	}

	public enum MODE {
		SINGLE, MULTIPLE
	}

	private void init() {
		// 默认是垂直方向
		Util.init(mContext);
		setOrientation(LinearLayout.VERTICAL);
		currentWidth = 0;
		itemLayout = new LinearLayout(mContext);
		// 每行高30dp
		paramsLin = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				Util.dpToPx(30));
		paramsLin.topMargin = Util.dpToPx(10);
		itemLayout.setLayoutParams(paramsLin);
		addView(itemLayout);
		// 获取屏幕宽度，减去padding 24dp
		// 推荐item父布局宽度
		layoutWidth = Util.screenWidth - Util.dpToPx(24);

	}

	/**
	 * 对外接口，提供单选和复选的设置
	 * 
	 * @param mode
	 */
	public void setChoiceMode(MODE mode) {
		mCurrentMode = mode;
	}

	/**
	 * 对外接口，设置子View点击事件的监听
	 * 
	 * @param listener
	 */
	public void setItemSelectedListenrt(OnChildViewSelectedListener listener) {
		this.mListener = listener;
	}

	/**
	 * 设置内容子Views
	 * 
	 * @param views
	 */
	public void setContainerChildViews(TextView[] views) {
		mChildPosArry = new boolean[views.length];
		mChildViews = new TextView[views.length];
		addViews(views);
	}

	private int normResId = -1, selectedResId = -1;
	private String normColor, selectedColor;

	public void setChildViewStyle(int normResId, int selcetedResId,
			String normColor, String selectedColor) {
		this.normResId = normResId;
		this.selectedResId = selcetedResId;
		this.normColor = normColor;
		this.selectedColor = selectedColor;
	}

	private void setChildViewStyle(TextView view, boolean isChecked) {
		if (normResId != -1 && selectedResId != -1) {
			if (isChecked) {
				view.setTextColor(Color.parseColor(selectedColor));
				view.setBackgroundResource(selectedResId);
			} else {
				view.setTextColor(Color.parseColor(normColor));
				view.setBackgroundResource(normResId);
			}
		}
	}

	private TextView[] getSelectedTextViews() {
		TextView[] tmpViews = null;
		if (mCurrentMode == MODE.SINGLE) {
			for (int i = 0; i < mChildPosArry.length; i++) {
				if (mChildPosArry[i]) {
					return new TextView[] { mChildViews[i] };
				}
			}
			return null;
		} else {
			int mCount = 0;
			for (boolean booVal : mChildPosArry) {
				if (booVal) {
					mCount++;
				}
			}
			if (mCount == 0) {
				return null;
			} else {
				tmpViews = new TextView[mCount];
				int index = 0;
				for (int i = 0; i < mChildPosArry.length && mCount != 0; i++) {
					if (mChildPosArry[i]) {
						tmpViews[index++] = mChildViews[i];
					}
				}
				return tmpViews;
			}

		}
	}

	private int mLastSelectedPos = -1;
	private OnClickListener mClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			int pos = Integer.parseInt(v.getTag().toString());
			if (mCurrentMode == MODE.SINGLE) {
				if (mLastSelectedPos != pos) {
					mChildPosArry[pos] = true;
					setChildViewStyle((TextView) v, true);
					if (mLastSelectedPos != -1) {
						mChildPosArry[mLastSelectedPos] = false;
						setChildViewStyle(
								(TextView) mChildViews[mLastSelectedPos], false);
					}
				} else {
					if (mChildPosArry[pos]) {
						mChildPosArry[mLastSelectedPos] = false;
						setChildViewStyle((TextView) v, false);
					} else {
						mChildPosArry[mLastSelectedPos] = true;
						setChildViewStyle((TextView) v, true);
					}
				}
				mLastSelectedPos = pos;
			} else {
				if (mChildPosArry[pos]) {
					setChildViewStyle((TextView) v, false);
					mChildPosArry[pos] = false;
				} else {
					setChildViewStyle((TextView) v, true);
					mChildPosArry[pos] = true;
				}
			}

			if (mListener != null)
				mListener.onChildViewSelected(getSelectedTextViews(),
						(TextView) v);
		}
	};

	private void addChildView(View child) {
		paramsLin = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.MATCH_PARENT);
		// 设置子View的左右外间距
		paramsLin.setMargins(Util.dpToPx(5), 0, Util.dpToPx(5), 0);
		child.setLayoutParams(paramsLin);

		int w = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		int h = View.MeasureSpec.makeMeasureSpec(0,
				View.MeasureSpec.UNSPECIFIED);
		child.measure(w, h);
		int width = child.getMeasuredWidth();
		// 记得加上外边距的距离
		currentWidth += (width + Util.dpToPx(10));
		// 一行放不下时，重新开启新的一行
		if (currentWidth > layoutWidth) {
			// 添加子View
			itemLayout = new LinearLayout(mContext);
			paramsLin = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, Util.dpToPx(30));
			// 上边距
			paramsLin.topMargin = Util.dpToPx(10);
			itemLayout.setLayoutParams(paramsLin);
			itemLayout.setOrientation(LinearLayout.HORIZONTAL);
			this.addView(itemLayout);
			currentWidth = width;
		}
		itemLayout.addView(child);
	}

	private int currentPos = 0;

	private void addViews(TextView[] views) {
		for (TextView child : views) {
			addChildView(child);
			mChildViews[currentPos] = child;
			// tag记录子View位置
			child.setTag(String.valueOf(currentPos++));
			child.setOnClickListener(mClickListener);
		}
		currentPos = 0;
	}

	/**
	 * 外部实现接口，子View被点击时将已选择的子View回调
	 */
	public interface OnChildViewSelectedListener {
		void onChildViewSelected(TextView[] selectedChildViews,
				TextView curTextView);
	}
}