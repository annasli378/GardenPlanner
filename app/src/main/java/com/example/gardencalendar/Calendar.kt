package com.example.gardencalendar

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter

class Calendar : AppCompatActivity(), CallendarAdapter.OnItemListener {

    private lateinit var monthYearText: TextView;
    private lateinit var calendarRecyclerView: RecyclerView;
    private lateinit var selectedDate: LocalDate;

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        initWidgets();
        selectedDate = LocalDate.now();
        setMonthView();

    }


    private fun initWidgets() {
        calendarRecyclerView = findViewById(R.id.calendarRecyclerView);
        monthYearText = findViewById(R.id.monthYearTV);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        monthYearText.setText(monthYearFromDate(selectedDate));
        var daysInMonth: ArrayList<String> = daysInMonthArray(selectedDate);

        var callendarAdapter: CallendarAdapter = CallendarAdapter(daysInMonth, this);
        var layoutManager: RecyclerView.LayoutManager = GridLayoutManager(applicationContext, 7);
        calendarRecyclerView.layoutManager = layoutManager;
        calendarRecyclerView.adapter = callendarAdapter;

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daysInMonthArray(date: LocalDate): ArrayList<String> {
        var daysInMonthArray: ArrayList<String> = ArrayList<String>();
        var yearMonth: YearMonth = YearMonth.from(date);

        var daysInMonth: Int = yearMonth.lengthOfMonth();
        var firstOfMonth: LocalDate = selectedDate.withDayOfMonth(1);
        var dayOfWeek: Int = firstOfMonth.dayOfWeek.value;

        for ( i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("");
            }
            else {
                daysInMonthArray.add( (i-dayOfWeek).toString() )
            }
        }
        return daysInMonthArray;

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun monthYearFromDate(date: LocalDate): String {
        var formatter:DateTimeFormatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun previousMonthAction(view: View) {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    public fun nextMonthAction(view: View) {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, dayText: String?) {
        if (dayText.equals("")) {
            var message: String = "Selected date "+ dayText + " "+monthYearFromDate(selectedDate);
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }
}