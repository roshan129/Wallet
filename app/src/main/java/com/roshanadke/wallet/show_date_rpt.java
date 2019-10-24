package com.roshanadke.wallet;


import android.app.DatePickerDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class show_date_rpt extends Fragment {
    String str = "Select Date";
    double total_amt;
    Cursor c;
    ImageView img_date;
    Button btn_show_date;
    TextView txt_date2,txt_total_amt,txt_total_text;
    int mYear, mMonth, mDay;
    recyclerAdapter adapter_recycler_object;
    LinearLayout line1;
    ArrayList<HashMap<String, String>> arrayList;
    RecyclerView.LayoutManager layoutManager_re;
    RecyclerView recyclerView_report_list;
    private DatabaseHelper databaseHelper;

    public show_date_rpt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_date_rpt, container, false);

        arrayList = new ArrayList<>();
        recyclerView_report_list = view.findViewById(R.id.recycler_list2);
        layoutManager_re = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_report_list.setLayoutManager(layoutManager_re);
        adapter_recycler_object = new recyclerAdapter(getActivity(), arrayList);
        recyclerView_report_list.setAdapter(adapter_recycler_object);

        txt_total_amt=view.findViewById(R.id.txt_total_amt);
        txt_total_text=view.findViewById(R.id.txt_total_text);

        databaseHelper = new DatabaseHelper(getActivity());
        txt_date2 = view.findViewById(R.id.txt_date2);
        line1= view.findViewById(R.id.line1);
        btn_show_date = view.findViewById(R.id.btn_report2);
        /*Typeface typeface=Typeface.createFromAsset(getActivity().getAssets(),"thin_text.ttf");
        btn_show_date.setTypeface(typeface);*/
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        img_date = view.findViewById(R.id.img_date);

        line1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        txt_date2.setText(dayOfMonth + "-" + month + "-" + year);
                        str = txt_date2.getText().toString();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });
        /*img_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        txt_date2.setText(dayOfMonth + "-" + month + "-" + year);
                        str = txt_date2.getText().toString();
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();

            }

        });*/

        btn_show_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (str == "Select Date") {
                    Toast.makeText(getActivity(), "Select Date", Toast.LENGTH_SHORT).show();
                } else {
                    adapter2();
                }

            }
        });

       return view;
    }

    public void adapter2() {
        txt_total_amt.setText("");
        txt_total_text.setVisibility(View.GONE);
        txt_total_amt.setVisibility(View.GONE);
        total_amt=0.0;
        arrayList.clear();
        c = databaseHelper.getData2(str);
        if (c.getCount() != 0) {
            c.moveToFirst();
            do {
                HashMap<String, String> map = new HashMap<String, String>();

                String id = c.getString(0);
                String date = c.getString(1);
                String category = c.getString(2);
                String amt = c.getString(4);

                map.put("id", id);
                map.put("date", date);
                map.put("category", category);
                map.put("amt", amt);
                arrayList.add(map);
                Log.d(TAG, "ProgramAdapter: " + arrayList);
                total_amt=total_amt+Double.parseDouble(amt);
            } while (c.moveToNext());
        } else {
            Toast.makeText(getActivity(), "No Data found for list ...", Toast.LENGTH_SHORT).show();
        }

        if (adapter_recycler_object != null) {
            adapter_recycler_object.notifyDataSetChanged();
            System.out.println("Adapter " + adapter_recycler_object.toString());
        }
        if(total_amt!=0)
        {
            txt_total_amt.setText(""+total_amt);
            txt_total_text.setVisibility(View.VISIBLE);
            txt_total_amt.setVisibility(View.VISIBLE);
        }

    }

    public class recyclerAdapter extends RecyclerView.Adapter<recyclerAdapter.ProgramViewHolder> {

        Context context;
        ArrayList<HashMap<String, String>> expense_list;

        public recyclerAdapter(Context context, ArrayList<HashMap<String, String>> exp_list) {
            this.expense_list = exp_list;
            this.context = context;
        }

        @NonNull
        @Override
        public ProgramViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            View view = inflater.inflate(R.layout.wallet_list, viewGroup, false);
            return new ProgramViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ProgramViewHolder programViewHolder, int i) {
            programViewHolder.exp_id.setText(expense_list.get(i).get("id"));
            programViewHolder.exp_date.setText(expense_list.get(i).get("date"));
            programViewHolder.exp_cat.setText(expense_list.get(i).get("category"));
            programViewHolder.exp_amt.setText(expense_list.get(i).get("amt"));
        }

        @Override
        public int getItemCount() {
            return expense_list.size();
        }

        public class ProgramViewHolder extends RecyclerView.ViewHolder {
            TextView exp_id, exp_date, exp_cat, exp_amt;
            TableLayout layout_grid_position;

            public ProgramViewHolder(@NonNull View itemView) {
                super(itemView);
                this.layout_grid_position = itemView.findViewById(R.id.table_grid_position);
                this.exp_id =  itemView.findViewById(R.id.list_id);
                this.exp_date =  itemView.findViewById(R.id.list_date);
                this.exp_cat =  itemView.findViewById(R.id.list_category);
                this.exp_amt =  itemView.findViewById(R.id.list_amt);

            }
        }
    }
}
