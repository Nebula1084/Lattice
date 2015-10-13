package com.sea.lattice.chart;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import java.util.List;

public class Line {
    private XYMultipleSeriesDataset mDataset;
    private XYMultipleSeriesRenderer mxyRenderer;
    private XYSeries bSeries, obSeries, wSeries, owSeries;
    private GraphicalView mxyChartView;
    private LinearLayout layout;
    private int W, H;

    public Line(Context context, LinearLayout layout, int W, int H) {
        int colors[];
        PointStyle styles[];

        this.layout = layout;
        this.W = W;
        this.H = H;
        mDataset = new XYMultipleSeriesDataset();
        mxyRenderer = new XYMultipleSeriesRenderer();
        mxyChartView = ChartFactory.getLineChartView(context, mDataset,
                mxyRenderer);
        mxyRenderer.setDisplayValues(true);
        mxyRenderer.setPanEnabled(false);
        mxyRenderer.setZoomEnabled(false, false);
        bSeries = new XYSeries("黑业");
        obSeries = new XYSeries("黑业对治");
        wSeries = new XYSeries("白业");
        owSeries = new XYSeries("白业对治");
        colors = new int[]{Color.BLACK, Color.GREEN, Color.WHITE, Color.RED};
        styles = new PointStyle[]{PointStyle.CIRCLE, PointStyle.DIAMOND,
                PointStyle.TRIANGLE, PointStyle.SQUARE};
        setRenderer(mxyRenderer, colors, styles);
        int length = mxyRenderer.getSeriesRendererCount();
        for (int i = 0; i < length; i++) {
            ((XYSeriesRenderer) mxyRenderer.getSeriesRendererAt(i))
                    .setFillPoints(true);
        }
        mDataset.addSeries(bSeries);
        mDataset.addSeries(obSeries);
        mDataset.addSeries(wSeries);
        mDataset.addSeries(owSeries);
    }

    public void paint(double[] b, double[] ob, double[] w, double[] ow) {
        int max = 0;
        layout.removeAllViews();
        layout.addView(mxyChartView, new LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        bSeries.clear();
        for (int i = 0; i < b.length; i++) {
            bSeries.add(i + 1, b[i]);
            if (max < b[i])
                max = (int) b[i];
        }
        obSeries.clear();
        for (int i = 0; i < ob.length; i++) {
            obSeries.add(i + 1, ob[i]);
            if (max < ob[i])
                max = (int) ob[i];
        }
        wSeries.clear();
        for (int i = 0; i < w.length; i++) {
            wSeries.add(i + 1, w[i]);
            if (max < w[i])
                max = (int) w[i];
        }
        owSeries.clear();
        for (int i = 0; i < ow.length; i++) {
            owSeries.add(i + 1, ow[i]);
            if (max < ow[i])
                max = (int) ow[i];
        }
        max = max * 2;
        setChartSettings(mxyRenderer, "", "时间", "业习", 1, b.length, 0, max,
                Color.LTGRAY, Color.LTGRAY);
        mxyChartView.repaint();
    }

    private void setRenderer(XYMultipleSeriesRenderer renderer, int[] colors,
                             PointStyle[] styles) {
        renderer.setAxisTitleTextSize((float) (20 * Math.sqrt(W * H / 480 / 800)));
        renderer.setChartTitleTextSize((float) (20 * Math.sqrt(W * H / 480 / 800)));
        renderer.setLabelsTextSize((float) (20 * Math.sqrt(W * H / 480 / 800)));
        renderer.setLegendTextSize((float) (20 * Math.sqrt(W * H / 480 / 800)));
        renderer.setPointSize(5f);
        renderer.setMargins(new int[]{30, 30, 30, 30});
        renderer.setMarginsColor(Color.TRANSPARENT);
        int length = colors.length;
        for (int i = 0; i < length; i++) {
            XYSeriesRenderer r = new XYSeriesRenderer();
            r.setColor(colors[i]);
            r.setPointStyle(styles[i]);
            r.setLineWidth(3);
            renderer.addSeriesRenderer(r);
        }
    }

    protected void setChartSettings(XYMultipleSeriesRenderer renderer,
                                    String title, String xTitle, String yTitle, double xMin,
                                    double xMax, double yMin, double yMax, int axesColor,
                                    int labelsColor) {
        renderer.setChartTitle(title);
        renderer.setXTitle(xTitle);
        renderer.setYTitle(yTitle);
        renderer.setXAxisMin(xMin);
        renderer.setXAxisMax(xMax);
        renderer.setYAxisMin(yMin);
        renderer.setYAxisMax(yMax);
        renderer.setAxesColor(axesColor);
        renderer.setLabelsColor(labelsColor);
    }
}