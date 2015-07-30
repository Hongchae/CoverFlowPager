package com.github.hongchae.coverflow.lib;

import android.content.Context;
import android.graphics.*;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class AbstractCoverFlowAdapter<T> extends PagerAdapter {

    protected Context context;

    protected final List<T> items;

    public AbstractCoverFlowAdapter(Context context, List<T> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }

    public Bitmap getReflectionBitmap(Bitmap original) {
        final int w = original.getWidth();
        final int h = original.getHeight();
        final int rh = (h / 2);
        final int gradientColor = Color.argb(0x66, 0xff, 0xff, 0xff);

        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);
        final Bitmap reflection = Bitmap.createBitmap(original, 0, rh, w, rh, matrix, false);

        Paint paint = new Paint();
        final LinearGradient shader = new LinearGradient(0, 0, 0, reflection.getHeight(), gradientColor, 0x00ffffff, Shader.TileMode.CLAMP);
        paint.reset();
        paint.setShader(shader);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));

        Canvas canvas = new Canvas(reflection);
        canvas.setBitmap(reflection);
        canvas.drawRect(0, 0, reflection.getWidth(), reflection.getHeight(), paint);

        return reflection;
    }
}
