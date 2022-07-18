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
import com.anychart.charts.Pie;
import com.anychart.enums.Align;
import com.anychart.enums.LegendLayout;
import com.example.firsttask.databinding.FragmentPieChartBinding;

import java.util.ArrayList;
import java.util.List;

public class PieChartFragment extends Fragment {

    private FragmentPieChartBinding binding;
    private AnyChartView anyChartView;

    private ModelWhirligigChart data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentPieChartBinding.inflate(inflater, container, false);


        Bundle bundle = getArguments();
        if (bundle != null) {
            data = bundle.getParcelable("UNIQUE_KEY");
            createPieChart(data);
        }

        return binding.getRoot();
    }

    private void createPieChart(ModelWhirligigChart model) {
        anyChartView = binding.anyChartView;
        anyChartView.setProgressBar(binding.progressBar);

        Pie pie = AnyChart.pie();

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

        pie.data(data);

        pie.animation(true);

//        pie.title("Fruits imported in 2015 (in kg)");

        pie.labels().position("outside");

        pie.legend().title().enabled(true);
        pie.legend().title()
                .text(model.getTitle())
                .padding(0d, 0d, 10d, 0d);

        pie.legend()
                .position("center-bottom")
                .itemsLayout(LegendLayout.HORIZONTAL)
                .align(Align.CENTER);

        pie.background().enabled(true);
        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
            case Configuration.UI_MODE_NIGHT_YES:
                pie.background().fill("#ff000000");
                break;
            case Configuration.UI_MODE_NIGHT_NO:
                pie.background().fill("#ffffff");
                break;
        }

        anyChartView.setChart(pie);
    }

}