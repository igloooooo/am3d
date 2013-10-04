package au.com.iglooit.am3d.web;

import au.com.iglooit.am3d.web.data.DataProvider;
import au.com.iglooit.am3d.web.data.DataProvider.Movie;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;

import java.util.ArrayList;

public class TopSixTheatersChart extends Chart {

    public TopSixTheatersChart() {
        // TODO this don't actually visualize top six theaters, but just makes a
        // pie chart
        super(ChartType.PIE);

        setCaption("Popular Movies");
        getConfiguration().setTitle("");
        getConfiguration().getChart().setType(ChartType.PIE);
        setWidth("100%");
        setHeight("90%");

        DataSeries series = new DataSeries();

        ArrayList<Movie> movies = DataProvider.getMovies();
        for (int i = 0; i < 6; i++) {
            Movie movie = movies.get(i);
            series.add(new DataSeriesItem(movie.title, movie.score));
        }
        getConfiguration().setSeries(series);
    }

}
