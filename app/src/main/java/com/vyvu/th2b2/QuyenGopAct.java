package com.vyvu.th2b2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.vyvu.th2b2.dao.QuyenGopDAO;
import com.vyvu.th2b2.model.QuyenGop;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuyenGopAct extends AppCompatActivity {
    private EditText id, name, price;
    private TextView date;
    private Spinner city;
    private Button btnAdd, btnUpdate, btnDelete, btnClose;
    private QuyenGopDAO quyenGopDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quyengop);
        quyenGopDAO =new QuyenGopDAO(this, "quyengop");
        initComponent();
    }

    private void initComponent() {
        id=findViewById(R.id.txtId);
        name=findViewById(R.id.txtName);
        city =findViewById(R.id.txtCity);
        price=findViewById(R.id.txtPrice);
        date=findViewById(R.id.txtDate);
        btnAdd=findViewById(R.id.btadd);
        btnUpdate=findViewById(R.id.btupdate);
        btnDelete=findViewById(R.id.btdelete);
        btnClose=findViewById(R.id.btclose);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> picker=MaterialDatePicker.Builder
                        .datePicker()
                        .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                        .build();
                picker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        date.setText(new SimpleDateFormat("dd/MM/yyyy").format(new Date(selection)).toString());
                    }
                });
                picker.show(getSupportFragmentManager(), "DP");
            }
        });

        ArrayAdapter<CharSequence> cityadap=ArrayAdapter.createFromResource(this, R.array.cities, android.R.layout.simple_spinner_dropdown_item);
        cityadap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(cityadap);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        id.setEnabled(false);
        QuyenGop p= (QuyenGop) getIntent().getSerializableExtra("quyengop");
        if(p!=null){
            btnAdd.setEnabled(false);

            id.setText(p.getId()+"");
            name.setText(p.getName());
            city.setSelection(p.getCity());
            price.setText(p.getPrice()+"");
            date.setText(p.getDate());
        }else{
            btnDelete.setEnabled(false);
            btnUpdate.setEnabled(false);
        }

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quyenGopDAO.add(new QuyenGop(
                        name.getText().toString(),
                        city.getSelectedItemPosition(),
                        Double.parseDouble(price.getText().toString()),
                        date.getText().toString()
                ));
                finish();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quyenGopDAO.update(new QuyenGop(
                        Integer.parseInt(id.getText().toString()),
                        name.getText().toString(),
                        city.getSelectedItemPosition(),
                        Double.parseDouble(price.getText().toString()),
                        date.getText().toString()
                ));
                finish();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quyenGopDAO.delete(p);
                finish();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}