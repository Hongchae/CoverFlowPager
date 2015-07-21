# CoverFlowPager
Android CoverFlow widget implementaion using ViewPager.

## Screenshot
![Demo](https://cloud.githubusercontent.com/assets/6946586/8802009/79f2be2e-2ff9-11e5-9756-536b26b241e1.png)

## XML Layout
```xml
    ...
    <com.github.hongchae.coverflow.lib.CoverFlowContainer
            android:id="@+id/pager_container"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
        <com.github.hongchae.coverflow.lib.VelocityViewPager
                android:layout_width="150dp"
                android:layout_height="225dp"
                android:layout_gravity="center"
                CoverFlowPager:listSpacing="10dp"
                CoverFlowPager:rotateFactor="20.0"
                CoverFlowPager:rotateLimit="80.0"
                CoverFlowPager:scaleFactor="0.2"
                CoverFlowPager:alphaFactor="0.3"
                CoverFlowPager:gravityFactor="-5.0"
                CoverFlowPager:listRadius="2.0"
                />
    </com.github.hongchae.coverflow.lib.CoverFlowContainer>
    ...
```
