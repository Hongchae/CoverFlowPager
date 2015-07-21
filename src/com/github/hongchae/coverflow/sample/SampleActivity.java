package com.github.hongchae.coverflow.sample;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.TextView;
import com.github.hongchae.coverflow.R;
import com.github.hongchae.coverflow.lib.CoverFlowContainer;

import java.util.Arrays;
import java.util.List;

public class SampleActivity extends Activity {

    private final List<SampleItem> sampleItems = Arrays.asList(
            new SampleItem(R.drawable.c1, "cover 1"),
            new SampleItem(R.drawable.c2, "cover 2"),
            new SampleItem(R.drawable.c3, "cover 3"),
            new SampleItem(R.drawable.c4, "cover 4"),
            new SampleItem(R.drawable.c5, "cover 5"),
            new SampleItem(R.drawable.c6, "cover 6"),
            new SampleItem(R.drawable.c7, "cover 7"),
            new SampleItem(R.drawable.c8, "cover 8"),
            new SampleItem(R.drawable.c9, "cover 9"),
            new SampleItem(R.drawable.c10, "cover 10"),
            new SampleItem(R.drawable.c11, "cover 11")
    );

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        CoverFlowContainer mContainer = (CoverFlowContainer) findViewById(R.id.pager_container);

        ViewPager pager = mContainer.getViewPager();

        PagerAdapter adapter = new SampleCoverFlowAdapter(SampleActivity.this, sampleItems);
        final TextView selectedTitle = (TextView) findViewById(R.id.selectedTitle);

        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(adapter.getCount());
        pager.setClipChildren(false);
        selectedTitle.setText(sampleItems.get(0).title);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                selectedTitle.setText(sampleItems.get(position).title);
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }


}