package com.example.firsttask.ui.transactions.view.chart;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.firsttask.R;
import com.example.firsttask.databinding.FragmentCreatingChartBinding;

public class CreatingChartFragment extends Fragment {

    private FragmentCreatingChartBinding binding;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreatingChartBinding.inflate(inflater, container, false);

        fillFields();

        binding.btnChartStart.setOnClickListener(l -> {
            if (binding.etQuantityName1.getText().length() == 0
                    || binding.etQuantityName2.getText().length() == 0
                    || binding.etQuantity1.getText().length() == 0
                    || binding.etQuantity2.getText().length() == 0
                    || binding.etChartTitle.getText().length() == 0
            ) {
                errorDialog();
            } else {
                startWhirligig();
            }
        });

        return binding.getRoot();
    }

    private void fillFields() {

        binding.etChartTitle.setText("Title");

        binding.etQuantityName1.setText("aaaaaa");
        binding.etQuantityName2.setText("bbbbbb");
        binding.etQuantityName3.setText("cccccc");
        binding.etQuantityName4.setText("dddddd");
        binding.etQuantityName5.setText("eeeeee");
        binding.etQuantityName6.setText("ffffff");
        binding.etQuantityName7.setText("ggggg");

        binding.etQuantity1.setText("80540");
        binding.etQuantity2.setText("94190");
        binding.etQuantity3.setText("102610");
        binding.etQuantity4.setText("110430");
        binding.etQuantity5.setText("128000");
        binding.etQuantity6.setText("143760");
        binding.etQuantity7.setText("170670");

    }

    private void startWhirligig() {

        final FragmentManager fm = ((AppCompatActivity) getActivity()).getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();
        WhirligigChartFragment f2 = new WhirligigChartFragment();
        Bundle b = new Bundle();
        b.putParcelable("UNIQUE_KEY", getDataFromFields());
        f2.setArguments(b);
        ft.add(R.id.fragment_place, f2, "TAG_WhirligigChartFragment");
        ft.addToBackStack(null);
        ft.commit();
    }

    private ModelWhirligigChart getDataFromFields() {

        Integer quantity3, quantity4, quantity5, quantity6, quantity7;

        if(binding.etQuantity3.getText().toString().equals("")){
             quantity3 = 0;
        } else {
             quantity3 = Integer.parseInt(binding.etQuantity3.getText().toString());
        }
        if(binding.etQuantity4.getText().toString().equals("")){
             quantity4 = 0;
        } else {
             quantity4 = Integer.parseInt(binding.etQuantity4.getText().toString());
        }
        if(binding.etQuantity5.getText().toString().equals("")){
             quantity5 = 0;
        } else {
             quantity5 = Integer.parseInt(binding.etQuantity5.getText().toString());
        }
        if(binding.etQuantity6.getText().toString().equals("")){
             quantity6 = 0;
        } else {
             quantity6 = Integer.parseInt(binding.etQuantity6.getText().toString());
        }
        if(binding.etQuantity7.getText().toString().equals("")){
             quantity7 = 0;
        } else {
             quantity7 = Integer.parseInt(binding.etQuantity7.getText().toString());
        }

        return new ModelWhirligigChart(
                binding.etChartTitle.getText().toString(),
                binding.etQuantityName1.getText().toString(),
                Integer.parseInt(binding.etQuantity1.getText().toString()),
                binding.etQuantityName2.getText().toString(),
                Integer.parseInt(binding.etQuantity2.getText().toString()),
                binding.etQuantityName3.getText().toString(),
                quantity3,
                binding.etQuantityName4.getText().toString(),
                quantity4,
                binding.etQuantityName5.getText().toString(),
                quantity5,
                binding.etQuantityName6.getText().toString(),
                quantity6,
                binding.etQuantityName7.getText().toString(),
                quantity7
        );
    }

    private void errorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Error")
                .setMessage("quantities1 and quantities2 required")
                .setNeutralButton("OK", (dialog, which) -> {
                    dialog.cancel();
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}