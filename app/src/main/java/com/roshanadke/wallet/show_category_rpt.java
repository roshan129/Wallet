package com.roshanadke.wallet;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class show_category_rpt extends Fragment {

    String str;
    Spinner spin;
    Cursor c;
    double total_amt2;
    recyclerAdapter adapter_recycler_object;
    TextView txt_total_text2, txt_total_amt2;
    ArrayList<HashMap<String, String>> arrayList;
    RecyclerView.LayoutManager layoutManager_re;
    RecyclerView recyclerView_report_list;
    private DatabaseHelper databaseHelper;
    Button btn_s;

    public show_category_rpt() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_category_rpt, container, false);

        arrayList = new ArrayList<HashMap<String, String>>();
        recyclerView_report_list = (RecyclerView) view.findViewById(R.id.recycler_list);
        layoutManager_re = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView_report_list.setLayoutManager(layoutManager_re);
        adapter_recycler_object = new recyclerAdapter(getActivity(), arrayList);
        recyclerView_report_list.setAdapter(adapter_recycler_object);

        txt_total_amt2 = view.findViewById(R.id.txt_total_amt2);
        txt_total_text2 = view.findViewById(R.id.txt_total_text2);

        databaseHelper = new DatabaseHelper(getActivity());
        spin = (Spinner) view.findViewById(R.id.spinner1);
        List<String> list = new ArrayList<String>();
        list.add("Select Category");
        list.add("Food");
        list.add("Travel");
        list.add("Clothes");
        list.add("Mobile");
        list.add("Others");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(dataAdapter);

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                str = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btn_s = (Button) view.findViewById(R.id.btn_report1);

        btn_s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (str.equals("Select Category")) {
                    Toast.makeText(getActivity(), "Select Category", Toast.LENGTH_SHORT).show();
                } else {
                    adapter1();
                }
            }
        });
        return view;
    }

    public void adapter1() {
        txt_total_amt2.setText("");
        txt_total_text2.setVisibility(View.GONE);
        txt_total_amt2.setVisibility(View.GONE);
        total_amt2 = 0.0;
        arrayList.clear();
        c = databaseHelper.getData(str);
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
                total_amt2 = total_amt2 + Double.parseDouble(amt);
            } while (c.moveToNext());
        } else {
            Toast.makeText(getActivity(), "No Data found for list ...", Toast.LENGTH_SHORT).show();
        }

        if (adapter_recycler_object != null) {
            adapter_recycler_object.notifyDataSetChanged();
            System.out.println("Adapter " + adapter_recycler_object.toString());
        }

        if (total_amt2 != 0) {
            txt_total_amt2.setText("" + total_amt2);
            txt_total_text2.setVisibility(View.VISIBLE);
            txt_total_amt2.setVisibility(View.VISIBLE);
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
                this.exp_id = itemView.findViewById(R.id.list_id);
                this.exp_date = itemView.findViewById(R.id.list_date);
                this.exp_cat = itemView.findViewById(R.id.list_category);
                this.exp_amt = itemView.findViewById(R.id.list_amt);
            }
        }
    }

}
