#AndroidHotLabelView
Sometimes , we need to show lots of hot tags in our app, like popular comments in some shopping apps or somebody's  personalized lables in some social apps,so I just have created the hot label view.

#ScreenShots
<img src="https://github.com/hjw541988478/AndroidHotLabelView/blob/master/screenshots/screen_shot.png" >

#DemoApk
[Download](https://github.com/hjw541988478/AndroidHotLabelView/blob/master/HotLabelView.apk)

#Features
- support two modes (**single** choice mode and **multiple** choice mode)
- provide the interface that you would like to implement in need,and you can use the selceted data array.
- customize the style by the method `setChildViewStyle`.

#Usage

1.make the `AndroidHotLabelView` avaliable in your project ;

2.puts the `net.innlab.ui.HotLabelView` in your layout xml file :
``` xml
<net.innlab.hlv.ui.HotLabelView
    android:id="@+id/hlv_single"
    android:layout_width="match_parent"
    android:layout_height="wrap_content">
</net.innlab.hlv.ui.HotLabelView>
```
3.initialize the `HotLabelView` by code :
```
HotLabelView hlv_single = (HotLabelView) findViewById(R.id.hlv_single);
```
4.set the choice mode , the seleceted style and populate the container childviews:
``` java
//make it single choice
hlv_single.setChoiceMode(MODE.SINGLE);
//make customlized style just like this
hlv_single.setChildViewStyle(R.drawable.drawable_item_normal,
		R.drawable.drawable_item_selected, "#a5333333", "#ffff0000");
//populate data just like this by array,anyway use the TextView
String[] titiles = { "好评", "差评", "中评", "实惠", "下次再来", "味道不错", "支持，好顶赞" };
views = new TextView[titiles.length];
for (int i = 0; i < titiles.length; i++) {
	TextView item_view = (TextView) LayoutInflater.from(this).inflate(
			R.layout.item_comment_label, hlv_single, false);
	item_view.setText(titiles[i]);
	views[i] = item_view;
}
hlv_single.setContainerChildViews(views);
```
5.implements the interface `OnChildViewSelectedListener`:
```
//like this
hlv_single.setItemSelectedListenrt(new OnChildViewSelectedListener() {
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
	};);
```
Pull Requests
===
I will gladly accept pull requests for fixes and feature enhancements but please do them in the develop branch.

License
===
CampusAssustant is licensed under [Apache License](https://github.com/hjw541988478/AndroidHotLabelView/blob/master/LICENSE).
