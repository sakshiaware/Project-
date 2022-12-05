package com.smc.smcsystem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.os.Bundle;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

public class AdminReport extends AppCompatActivity {
    //    EditText input_minimal,
//            input_maximal;
//    Button btn_minimal,
//            btn_maximal,
//            cari;
//    ArrayList<ModelPost> list = new ArrayList<>();
//    AdapterItem adapterItem;
//    RecyclerView recyclerView;
//    DatabaseReference database = FirebaseDatabase.getInstance().getReference();
//    FloatingActionButton fab_add;
//    AlertDialog builderAlert;
//    Context context;
//    LayoutInflater layoutInflater;
//    View showInput;
//    Calendar calendar = Calendar.getInstance();
//    Locale id = new Locale("in", "ID");
//    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-mm-yyyy", id);
//    Date date_minimal;
//    Date date_maximal;
    FirebaseDatabase database;
    DatabaseReference ref;
    TextView txt;
    int totalpostcount = 0, complcnt = 0;
    int comper;
    int rem;
    int d;
    TextView tvtotalcom, tvPython, tvCPP, tvtotalccomcom,tvtotalcompen;

    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_report);

        database = FirebaseDatabase.getInstance();
        txt = (TextView) findViewById(R.id.ttlpost);
        tvtotalcom = (TextView) findViewById(R.id.tvtotalnoofcom);
        tvtotalccomcom = (TextView) findViewById(R.id.tvtotalnoofcomcomplited);
        tvtotalcompen = (TextView) findViewById(R.id.tvtotalnoofcompending);


        tvPython = findViewById(R.id.tvPython);
        tvCPP = findViewById(R.id.tvCPP);
        ref = database.getReference("Posts");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    if (ds.child("status").getValue().toString().equals("Completed")) {
                        complcnt = complcnt + 1;
                    }
                    totalpostcount = totalpostcount + 1;
                }


                d = (complcnt * 100) / totalpostcount;
              // txt.setText(d);
                // double t=d*100;
                // int j= (int) Math.round(t);
                // Log.d("j", String.valueOf(j));
                // Log.d("t", String.valueOf());
                tvPython.setText(Integer.toString(d));
               // txt.setText(Integer.toString(d));
                tvtotalcom.setText(Integer.toString(totalpostcount));
                tvtotalccomcom.setText(Integer.toString(complcnt));
                tvtotalcompen.setText(Integer.toString(totalpostcount-complcnt));

                tvCPP.setText(Integer.toString(100 - d));
                //comper=Integer.parseInt(tvPython.getText().toString());
                //rem=Integer.parseInt(tvCPP.getText().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        pieChart = findViewById(R.id.piechart);
        pieChart.addPieSlice(
                new PieModel(

                        "Completed",
                        40,
                      // Integer.parseInt(txt.getText().toString()),
                        Color.parseColor("#66BB6A")));

        pieChart.addPieSlice(

                new PieModel(

                        "Pending",
                          60,
                           //100-Integer.parseInt(txt.getText().toString()),
                           // rem,
                        //Integer.parseInt(tvCPP.getText().toString()),

                        Color.parseColor("#EF5350")));

//


        pieChart.startAnimation();


        //setData();

    }
}


   // private void setData()

   // {

//        pieChart.addPieSlice(
//
//                new PieModel(
//
//                        "Completed",
//                        Integer.parseInt(tvPython.getText().toString()),
//
//                        Color.parseColor("#66BB6A")));
//
//        pieChart.addPieSlice(
//
//                new PieModel(
//
//                        "Pending",
//
//                        Integer.parseInt(tvCPP.getText().toString()),
//
//                        Color.parseColor("#EF5350")));
//
////
//
//
//
//
//        pieChart.startAnimation();


