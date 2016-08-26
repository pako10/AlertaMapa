package com.example.pakoandrade.alertamapa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import cz.msebera.android.httpclient.Header;

public class AmberActivity extends AppCompatActivity {

    String idAlert = "";
    Button btAtender;
    ListView listAlert;
    String lat = "";
    String longs = "";

    String lugar,contacto,age,fisico;
    String name;
    String vestimenta;

    JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amber);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        listAlert = (ListView) findViewById(R.id.listAmber);



        listAlert.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                readAlertId(position);
                for (int n = 0; n < listAlert.getChildCount(); n++) {
                    if(position == n ){
                        listAlert.getChildAt(n).setBackgroundColor(getResources().getColor(R.color.colorOnListItemSelected));
                    }else{
                        listAlert.getChildAt(n).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
                Intent i = new Intent(AmberActivity.this, AmberDetailActivity.class);
                i.putExtra("lugar",lugar);
                i.putExtra("contacto",contacto);
                i.putExtra("edad",age);
                i.putExtra("fisico",fisico);
                i.putExtra("nombre",name);
                i.putExtra("vestimenta",vestimenta);
                startActivity(i);


            }
        });

        // readAlert();
        callAsynchronousTask();

    }


    public void readAlert(){

        final ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        final ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);

        listAlert.setAdapter(adaptador);

        AsyncHttpClient client = new AsyncHttpClient();

        client.get("http://wsars.cloudapp.net/wsARS.svc/AlertasAmber", new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    jsonArray = (JSONArray) jsonObject.opt("d");

                    for(int i = 0;i < jsonArray.length();i++){
                        JSONObject jsonObjeto = jsonArray.getJSONObject(i);
                        String idAlerta = jsonObjeto.optString("LugarAmber");


                        listAlert.setAdapter(adaptador);
                        adaptador.add(idAlerta);
                    }

                }catch (Exception e){

                }
            }
        });
    }

    public void readAlertId(final int value) {


        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObjeto = jsonArray.getJSONObject(i);

                if (value == i) {
                    contacto = jsonObjeto.optString("ContactoAmber");
                    age = jsonObjeto.optString("EdadAmber");
                    fisico =   jsonObjeto.optString("FisicosAmber");
                    lugar =   jsonObjeto.optString("LugarAmber");
                    name =   jsonObjeto.optString("NombreAmber");
                    vestimenta =   jsonObjeto.optString("VestimentaAmber");
                    Toast.makeText(AmberActivity.this, contacto + age, Toast.LENGTH_SHORT).show();
                    //Log.d("Numero de alerta ", idAlert);
                }
            }

        } catch (Exception e) {

        }
    }

    public void changeStatus(){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("idAlerta",idAlert);

        client.get("http://wsars.cloudapp.net/wsARS.svc/AtenderAlerta", params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(AmberActivity.this, "Alerta cambiada", Toast.LENGTH_SHORT).show();
                readAlert();
            }
        });
    }

    public void callAsynchronousTask() {
        final Handler handler = new Handler();
        Timer timer = new Timer();
        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            readAlert();
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, 50000); //execute in every 50000 ms
    }


}
