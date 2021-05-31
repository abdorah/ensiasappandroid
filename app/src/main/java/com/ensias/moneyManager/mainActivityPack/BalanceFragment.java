package com.ensias.moneyManager.mainActivityPack;

import android.graphics.Color;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
import java.util.Locale;


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


    private ArrayList<PieEntry> mPieChartValues;
    private PieDataSet mPieChartdataSet;
    private PieData mPieChartdata;
    private PieChart mPieChart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_balance, container, false);
        return view;

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

            }
        });


        mPieChart = view.findViewById(R.id.pie_chart);
        mPieChart.setUsePercentValues(true);
        mPieChart.getDescription().setEnabled(false);


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

        mTotalExpense = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_EXPENSE);
        mTotalIncome = mDataBaseDbHelper.loadTotalValuesForBalance(Item.TYPE_INCOME);
        mBalanceValue = mTotalIncome - mTotalExpense;
        mBalance.setText(NumberFormat.getCurrencyInstance(Locale.FRANCE).format(mBalanceValue));
        mSumExpence.setText(NumberFormat.getCurrencyInstance(Locale.FRANCE).format(mTotalExpense));
        mSumIncome.setText(NumberFormat.getCurrencyInstance(Locale.FRANCE).format(mTotalIncome));
        if (mTotalIncome + mTotalExpense != 0){
            mPieChartValues.clear();
            mPieChartValues.add(new PieEntry((float) (mTotalExpense/ 100), getResources().getText(R.string.tab_expense).toString() + ", %"));
            mPieChartValues.add(new PieEntry((float) (mTotalIncome/ 100), getResources().getText(R.string.tab_income).toString() + ", %"));
            mPieChart.setData(mPieChartdata);
        }
    }
}