//         context = this;
//        fab_add = findViewById(R.id.fab_add);
//        cari = findViewById(R.id.cari);
//        input_minimal = findViewById(R.id.input_minimal);
//        input_maximal = findViewById(R.id.input_maximal);
//        btn_minimal = findViewById(R.id.btn_minimal);
//        btn_maximal = findViewById(R.id.btn_maximal);
//        recyclerView = findViewById(R.id.recyclerView);
//
//        btn_minimal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(year, month, dayOfMonth);
//                        input_minimal.setText(simpleDateFormat.format(calendar.getTime()));
//                        date_minimal = calendar.getTime();
//
//                        String input1 = input_minimal.getText().toString();
//                        String input2 = input_maximal.getText().toString();
//                        if (input1.isEmpty() && input2.isEmpty()){
//                            cari.setEnabled(false);
//                        }else {
//                            cari.setEnabled(true);
//                        }
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//        });
//
//        btn_maximal.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(year, month, dayOfMonth);
//                        input_maximal.setText(simpleDateFormat.format(calendar.getTime()));
//                        date_maximal = calendar.getTime();
//
//                        String input1 = input_maximal.getText().toString();
//                        String input2 = input_minimal.getText().toString();
//                        if (input1.isEmpty() && input2.isEmpty()){
//                            cari.setEnabled(false);
//                        }else {
//                            cari.setEnabled(true);
//                        }
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//        });
//
//        cari.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Query query = database.child("Posts").orderByChild("ptime").startAt(date_minimal.getDate()).endAt(date_maximal.getDate());
//                query.addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        showLisener(snapshot);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//            }
//        });
//
//     /*  fab_add.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                inputData();
//            }
//        });*/
//
//       // showData();
//    }
//
//    EditText et_nama, tgl_daftar,
//            et_jurusan;
//    Button btnDateDaftar,
//            simpanData;
//    RadioGroup rb_group;
//    RadioButton radioButton;
//    Date tgl_daftar_date;
//
//    /*private void inputData() {
//        builderAlert = new AlertDialog.Builder(context).create();
//        layoutInflater = getLayoutInflater();
//        showInput = layoutInflater.inflate(R.layout.input_layout, null);
//        builderAlert.setView(showInput);
//
//        et_nama = showInput.findViewById(R.id.et_nama);
//        tgl_daftar = showInput.findViewById(R.id.tgl_daftar);
//        et_jurusan = showInput.findViewById(R.id.et_jurusan);
//        btnDateDaftar = showInput.findViewById(R.id.btnDateDaftar);
//        simpanData = showInput.findViewById(R.id.simpanData);
//        rb_group = showInput.findViewById(R.id.rb_group);
//        builderAlert.show();
//
//        simpanData.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String nama = et_nama.getText().toString();
//                String jurusan = et_jurusan.getText().toString();
//                String tgl = tgl_daftar.getText().toString();
//                if (nama.isEmpty()) {
//                    et_nama.setError("Data tidak boleh kosong");
//                    et_nama.requestFocus();
//                } else if (jurusan.isEmpty()) {
//                    et_jurusan.setError("Data tidak boleh kosong");
//                    et_jurusan.requestFocus();
//                } else if (tgl.isEmpty()) {
//                    tgl_daftar.setError("Data tidak boleh kosong");
//                    tgl_daftar.requestFocus();
//                } else {
//                    int selected = rb_group.getCheckedRadioButtonId();
//                    radioButton = showInput.findViewById(selected);
//
//                    database.child("user").child(nama).setValue(new dataUser(
//                            nama,
//                            radioButton.getText().toString(),
//                            jurusan,
//                            tgl_daftar_date.getTime()
//                    )).addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void aVoid) {
//                            Toast.makeText(context, "Data berhasil ditambahkan", Toast.LENGTH_SHORT).show();
//                            builderAlert.dismiss();
//                        }
//                    }).addOnFailureListener(new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//                            builderAlert.dismiss();
//                        }
//                    });
//
//                }
//            }
//        });
//
//        btnDateDaftar.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
//                    @Override
//                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        calendar.set(year, month, dayOfMonth);
//                        tgl_daftar.setText(simpleDateFormat.format(calendar.getTime()));
//                        tgl_daftar_date = calendar.getTime();
//                    }
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
//                datePickerDialog.show();
//            }
//
//        });
//
//    }*/
//
//   private void showData() {
//        database.child("Posts").addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                showLisener(snapshot);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }
//
//    //@RequiresApi(api = Build.VERSION_CODES.N)
//    private void showLisener(DataSnapshot snapshot) {
//        list.clear();
//        for (DataSnapshot item : snapshot.getChildren()) {
//            ModelPost user = item.getValue(ModelPost.class);
//            list.add(user);
//           // list.add(modelPost);
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                adapterItem = new AdapterItem(AdminReport.this, list);
//            }
//            recyclerView.setAdapter(adapterItem);
//
//        }
//       // if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//          //  adapterItem = new AdapterItem(getAct,list);
//       // }
//        //recyclerView.setAdapter(adapterItem);
//
//    }
//}
//
//
//
//

