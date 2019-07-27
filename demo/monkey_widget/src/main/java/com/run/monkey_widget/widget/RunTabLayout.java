package com.run.monkey_widget.widget;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.run.monkey_widget.R;

import java.util.ArrayList;

public class RunTabLayout extends HorizontalScrollView implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;
    private FragmentPagerAdapter mVPAdapter;
    private Context mContext ;
    private int defaultTabWidth = 150;

    private int mCurrentTab;

    private LinearLayout mContent ;
    private String[] mTabTexts ;

    private int mUnderLineColor = 0xfffb7299;

    public RunTabLayout(Context context) {
        this(context,null,0);
    }

    public RunTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public RunTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setFillViewport(true);//设置滚动视图是否可以伸缩其内容以填充视口
        setWillNotDraw(false);//重写onDraw方法,需要调用这个方法来清除flag
        setClipChildren(false);
        setClipToPadding(false);

        this.mContext = context ;
        View v  = View.inflate(mContext,R.layout.tabs_root,null);
        mContent = v.findViewById(R.id.linearlayout);
        addView(v);
    }

    public void setViewPager(ViewPager vp){
        if (vp == null){
            throw new NullPointerException("ViewPager is not NULL");
        }

        this.mViewPager = vp;
        mCurrentTab = mViewPager.getCurrentItem();
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        notifyDataSetChanged();
    }


    public void setViewPager(ViewPager vp,String[] titles) {
        if (vp == null){
            throw new NullPointerException("ViewPager is not NULL");
        }

        if (vp.getAdapter() == null ){
            throw new NullPointerException("FragmentPagerAdapter is not NULL");
        }

        this.mViewPager = vp;
        mVPAdapter = (FragmentPagerAdapter) mViewPager.getAdapter();
        mCurrentTab = mViewPager.getCurrentItem();
        this.mViewPager.removeOnPageChangeListener(this);
        this.mViewPager.addOnPageChangeListener(this);
        if (titles != null || titles.length == mVPAdapter.getCount()){
            mTabTexts = titles;
        }else{
            mTabTexts = null;
            mTabTexts = new String[mVPAdapter.getCount()];
        }
        notifyDataSetChanged();
    }

    private void notifyDataSetChanged() {
        mContent.removeAllViews();

        for (int i = 0 ; i<mVPAdapter.getCount() ; i++){
            RelativeLayout tab = (RelativeLayout) inflate(mContext,R.layout.tab_item,null);
            //String pageTitle = mViewPager.getAdapter().getPageTitle(i).toString();
            String pageTitle = mTabTexts[i] == null ? mVPAdapter.getPageTitle(i).toString() : mTabTexts[i];
            addTabView(i, pageTitle, tab);
        }
    }

    private void addTabView(final int position, String tag , View view){
        /** 每一个Tab的布局参数 */
        LinearLayout.LayoutParams lp_tab =
                new LinearLayout.LayoutParams(defaultTabWidth, LayoutParams.MATCH_PARENT ) ;
        mContent.addView(view,position,lp_tab);
        TextView tv = mContent.getChildAt(position).findViewById(R.id.tv);
        tv.setText(tag);

        tv.setOnClickListener(new OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (mCurrentTab != position){
                    mContent.getChildAt(mCurrentTab).findViewById(R.id.hua)
                            .setBackground(mContent.getBackground());
                    mCurrentTab = position;
                    mViewPager.setCurrentItem(position);
                    mContent.getChildAt(mCurrentTab).findViewById(R.id.hua)
                            .setBackgroundColor(mUnderLineColor);
                }
            }
        });
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onPageSelected(int i) {
        if (mCurrentTab == i){
            return;
        }
        mContent.getChildAt(mCurrentTab).findViewById(R.id.hua)
                .setBackground(mContent.getBackground());
        mCurrentTab = i;
        mContent.getChildAt(mCurrentTab).findViewById(R.id.hua)
                .setBackgroundColor(mUnderLineColor);
    }

    public void setUnderLineColor(int color) {
        this.mUnderLineColor = color;
    }

    public int getUnderLineColor() {
        return mUnderLineColor;
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
