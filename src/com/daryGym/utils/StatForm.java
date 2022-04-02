package com.daryGym.utils;

import com.codename1.charts.ChartComponent;
import com.codename1.charts.models.CategorySeries;
import com.codename1.charts.models.MultipleCategorySeries;
import com.codename1.charts.models.TimeSeries;
import com.codename1.charts.models.XYMultipleSeriesDataset;
import com.codename1.charts.models.XYSeries;
import com.codename1.charts.renderers.DefaultRenderer;
import com.codename1.charts.renderers.SimpleSeriesRenderer;
import com.codename1.charts.renderers.XYMultipleSeriesRenderer;
import com.codename1.charts.renderers.XYSeriesRenderer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.charts.views.PieChart;
import com.codename1.charts.views.PointStyle;
import com.codename1.ui.Component;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.daryGym.services.reclamationService;
import java.util.Date;
import java.util.List;




/**
 * An abstract class for the demo charts to extend. It contains some methods for
 * building datasets and renderers.
 */
public  class StatForm  {

 /**
 * Creates a renderer for the specified colors.
 */
    public StatForm()
    {}
private DefaultRenderer buildCategoryRenderer(int[] colors) {
    DefaultRenderer renderer = new DefaultRenderer();
    renderer.setLabelsTextSize(15);
    renderer.setLegendTextSize(50);
    renderer.setMargins(new int[]{20, 30, 15, 0});
    for (int color : colors) {
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(color);
        renderer.addSeriesRenderer(r);
    }
    return renderer;
}

/**
 * Builds a category series using the provided values.
 *
 * @param titles the series titles
 * @param values the values
 * @return the category series
 */
protected CategorySeries buildCategoryDataset(String title, Double[] values) {
    CategorySeries series = new CategorySeries(title);
    int k = 0;
    int j = 1 ;
    
    for (Double value : values) {
        //series.add("Pourcentage Entre 1 et 2 ", value);
        if (j==1)
        {
            series.add("Pourcentage des reclamations elevées ", value);
           
            j++;
        }
        else if (j==2)
        {
            series.add("Pourcentage des reclamations moyennes ", value);
           
            j++;
        }
        else if (j==3)
        {
            series.add("Pourcentage des reclamations faibles ", value);
            
            j++;
        }
    }

    return series;
}

public Form createPieChartForm() {
    // Generate the values
    reclamationService cs = new reclamationService();
    
    Double[] vv = cs.StatReclamation();
    
    // Set up the renderer
    int[] colors = new int[]{ColorUtil.BLUE, ColorUtil.GREEN, ColorUtil.MAGENTA};
    DefaultRenderer renderer = buildCategoryRenderer(colors);
    renderer.setZoomButtonsVisible(true);
    renderer.setZoomEnabled(true);
    renderer.setChartTitleTextSize(20);
    renderer.setDisplayValues(true);
    renderer.setShowLabels(true);
    SimpleSeriesRenderer r = renderer.getSeriesRendererAt(0);
    r.setGradientEnabled(true);
    r.setGradientStart(0, ColorUtil.BLUE);
    r.setGradientStop(0, ColorUtil.GREEN);
    r.setHighlighted(true);

    // Create the chart ... pass the values and renderer to the chart object.
    PieChart chart = new PieChart(buildCategoryDataset("Project budget", vv), renderer);

    // Wrap the chart in a Component so we can add it to a form
    ChartComponent c = new ChartComponent(chart);

    // Create a form and show it.
  //  Double m = cs.moyenne(k);
  //  Label l = new Label(""+m);
    Form f = new Form("stat des reclamations : ", new BorderLayout());
    
    //f.add(BorderLayout.CENTER,l.getText());
    f.add(BorderLayout.CENTER, c);
    return f;

}
}