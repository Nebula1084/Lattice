package com.sea.lattice.chart;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

/**
 * Created by Sea on 10/6/2015.
 */
public class Pie {
    private CategorySeries mSeries;
    private DefaultRenderer mRenderer;
    private SimpleSeriesRenderer renderer;
    private GraphicalView mChartView;
    private LinearLayout layout;

    public Pie(Context context, LinearLayout layout, int W, int H) {
        this.layout = layout;
        mSeries = new CategorySeries("");
        mRenderer = new DefaultRenderer();
        mRenderer.setStartAngle(180);
        mRenderer.setDisplayValues(true);
        mRenderer.setLabelsTextSize((float) (20*Math.sqrt(W*H/480/800)));
        mRenderer.setLegendTextSize((float) (20*Math.sqrt(W*H/480/800)));
        mRenderer.setPanEnabled(false);
        mRenderer.setZoomEnabled(false);
        mChartView = ChartFactory.getPieChartView(context, mSeries, mRenderer);
    }

    public void paint(double b, double ob, double w, double ow) {
        layout.removeAllViews();
        layout.addView(mChartView, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        mSeries.clear();
        mRenderer.removeAllRenderers();
        if (b != 0) {
            mSeries.add("黑业", b);
            renderer = new SimpleSeriesRenderer();
            renderer.setColor(Color.BLACK);
            mRenderer.addSeriesRenderer(renderer);
            mChartView.repaint();
        }
        if (ob != 0) {
            mSeries.add("黑业对治", ob);
            renderer = new SimpleSeriesRenderer();
            renderer.setColor(Color.GREEN);
            mRenderer.addSeriesRenderer(renderer);
            mChartView.repaint();
        }
        if (w != 0) {
            mSeries.add("白业", w);
            renderer = new SimpleSeriesRenderer();
            renderer.setColor(Color.WHITE);
            mRenderer.addSeriesRenderer(renderer);
            mChartView.repaint();
        }
        if (ow != 0) {
            mSeries.add("白业对治", ow);
            renderer = new SimpleSeriesRenderer();
            renderer.setColor(Color.RED);
            mRenderer.addSeriesRenderer(renderer);
            mChartView.repaint();
        }
    }

    public void setRenderer() {
        mRenderer.setDisplayValues(false);
        mRenderer.setShowLabels(false);
        mRenderer.setShowLegend(false);
    }
}