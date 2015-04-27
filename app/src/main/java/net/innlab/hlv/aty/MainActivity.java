package net.innlab.hlv.aty;

import net.innlab.hlv.ui.HotLabelView;
import net.innlab.hlv.ui.HotLabelView.MODE;
import net.innlab.hlv.ui.HotLabelView.OnChildViewSelectedListener;
import net.innlab.hotlabelview.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.TextView;

public class MainActivity extends Activity {

	HotLabelView hlv_single, hlv_multi;
	TextView[] views = null;
	TextView[] multiViews = null;

	private void initSingleTextViews() {
		hlv_single.setChoiceMode(MODE.SINGLE);
		hlv_single.setChildViewStyle(R.drawable.drawable_item_normal,
				R.drawable.drawable_item_selected, "#a5333333", "#ffff0000");
		hlv_single.setItemSelectedListenrt(mChildViewSelectedListener);
		String[] titiles = { "好评", "差评", "中评", "实惠", "下次再来", "味道不错", "支持，好顶赞" };
		views = new TextView[titiles.length];
		for (int i = 0; i < titiles.length; i++) {
			TextView item_view = (TextView) LayoutInflater.from(this).inflate(
					R.layout.item_comment_label, hlv_single, false);
			item_view.setText(titiles[i]);
			views[i] = item_view;
		}
		hlv_single.setContainerChildViews(views);
	}

	private OnChildViewSelectedListener mChildViewSelectedListener = new OnChildViewSelectedListener() {

		@Override
		public void onChildViewSelected(TextView[] selectedChildViews,
				TextView curTextView) {
			if (selectedChildViews != null) {
				StringBuffer sb = new StringBuffer();
				for (TextView tmp : selectedChildViews) {
					sb.append(tmp.getTag().toString() + ":" + tmp.getText()
							+ ",");
				}
				Log.d("multi_mode", sb.toString());
			} else {
				Log.d("multi_mode", "未选择任何选项");
			}
		}
	};

	private void initMultiTextViews() {
		hlv_multi.setChoiceMode(MODE.MULTIPLE);
		hlv_multi.setChildViewStyle(R.drawable.drawable_item_normal,
				R.drawable.drawable_item_selected, "#a5333333", "#ffff0000");
		hlv_multi.setItemSelectedListenrt(mChildViewSelectedListener);
		String[] titiles = { "好评", "差评", "中评", "实惠", "下次再来", "味道不错", "支持，好顶赞" };
		views = new TextView[titiles.length];
		for (int i = 0; i < titiles.length; i++) {
			TextView item_view = (TextView) LayoutInflater.from(this).inflate(
					R.layout.item_comment_label, hlv_single, false);
			item_view.setText(titiles[i]);
			views[i] = item_view;
		}
		hlv_multi.setContainerChildViews(views);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		hlv_single = (HotLabelView) findViewById(R.id.hlv_single);
		hlv_multi = (HotLabelView) findViewById(R.id.hlv_multi);
		initSingleTextViews();
		initMultiTextViews();
	}

}
