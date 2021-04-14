package com.ensias.moneyManager.mainActivityPack;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.ensias.moneyManager.Item;
import com.ensias.moneyManager.R;
import com.ensias.moneyManager.data.DataBaseDbHelper;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * BalanceFragment show us our balance. I used a pieChart from this github's repository
 * com.github.PhilJay:MPAndroidChart:v3.0.3
 */

public class BalanceFragment extends Fragment {

    private TextView mSumExpence;
    private TextView mSumIncome;
    private TextView mBalance;
    private DataBaseDbHelper mDataBaseDbHelper;
    private int mTotalExpense;
    private int mTotalIncome;
    private int mBalanceValue;
    private SwipeRefreshLayout mRefreshLayout;
    private List <Integer> mColorsList = new ArrayList<>();
    // ColorList is list of color for pie chart

    private ArrayList<PieEntry> mPieChartValues;
    private PieDataSet mPieChartdataSet;
    private PieData mPieChartdata;
    private PieChart mPieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;
        // We draw our interface (Fragment_balance_layout) and return the view
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBaseDbHelper = new DataBaseDbHelper(getContext());
        mColorsList.add(getResources().getColor(R.color.colorExpense));
        mColorsList.add(getResources().getColor(R.color.colorIncome));

        mBalance = view.findViewById(R.id.sum_balance);
        mSumExpence = view.findViewById(R.id.sum_expense);
        mSumIncome = view.findViewById(R.id.sum_income);

        mRefreshLayout = view.findViewById(R.id.refresh);
        mRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadItemsBalance();
                mRefreshLayout.setRefreshing(false);
                // Add refresh listener our swipe. When we swipe items are loading from database
            }
        });

        /**
         * PieChart is Android chart view / graph view library, supporting line- bar- pie- radar-
         * bubble- and candlestick charts as well as scaling, dragging and animations.
         * More information about this library on link below
         * https://github.com/PhilJay/MPAndroidChart
         */
        mPieChart = view.findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setExtraOffsets(5, 5, 5, 5);
        mPieChart.setDragDecelerationFrictionCoef(0.1f);
        mPieChart.setDrawHoleEnabled(false);
        mPieChart.setHoleColor(Color.WHITE);
        mPieChart.setTransparentCircleRadius(61f);
        mPieChart.animateY(500, Easing.EasingOption.EaseInCubic);

        mPieChartValues = new ArrayList<>();
        mPieChartValues.add(new PieEntry(0f, getResources().getText(R.string.tab_expense).toString() + ", %"));
        mPieChartValues.add(new PieEntry(0f, getResources().getText(R.string.tab_income).toString() + ", %"));
        mPieChartdataSet = new PieDataSet(mPieChartValues, getResources().getText(R.string.tab_balance).toString());
        mPieChartdataSet.setSliceSpace(3f);
        mPieChartdataSet.setSelectionShift(5f);
        mPieChartdataSet.setColors(mColorsList);
        mPieChartdataSet.setValueTextSize(16);
        mPieChartdataSet.setValueTextColor(Color.WHITE);
        mPieChartdata = new PieData(mPieChartdataSet);
        Legend legend = mPieChart.getLegend();
        legend.setEnabled(false);
        loadItemsBalance();
    }

    private void loadItemsBalance(){
        // Load item's expense and income total values. Then we consider a balance
        // Add the values into TextViews and PieChart
        mTotalExpense = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_EXPENSE);
        mTotalIncome = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_INCOME);
        mBalanceValue = mTotalIncome - mTotalExpense;
        mBalance.setText(NumberFormat.getCurrencyInstance().format(mBalanceValue));
        mSumExpence.setText(NumberFormat.getCurrencyInstance().format(mTotalExpense));
        mSumIncome.setText(NumberFormat.getCurrencyInstance().format(mTotalIncome));
        if (mTotalIncome + mTotalExpense != 0){
            mPieChartValues.clear();
            mPieChartValues.add(new PieEntry((float) (mTotalExpense/ 100), getResources().getText(R.string.tab_expense).toString() + ", %"));
            mPieChartValues.add(new PieEntry((float) (mTotalIncome/ 100), getResources().getText(R.string.tab_income).toString() + ", %"));
            mPieChart.setData(mPieChartdata);
        }
    }
}
