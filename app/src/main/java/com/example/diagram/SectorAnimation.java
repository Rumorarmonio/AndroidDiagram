package com.example.diagram;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

public class SectorAnimation extends AsyncTask<Void, Void, Void> {
    Sector sector;
    View view;
    float x, y;
    float xDelta, yDelta;
    float startAngle, sweepAngle;

    public SectorAnimation(View view, Sector sector, float centerW, float centerH, float startAngle, float sweepAngle) {
        this.view = view;
        this.sector = sector;
        this.x = centerW;
        this.y = centerH;
        this.startAngle = startAngle;
        this.sweepAngle = sweepAngle;
        Log.d("mytag", "centerW = " + centerW + " centerH = " + centerH);
        xDelta = (centerW - sector.getX()) / 10;
        yDelta = (centerH - sector.getY()) / 10;
        Log.d("mytag", "xDelta = " + xDelta + " yDelta = " + yDelta);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        for (int i = 0; i < 10; i++) {
            sector.move(xDelta, yDelta);
            try {
                Thread.sleep(10);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            publishProgress();
        }
//        sector.setPosition(x, y);
        Log.d("mytag", "x = " + x + " y = " + y);
        publishProgress();
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
        view.invalidate();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        // выполняется в основном потоке
    }
}