package com.example.diagram;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import java.util.Random;

public class Sector {
    private float x, y, radius, startAngle, sweepAngle;
    private boolean highlighted = false;
    private int color;
    private Random random = new Random();

    public Sector(float x, float y, float radius, float startAngle, float sweepAngle, int color) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        this.color = color;
    }

    public static float[] getDistanceAndAngle(float x, float y) {
        float distance = (float) Math.sqrt(Math.pow(Math.abs(x - DView.getW() / 2), 2) + Math.pow(Math.abs(y - DView.getH() / 2), 2));
        float angle = (float) Math.toDegrees(Math.atan2(y - DView.getH() / 2, x - DView.getW() / 2));
        if (angle < 0)
            angle = 360 + angle;
        Log.d("mytag", "distance = " + distance + " angle = " + angle);
        float[] distanceAndAngle = {distance, angle};
        return distanceAndAngle;
    }

    public boolean isHighlighted() {
        return highlighted;
    }

    public boolean isPointInto(float distanceAndAngle[]) {
        return (distanceAndAngle[1] > startAngle && distanceAndAngle[1] < startAngle + sweepAngle && distanceAndAngle[0] <= radius); // 2) функция должна сообщать, содержится ли точка внутри сектора
    }

    public void setHighlighted(int color) {
        highlighted = highlighted == false ? true : false;
        changeColor(color);
        // 3*) реализовать отделение сектора (см. иллюстрацию к заданию)
    }

    private int changeColor(int color) {
        this.color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256)); // 3) реализовать замену цвета
        while ((Math.abs(Color.red(color) - Color.red(this.color)) +
                Math.abs(Color.green(color) - Color.green(this.color)) +
                Math.abs(Color.blue(color) - Color.blue(this.color))) < 300)
            color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        return color;
    }

    public void draw(Canvas c) {
        Paint paint = new Paint();
        paint.setColor(color);
        c.drawArc(x - radius, y - radius, x + radius, y + radius, startAngle, sweepAngle, true, paint);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getColor() {
        return color;
    }

    public float getStartAngle() {
        return startAngle;
    }

    public float getSweepAngle() {
        return sweepAngle;
    }

    public float getCenterAngle() {
        float centerAngle = startAngle + sweepAngle / 2;
        Log.d("mytag", "startAngle + sweepAngle / 2 = " + centerAngle);
        return centerAngle;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void move(float x, float y) {
        setPosition(this.x + x, this.y + y);
    }
}