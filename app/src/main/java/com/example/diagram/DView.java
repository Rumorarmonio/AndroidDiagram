package com.example.diagram;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Random;

public class DView extends View {
    ArrayList<Sector> sectors = new ArrayList<>();
    private static float w;
    private static float h;
    private float centerW = w / 2;
    private float centerH = h / 2;
    private int[] ratio;
    private Sector selected;
    Random random = new Random();

    /*public final int[] colors = {Color.BLACK, Color.BLUE, Color.CYAN, Color.DKGRAY, Color.GRAY, Color.GREEN, Color.LTGRAY, Color.MAGENTA, Color.RED, Color.TRANSPARENT, Color.WHITE, Color.YELLOW};
    public final int[] colors = {Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW};*/

    public DView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DView(Context context) {
        super(context);
    }

    public DView(Context context, int[] ratio) {
        super(context);
        this.ratio = ratio;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            /*this.x = x;
            this.y = y;*/
            Log.d("mytag", "coordinates = " + x + ", " + y);
            float[] distanceAndAngle = Sector.getDistanceAndAngle(x, y);
            for (Sector currentSector : sectors)
                if (currentSector.isPointInto(distanceAndAngle)) {
                    currentSector.setHighlighted(currentSector.getColor());
                    Toast.makeText(DView.super.getContext(), "The number of sector is " + sectors.indexOf(currentSector) + "\nIts weight equals " + ratio[sectors.indexOf(currentSector)], Toast.LENGTH_SHORT).show();
                    if (selected != null) {
                        SectorAnimation sa1 = new SectorAnimation(this, selected, centerW, centerH, selected.getStartAngle(), selected.getSweepAngle());
                        sa1.execute();
                    }
                    if (currentSector == selected) {
                        selected = null;
                        break;
                    }
                    float centerAngle = currentSector.getCenterAngle();
                    SectorAnimation sa2 = new SectorAnimation(this, currentSector, centerW + (float) Math.cos(centerAngle) * 80, centerH - (float) Math.sin(centerAngle) * 80, currentSector.getStartAngle(), currentSector.getSweepAngle());
                    sa2.execute();
                    selected = currentSector;
                    break;
                }
        }
        invalidate();
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        this.w = getMeasuredWidth();
        this.h = getMeasuredHeight();
        this.centerW = w / 2;
        this.centerH = h / 2;
        Log.d("mytag", "centerW = " + this.centerW + " centerH = " + this.centerH);
        // только если размер или позиция изменились
        if (changed) {
            float piece = 0;

            for (int i = 0; i < ratio.length; i++)
                piece += ratio[i];

            piece = (float) 360 / piece;
            float startAngle = 0;

            short difference = 300;

            for (int i = 0; i < ratio.length; i++) {
                float sweepAngle = piece * ratio[i];

                int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
                if (i > 0)
                    color = generateColor(i, difference, color);

                sectors.add(new Sector(centerW, centerH, 500, startAngle, sweepAngle, color));
                startAngle += sweepAngle;
                Log.d("mytag", "red = " + Color.red(color) + " blue = " + Color.blue(color) + " green = " + Color.green(color));
            }
            Log.d("mytag", "Sectors added, array length = " + sectors.size());
        }
    }

    private int generateColor(int i, short difference, int color) {
        while ((Math.abs(Color.red(sectors.get(i - 1).getColor()) - Color.red(color)) +
                Math.abs(Color.green(sectors.get(i - 1).getColor()) - Color.green(color)) +
                Math.abs(Color.blue(sectors.get(i - 1).getColor()) - Color.blue(color))) < difference) {
            color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
            Log.d("mytag", "i = " + i);
        }
        return color;
    }

    public static float getW() {
        return w;
    }

    public static float getH() {
        return h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Sector s : sectors)
            s.draw(canvas);
    }
}