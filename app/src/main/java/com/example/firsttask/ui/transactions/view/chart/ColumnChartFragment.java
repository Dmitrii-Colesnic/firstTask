package com.example.firsttask.ui.transactions.view.chart;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.core.cartesian.series.Column;
import com.anychart.enums.Anchor;
import com.anychart.enums.HoverMode;
import com.anychart.enums.Position;
import com.anychart.enums.TooltipPositionMode;
import com.example.firsttask.databinding.FragmentColumnChartBinding;

import java.util.ArrayList;
import java.util.List;

public class ColumnChartFragment extends Fragment {

    private FragmentColumnChartBinding binding;

    private ModelWhirligigChart data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentColumnChartBinding.inflate(inflater, container, false);

        Bundle bundle = getArguments();
        if (bundle != null) {
            data = bundle.getParcelable("UNIQUE_KEY");
            createColumnChart(data);
        }

        return binding.getRoot();
    }

    private void createColumnChart(ModelWhirligigChart model) {
        AnyChartView anyChartView = binding.anyChartView;
        anyChartView.setProgressBar(binding.progressBar);

        Cartesian cartesian = AnyChart.column();

        List<DataEntry> data = new ArrayList<>();
        data.add(new ValueDataEntry(model.getQuantityName1(), model.getQuantity1()));
        data.add(new ValueDataEntry(model.getQuantityName2(), model.getQuantity2()));
        if (model.getQuantity3() != 0) {
            data.add(new ValueDataEntry(model.getQuantityName3(), model.getQuantity3()));
        }
        if (model.getQuantity4() != 0) {
            data.add(new ValueDataEntry(model.getQuantityName4(), model.getQuantity4()));
        }
        if (model.getQuantity5() != 0) {
            data.add(new ValueDataEntry(model.getQuantityName5(), model.getQuantity5()));
        }
        if (model.getQuantity6() != 0) {
            data.add(new ValueDataEntry(model.getQuantityName6(), model.getQuantity6()));
        }
        if (model.getQuantity7() != 0) {
            data.add(new ValueDataEntry(model.getQuantityName7(), model.getQuantity7()));
        }

        Column column = cartesian.column(data);

        column.tooltip()
                .titleFormat("{%X}")
                .position(Position.CENTER_BOTTOM)
                .anchor(Anchor.CENTER_BOTTOM)
                .offsetX(0d)
                .offsetY(5d)
                .format("Value: {%Value}{groupsSeparator: }");

        cartesian.animation(true);
        cartesian.title(model.getTitle());

        cartesian.yScale().minimum(0d);

        cartesian.yAxis(0).labels().format("${%Value}{groupsSeparator: }");

        cartesian.tooltip().positionMode(TooltipPositionMode.POINT);
        cartesian.interactivity().hoverMode(HoverMode.BY_X);

//        cartesian.xAxis(0).title("Product");
//        cartesian.yAxis(0).title("Revenue");

        cartesian.background().enabled(true);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                cartesian.background().fill("#ff000000");
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                cartesian.background().fill("#ffffff");
                break;
        }

        anyChartView.setChart(cartesian);
    }
}