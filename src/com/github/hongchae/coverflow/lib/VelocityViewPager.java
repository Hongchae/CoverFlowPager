package com.github.hongchae.coverflow.lib;

/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Author: Dororo @ StackOverflow
 * An extended ViewPager which implements multiple page flinging.
 *
 */

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.Scroller;

public class VelocityViewPager extends ViewPager implements GestureDetector.OnGestureListener {
    private GestureDetector mGestureDetector;
    private FlingRunnable mFlingRunnable = new FlingRunnable();
    private boolean mScrolling = false;
    private boolean mFling = false;

    public VelocityViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        setPageTransformer(false, new CoverFlowTransformer(context, attrs));
        mGestureDetector = new GestureDetector(context, this);
    }

    // We have to intercept this touch event else fakeDrag functions won't work as it will
// be in a real drag when we want to initialise the fake drag.
    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return true;
    }

    private float downX = 0f;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // give all the events to the gesture detector. I'm returning true here so the viewpager doesn't
        // get any events at all, I'm sure you could adjust this to make that not true.
        mGestureDetector.onTouchEvent(event);

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            downX = event.getX();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            onUp(event.getX() - downX);
        }
        return true;
    }

    public void onUp(float deltaX) {
        if (!mFling) this.onFling(null, null, deltaX, 0);
        mFling = false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velX, float velY) {
        mFling = true;
        mFlingRunnable.startUsingVelocity((int) (velX * 0.7));
        return false;
    }

    private void trackMotion(float distX) {

        // The following mimics the underlying calculations in ViewPager
        float scrollX = getScrollX() - distX;
        final int width = getWidth();
        final int widthWithMargin = width + this.getPageMargin();
        final float leftBound = Math.max(0, (this.getCurrentItem() - 1) * widthWithMargin);
        final float rightBound = Math.min(this.getCurrentItem() + 1, this.getAdapter().getCount() - 1) * widthWithMargin;

        if (scrollX < leftBound) {
            // Now we know that we've hit the bound, flip the page
            if (this.getCurrentItem() > 0) {
                this.setCurrentItem(this.getCurrentItem() - 1, false);
            }
        } else if (scrollX > rightBound) {
            // Now we know that we've hit the bound, flip the page
            if (this.getCurrentItem() < (this.getAdapter().getCount() - 1)) {
                this.setCurrentItem(this.getCurrentItem() + 1, false);
            }
        }

        // Do the fake dragging
        if (mScrolling) {
            this.fakeDragBy(distX);
        } else {
            this.beginFakeDrag();
            this.fakeDragBy(distX);
            mScrolling = true;
        }

    }

    private void endFlingMotion() {
        mScrolling = false;
        this.endFakeDrag();
    }

    // The fling runnable which moves the view pager and tracks decay
    private class FlingRunnable implements Runnable {
        private Scroller mScroller; // use this to store the points which will be used to create the scroll
        private int mLastFlingX;

        private FlingRunnable() {
            mScroller = new Scroller(getContext());
        }

        public void startUsingVelocity(int initialVel) {
            if (initialVel == 0) {
                // there is no velocity to fling!
                return;
            }

            removeCallbacks(this); // stop pending flings

            int initialX = initialVel < 0 ? Integer.MAX_VALUE : 0;
            mLastFlingX = initialX;
            // setup the scroller to calulate the new x positions based on the initial velocity. Impose no cap on the min/max x values.
            mScroller.fling(initialX, 0, initialVel, 0, 0, Integer.MAX_VALUE, 0, Integer.MAX_VALUE);

            post(this);
        }

        private void endFling() {
            mScroller.forceFinished(true);
            endFlingMotion();
        }

        @Override
        public void run() {

            final Scroller scroller = mScroller;
            boolean animationNotFinished = scroller.computeScrollOffset();
            final int x = scroller.getCurrX();
            int delta = x - mLastFlingX;

            trackMotion(delta);

            if (animationNotFinished) {
                mLastFlingX = x;
                post(this);
            } else {
                endFling();
            }

        }
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distX, float distY) {
        trackMotion(-distX);
        return false;
    }

    // Unused Gesture Detector functions below

    @Override
    public boolean onDown(MotionEvent event) {
        return false;
    }


    @Override
    public void onLongPress(MotionEvent event) {
        // we don't want to do anything on a long press, though you should probably feed this to the page being long-pressed.
    }

    @Override
    public void onShowPress(MotionEvent event) {
        // we don't want to show any visual feedback
    }

    @Override
    public boolean onSingleTapUp(MotionEvent event) {
        // we don't want to snap to the next page on a tap so ignore this
        return false;
    }

}