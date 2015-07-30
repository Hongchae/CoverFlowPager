# CoverFlowPager
Android CoverFlow widget implementaion using ViewPager.

## Screenshot
![Demo](https://cloud.githubusercontent.com/assets/6946586/8985287/72743fb4-3711-11e5-926a-23a0aeab1ee0.png)

## XML Layout
```xml
    ...
    <com.github.hongchae.coverflow.lib.CoverFlowContainer
            android:id="@+id/pager_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.github.hongchae.coverflow.lib.VelocityViewPager
                android:layout_width="120dp"
                android:layout_height="180dp"
                android:layout_gravity="center"
                CoverFlowPager:listSpacing="-35dp"
                CoverFlowPager:selectedSpacing="30dp"
                CoverFlowPager:rotateFactor="70.0"
                CoverFlowPager:rotateLimit="70.0"
                CoverFlowPager:scaleFactor="0.1"
                CoverFlowPager:alphaFactor="0.3"
                CoverFlowPager:gravityFactor="-5.0"
                CoverFlowPager:listRadius="2.0"
                />
    </com.github.hongchae.coverflow.lib.CoverFlowContainer>
    ...
```
