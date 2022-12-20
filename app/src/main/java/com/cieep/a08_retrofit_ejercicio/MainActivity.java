package com.cieep.a08_retrofit_ejercicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cieep.a08_retrofit_ejercicio.Adapters.UsersAdapter;
import com.cieep.a08_retrofit_ejercicio.conexiones.ApiConexiones;
import com.cieep.a08_retrofit_ejercicio.conexiones.RetrofitObject;
import com.cieep.a08_retrofit_ejercicio.modelos.DataItem;
import com.cieep.a08_retrofit_ejercicio.modelos.Response;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView contenedor;
    private Button btnPage1;
    private Button btnPage2;

    private UsersAdapter adapter;
    private RecyclerView.LayoutManager lm;

    private ArrayList<DataItem> users;

    private Retrofit retrofit;
    private ApiConexiones api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inicializaVistas();
        // RecyclerView
        users = new ArrayList<>();
        adapter = new UsersAdapter(users, R.layout.user_view_holder, this);
        lm = new LinearLayoutManager(this);
        contenedor.setAdapter(adapter);
        contenedor.setLayoutManager(lm);

        // RETROFIT
        retrofit = RetrofitObject.getConexion();
        api = retrofit.create(ApiConexiones.class);
        downloadUsers("1");
    }

    public void clickPaginas(View view) {
        Button btn = (Button) view;
        String page = btn.getText().toString();
        downloadUsers(page);
    }


    private void downloadUsers(String page) {
        Call<Response> doGetUsers = api.getUsers(page);

        doGetUsers.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK && response.body() != null) {
                    adapter.notifyItemRangeRemoved(0, users.size());
                    users.clear();
                    Response response1 = response.body();
                    ArrayList<DataItem> temp = (ArrayList<DataItem>) response1.getData();
                    users.addAll(temp);
                    adapter.notifyItemRangeInserted(0, temp.size());
                    if (response1.getPage() == 1) {
                        btnPage1.setEnabled(false);
                        btnPage2.setEnabled(true);
                    }
                    else {
                        btnPage1.setEnabled(true);
                        btnPage2.setEnabled(false);
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {

            }
        });
    }

    private void inicializaVistas() {
        contenedor = findViewById(R.id.contenedor);
        btnPage1 = findViewById(R.id.btnPage1);
        btnPage2 = findViewById(R.id.btnPage2);
    }

}