package com.roshanadke.wallet;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class add_expense extends AppCompatActivity {
    LinearLayout btn_select_date;
    Spinner spin_cate;
    TextView txt_sel_date;
    EditText txt_amount, edt_note;
    ImageView imageView;
    Button btn_add_exp2;
    private DatabaseHelper databaseHelper;
    int mYear, mMonth, mDay;
    String str1, str_spin, str3, str4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        btn_add_exp2 = findViewById(R.id.btn_add_exp2);
        txt_amount = findViewById(R.id.edt_amt);
        edt_note = findViewById(R.id.edt_note);
        txt_sel_date = findViewById(R.id.txt_date1);
        spin_cate = findViewById(R.id.spinner_category);
        btn_select_date = findViewById(R.id.btn_select_date);

        databaseHelper = new DatabaseHelper(this);

        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        btn_select_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(add_expense.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txt_sel_date.setText(dayOfMonth + "-" + month + "-" + year);
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        /*imageView=findViewById(R.id.img_date);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txt_sel_date.setText(dayOfMonth+"-"+month+"-"+year);
                    }
                },mYear,mMonth,mDay);
                datePickerDialog.show();
            }
        });*/

        List<String> list = new ArrayList<>();
        list.add("Select Category");
        list.add("Food");
        list.add("Travel");
        list.add("Clothes");
        list.add("Mobile");
        list.add("Others");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spin_cate.setAdapter(dataAdapter);
        spin_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                str_spin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_add_exp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                str1 = txt_sel_date.getText().toString();

                str3 = edt_note.getText().toString();
                str4 = txt_amount.getText().toString();

                if (str1.matches("")) {
                    Toast.makeText(add_expense.this, "Select Date", Toast.LENGTH_SHORT).show();
                } else if (str_spin.matches("Select Category")) {
                    Toast.makeText(add_expense.this, "Select Category", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(str3)) {
                    edt_note.setError("Enter Note");
                } else if (TextUtils.isEmpty((str4))) {
                    txt_amount.setError("Enter amount");
                } else {

                    boolean result = databaseHelper.insertData(str1, str_spin, str3, str4);
                    if (result) {
                        txt_sel_date.setText("");
                        spin_cate.setSelection(0);
                        edt_note.setText("");
                        txt_amount.setText("");
                        Toast.makeText(getApplicationContext(), "Record Added Successfully", Toast.LENGTH_LONG).show();
                    } else
                        Toast.makeText(getApplicationContext(), "Error..", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
