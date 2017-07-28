package com.aldieemaulana.president.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.aldieemaulana.president.App;
import com.aldieemaulana.president.R;
import com.aldieemaulana.president.adapter.ListAdapter;
import com.aldieemaulana.president.model.President;
import com.aldieemaulana.president.api.PresidentApi;
import com.aldieemaulana.president.response.PresidentResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recylerView) RecyclerView recylerView;

    private List<President> presidentList;
    private ListAdapter presidentListAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresident();
            }
        });

        getPresident();

        presidentList = new ArrayList<>();
        presidentListAdapter = new ListAdapter(this, presidentList);
        recylerView.setLayoutManager(layoutManager);
        recylerView.setNestedScrollingEnabled(false);
        recylerView.setHorizontalScrollBarEnabled(true);
        recylerView.setItemAnimator(new DefaultItemAnimator());
        recylerView.setAdapter(presidentListAdapter);
    }

    private void getPresident() {
        swipeContainer.setRefreshing(true);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                    .addHeader("Content-Type", "application/json")
                .build();

                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(App.API)
            .addConverterFactory(GsonConverterFactory.create()).build();

        PresidentApi service = retrofit.create(PresidentApi.class);

        Call<PresidentResponse> call = service.getPresident();

        call.enqueue(new Callback<PresidentResponse>() {
            @Override
            public void onResponse(Call<PresidentResponse> call, Response<PresidentResponse> response) {

                if (response.raw().isSuccessful()) {
                    presidentList.clear();
                    presidentList.addAll(response.body().getPresident());
                    presidentListAdapter.notifyDataSetChanged();
                }

                Log.i("aldieemaulana", "aldieemaulana result: " + response.body().getTotal());

                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PresidentResponse> call, Throwable t) {
                presidentListAdapter.notifyDataSetChanged();

                Log.i("aldieemaulana", "aldieemaulana error: " + t.getMessage());

                swipeContainer.setRefreshing(false);
            }
        });
    }

    @OnClick(R.id.buttonLeft)
    protected void backToMain(View view) {
        super.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        Log.i("aldieemaulana", "aldieemaulana action: exit");
    }

}
