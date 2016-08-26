package com.example.pakoandrade.alertamapa;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class AmberDetailActivity extends AppCompatActivity {

    TextView tvName,tvAge,tvClothes,tvFisico,tvPlace,tvContact;
    String name,age,clothes,fisico,place,contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amber_detail);

        tvName = (TextView) findViewById(R.id.tvName);
        tvAge = (TextView) findViewById(R.id.tvAge);
        tvClothes = (TextView) findViewById(R.id.tvClothes);
        tvFisico = (TextView) findViewById(R.id.tvPerson);
        tvPlace = (TextView) findViewById(R.id.tvPlace);
        tvContact = (TextView) findViewById(R.id.tvData);

        Intent i = getIntent();
        Bundle extra = i.getExtras();

        if(extra != null) {
            place = (String) extra.get("lugar");
            contact = (String) extra.get("contacto");
            age = (String) extra.get("edad");
            fisico = (String) extra.get("fisico");
            name = (String) extra.get("nombre");
            clothes = (String) extra.get("vestimenta");
        }else{
            Toast.makeText(this, "No se reciben valores", Toast.LENGTH_LONG).show();
        }

        tvName.setText(name);
        tvPlace.setText(place);
        tvContact.setText(contact);
        tvAge.setText(age);
        tvFisico.setText(fisico);
        tvClothes.setText(clothes);

    }


}
