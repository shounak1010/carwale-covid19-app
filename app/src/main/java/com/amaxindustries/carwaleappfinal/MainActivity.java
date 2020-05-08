package com.amaxindustries.carwaleappfinal;

import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private TextView covidtext;
    private Dialog loadingDialog;
    private String myJsonText;
    static private int tv0clicked = 0;
    static private int tv1clicked = 0;
    static private int tv2clicked = 0;
    static private int tv3clicked = 0;
    private Button button;
    private TableLayout stk;
    private long globalTotal = 0;
    private long globalDeaths = 0;
    private long globalRecovered = 0;
    private TextView txt1;
    private TextView txt2;
    private TextView txt3;
    private Spinner dropdown1;
    private Spinner dropdown;
    private Button Button1;
    static String selectedcases;
    static String selectedoperator;
    long number;
    private EditText editText;
    ArrayList<Country> countryList = new ArrayList<Country>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        covidtext = findViewById(R.id.text);
        editText = (EditText) findViewById(R.id.editText);
        number = Long.parseLong((editText.getText().toString()));
        dropdown1 = findViewById(R.id.spinner1);
        dropdown = findViewById(R.id.spinner);
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://covid-19-data.p.rapidapi.com/country/all?format=json")
                .get()
                .addHeader("x-rapidapi-host", "covid-19-data.p.rapidapi.com")
                .addHeader("x-rapidapi-key", "0904b15f55msh1c1552b93e3040bp12b225jsn9b2c9499dd6b")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    myJsonText = response.body().string();
                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            covidtext.setText(myJsonText);
                            init(myJsonText);
                            stk = (TableLayout) findViewById(R.id.table_main);
                            TableRow row = (TableRow) stk.getChildAt(0); // Here get row id depending on number of row
                            button = (Button) row.getChildAt(0);
                            button.setOnClickListener(mListener);
                        }
                    });
                }
            }
        });

        String[] items = new String[]{"Total Cases", "Deaths", "Recovered"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(adapter);
        dropdown.setOnItemSelectedListener(onitemclick0);

        String[] items1 = new String[]{">=", "<="};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items1);
        dropdown1.setAdapter(adapter1);
        dropdown1.setOnItemSelectedListener(onitemclick1);
        Button1 = findViewById(R.id.Search);
        Button1.setOnClickListener(searchlistener);
    }

    public void init(String JsonFile) {
        try {
            JSONArray jsonArray = new JSONArray(JsonFile);
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            // Get all jsonObject from jsonArray
            for (
                    int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country countryObject = new Country();
                if (jsonObject.has("country") && !jsonObject.isNull("country")) {
                    countryObject.country = jsonObject.getString("country");
                }
                if (jsonObject.has("confirmed") && !jsonObject.isNull("confirmed")
                ) {
                    countryObject.confirmed = jsonObject.getLong("confirmed");
                    globalTotal = globalTotal + countryObject.confirmed;
                }
                if (jsonObject.has("recovered") && !jsonObject.isNull("recovered")) {
                    countryObject.recovered = jsonObject.getLong("recovered");
                    globalRecovered = globalRecovered + countryObject.recovered;
                }
                if (jsonObject.has("deaths") && !jsonObject.isNull("deaths")) {
                    countryObject.deaths = jsonObject.getLong("deaths");
                    globalDeaths = globalDeaths + countryObject.deaths;
                }
                Log.d("SUCCESS", "JSON Object: " + "\ncountry: " + countryObject.country
                        + "\nconfirmed: " + countryObject.confirmed);
                countryList.add(countryObject);
                //covidtext.setText(countryObject.country);

            }
            countryList = sortdata(countryList, Country.CountryTotalComparator, stk);
            showdata(countryList, stk);
            txt1 = findViewById(R.id.txt1);
            txt2 = findViewById(R.id.txt2);
            txt3 = findViewById(R.id.txt3);

            txt1.setText(txt1.getText() + "\n\n " + String.valueOf(globalTotal));
            txt2.setText(txt2.getText() + "\n\n " + String.valueOf(globalDeaths));
            txt3.setText(txt3.getText() + "\n\n " + String.valueOf(globalRecovered));

        } catch (JSONException e) {
            Log.e("FAILED", "Json parsing error: " + e.getMessage());
        }

    }

    public void showdata(final ArrayList<Country> countryList, final TableLayout stk) {
        final ArrayList<Country> sortedlist = new ArrayList<Country>();
        stk.removeAllViews();
        TableRow tbrow0 = new TableRow(this);
        Button tv0 = new Button(this);
        tv0.setText("Country");
        tv0.setAllCaps(true);
        tv0.setTextColor(Color.WHITE);
        tv0.setGravity(Gravity.RIGHT);
        tv0.setTextSize(15);
        tv0.setId(0);
        tbrow0.addView(tv0);
        Button tv1 = new Button(this);
        tv1.setText(" Total Cases ");
        tv1.setAllCaps(true);
        tv1.setTextColor(Color.rgb(13, 54, 219));
        tv1.setGravity(Gravity.LEFT);
        tv1.setTextSize(15);
        tv1.setLinksClickable(true);
        tbrow0.addView(tv1);
        Button tv2 = new Button(this);
        tv2.setText(" Deaths ");
        tv2.setAllCaps(true);
        tv2.setTextColor(Color.RED);
        tv2.setGravity(Gravity.LEFT);
        tv2.setTextSize(15);
        tv2.setLinksClickable(true);
        tbrow0.addView(tv2);
        Button tv3 = new Button(this);
        tv3.setText(" Recovered ");
        tv3.setAllCaps(true);
        tv3.setTextColor(Color.GREEN);
        tv3.setGravity(Gravity.LEFT);
        tv3.setTextSize(15);
        tv3.setLinksClickable(true);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        for (int i = 0; i < countryList.size(); i++) {
            Country country = countryList.get(i);
            if (country.confirmed > 0) {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(country.country);
                t1v.setTextColor(Color.WHITE);
                t1v.setGravity(Gravity.RIGHT);
                t1v.setTextSize(15);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(String.valueOf(country.confirmed));
                t2v.setTextColor(Color.WHITE);
                t2v.setGravity(Gravity.RIGHT);
                t2v.setTextSize(15);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(String.valueOf(country.deaths));
                t3v.setTextColor(Color.WHITE);
                t3v.setGravity(Gravity.RIGHT);
                t3v.setTextSize(15);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(String.valueOf(country.recovered));
                t4v.setTextColor(Color.WHITE);
                t4v.setGravity(Gravity.RIGHT);
                t4v.setTextSize(15);
                tbrow.addView(t4v);
                stk.addView(tbrow);
            }
        }
    }

    public ArrayList<Country> sortdata(ArrayList<Country> countryList, Comparator<Country> csk, TableLayout stk) {
        ArrayList<String> sortedList = new ArrayList<String>();
        Collections.sort(countryList, csk);
        for (
                Country str : countryList) {
            sortedList.add(str.toString());
        }
        ArrayList<Country> sortedByConfirmed = new ArrayList<Country>();
        for (
                int i = 0; i < sortedList.size(); i++) {
            Country country = new Country();
            String str = sortedList.get(i);
            String[] strlist = str.split("/");
            for (int j = 0; j < strlist.length; j++) {
                if (j == 0) {
                    country.country = strlist[j];
                }
                if (j == 1) {
                    country.confirmed = (long) Float.parseFloat(strlist[j]);
                }
                if (j == 2) {
                    country.deaths = (long) Float.parseFloat(strlist[j]);
                }
                if (j == 3) {
                    country.recovered = (long) Float.parseFloat(strlist[j]);
                }
            }
            sortedByConfirmed.add(country);
        }
        return sortedByConfirmed;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TableRow tr = (TableRow) v.getParent();
            tv0clicked++;
            TableLayout stk = (TableLayout) findViewById(R.id.table_main);
            ArrayList<Country> sortedlist = new ArrayList<Country>();
            if (tv0clicked % 2 == 0) {
                sortedlist = sortdata(countryList, Country.CountryNameComparatorDsc, stk);
                showdata(sortedlist, stk);
            } else {

                sortedlist = sortdata(countryList, Country.CountryNameComparatorAsc, stk);
                showdata(sortedlist, stk);
            }
            ;
        }
    };

    private View.OnClickListener searchlistener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            editText = (EditText) findViewById(R.id.editText);
            number = Long.parseLong((editText.getText().toString()));
            if (editText.getText().toString() == null) {
                showdata(search(countryList, selectedcases, selectedoperator, 0), stk);
            } else {
                showdata(search(countryList, selectedcases, selectedoperator, number), stk);
            }
        }
    };

    private AdapterView.OnItemSelectedListener onitemclick1 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            dropdown1 = findViewById(R.id.spinner1);

            selectedoperator = dropdown1.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private AdapterView.OnItemSelectedListener onitemclick0 = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view,
                                   int position, long id) {
            dropdown = findViewById(R.id.spinner);
            selectedcases = dropdown.getSelectedItem().toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    public ArrayList<Country> search(@NotNull ArrayList<Country> countryList, String selectedcase, String selectedoperator, long number) {
        ArrayList<Country> newlist = new ArrayList<Country>();
        for (int i = 0; i < countryList.size(); i++) {
            Country country = countryList.get(i);
            if (selectedcase == "Total Cases" && selectedoperator == ">=") {
                if (country.confirmed >= number) {
                    newlist.add(country);
                }
            } else if (selectedcase == "Total Cases" && selectedoperator == "<=") {
                if (country.confirmed <= number) {
                    newlist.add(country);
                }
            } else if (selectedcase == "Deaths" && selectedoperator == "<=") {
                if (country.deaths <= number) {
                    newlist.add(country);
                }
            } else if (selectedcase == "Deaths" && selectedoperator == ">=") {
                if (country.deaths >= number) {
                    newlist.add(country);
                }
            } else if (selectedcase == "Recovered" && selectedoperator == ">=") {
                if (country.recovered >= number) {
                    newlist.add(country);
                }
            } else if (selectedcase == "Recovered" && selectedoperator == "<=") {
                if (country.recovered <= number) {
                    newlist.add(country);
                }
            } else {
                newlist.add(country);
            }
        }
        return (newlist);
    }


}

