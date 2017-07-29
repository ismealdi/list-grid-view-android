package com.aldieemaulana.president.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.aldieemaulana.president.App;
import com.aldieemaulana.president.R;
import com.aldieemaulana.president.adapter.ReleatedAdapter;
import com.aldieemaulana.president.api.PresidentApi;
import com.aldieemaulana.president.model.President;
import com.aldieemaulana.president.response.PresidentResponse;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

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

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.about) TextView about;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.birth_of_date) TextView birth;
    @BindView(R.id.period) TextView term;
    @BindView(R.id.photo) ImageView photo;
    @BindView(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.nestedListview) NestedScrollView nestedListview;

    private List<President> presidentList;
    private ReleatedAdapter presidentListAdapter;
    private LinearLayoutManager layoutManager;
    private Intent data;

    @Override
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        data = getIntent();

        title.setText(data.getStringExtra("name"));
        about.setText(getResources().getString(R.string.about) + " " + data.getStringExtra("country"));
        description.setText(data.getStringExtra("description"));
        birth.setText(data.getStringExtra("birth"));
        term.setText(data.getStringExtra("period") + " - NOW");

        Picasso.with(this).load(App.URL + "files/photos/" + data.getStringExtra("photo")).memoryPolicy(MemoryPolicy.NO_CACHE).into(photo);

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        swipeContainer.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresident();
            }
        });

        getPresident();

        presidentList = new ArrayList<>();
        presidentListAdapter = new ReleatedAdapter(this, presidentList);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
        recyclerView.setVerticalScrollBarEnabled(false);
        recyclerView.setHorizontalScrollBarEnabled(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(presidentListAdapter);

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

        Call<PresidentResponse> call = service.getReleateds(data.getIntExtra("id", 0));

        call.enqueue(new Callback<PresidentResponse>() {
            @Override
            public void onResponse(Call<PresidentResponse> call, Response<PresidentResponse> response) {

                if (response.raw().isSuccessful()) {
                    presidentList.clear();
                    presidentList.addAll(response.body().getPresident());
                    presidentListAdapter.notifyDataSetChanged();


                    recyclerView.getViewTreeObserver().addOnPreDrawListener(
                            new ViewTreeObserver.OnPreDrawListener() {
                                @Override
                                public boolean onPreDraw() {
                                    recyclerView.getViewTreeObserver().removeOnPreDrawListener(this);

                                    for (int i = 0; i < recyclerView.getChildCount(); i++) {
                                        View v = recyclerView.getChildAt(i);
                                        v.setAlpha(0.0f);
                                        v.animate().alpha(1.0f)
                                                .setDuration(300)
                                                .setStartDelay(i * 50)
                                                .start();
                                    }

                                    return true;
                                }
                            });
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

    protected void dialogVote(String name) {
        final Dialog dialog = new Dialog(DetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_vote);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final TextView num = (TextView) dialog.findViewById(R.id.num);

        Button plus = (Button) dialog.findViewById(R.id.plus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int n = Integer.parseInt(num.getText().toString());
                if(n < 5) {
                    n+=1;
                    num.setText("" + String.valueOf(n));
                }
            }
        });

        Button minus = (Button) dialog.findViewById(R.id.minus);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                int n = Integer.parseInt(num.getText().toString());
                if(n > 1) {
                    n-=1;
                    num.setText("" + String.valueOf(n));
                }
            }
        });


        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText(name);

        dialog.setTitle("");
        dialog.show();
    }

    @OnClick(R.id.buttonDecision)
    protected void decision(View view) {
        dialogVote(getResources().getString(R.string.decision));
    }

    @OnClick(R.id.buttonFirmness)
    protected void firmness(View view) {
        dialogVote(getResources().getString(R.string.firmness));
    }

    @OnClick(R.id.buttonLeadership)
    protected void leadership(View view) {
        dialogVote(getResources().getString(R.string.leadership));
    }

    @OnClick(R.id.buttonLeft)
    protected void backToMain(View view) {
        super.onBackPressed();

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(DetailActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
