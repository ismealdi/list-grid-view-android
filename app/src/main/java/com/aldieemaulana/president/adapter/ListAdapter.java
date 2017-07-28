package com.aldieemaulana.president.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aldieemaulana.president.App;
import com.aldieemaulana.president.R;
import com.aldieemaulana.president.activity.DetailActivity;
import com.aldieemaulana.president.model.President;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by aldieemaulana on 7/28/17.
 */

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private Context context;
    private List<President> presidentList;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView countryName, presidentName, birthDate, rate;
        public CircularImageView photo, flag;
        public RelativeLayout layout;

        public ViewHolder(View view) {
            super(view);

            layout = (RelativeLayout) view.findViewById(R.id.layout);
            countryName = (TextView) view.findViewById(R.id.country_name);
            presidentName = (TextView) view.findViewById(R.id.president_name);
            birthDate = (TextView) view.findViewById(R.id.birth_of_date);
            rate = (TextView) view.findViewById(R.id.rate);
            photo = (CircularImageView) view.findViewById(R.id.president);
            flag = (CircularImageView) view.findViewById(R.id.country);

            view.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                loadIntet(getAdapterPosition());
                }
            });

            layout.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    loadIntet(getAdapterPosition());
                }
            });
        }

    }

    private void loadIntet(int position) {
        Intent intent;
        intent = new Intent(context, DetailActivity.class);

        President president = presidentList.get(position);
        intent.putExtra("id", president.getId());
        intent.putExtra("name", president.getName());
        intent.putExtra("birth", president.getBirthOfDate());
        intent.putExtra("description", president.getDescription());
        intent.putExtra("period", president.getPeriod());
        intent.putExtra("photo", president.getPhoto());

        context.startActivity(intent);
    }

    public ListAdapter(Context context, List<President> presidentList) {
        this.context = context;
        this.presidentList = presidentList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_style, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        President president = presidentList.get(position);
        holder.presidentName.setText(president.getName());
        holder.countryName.setText(context.getResources().getString(R.string.president_of) + " " + president.getCountry());
        holder.birthDate.setText(changetToBirth(president.getBirthOfDate()));

        Picasso.with(context).load(App.URL + "files/photos/" + president.getPhoto()).memoryPolicy(MemoryPolicy.NO_CACHE).into(holder.photo);
        Picasso.with(context).load(App.URL + "files/flags/" + president.getPhoto()).into(holder.flag);

    }

    @Override
    public int getItemCount() {
        return presidentList.size();
    }

    public void clear() {
        presidentList.clear();
        notifyDataSetChanged();
    }

    public void addAll(List<President> list) {
        presidentList.addAll(list);
        notifyDataSetChanged();
    }

    public void add(President president) {
        presidentList.add(president);
        notifyDataSetChanged();
    }

    protected String changetToBirth(String s) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterNew = new SimpleDateFormat("MMMM d, yyyy", Locale.US);

        try {
            Date date = formatter.parse(s);
            return formatterNew.format(date);

        } catch (ParseException e) {
            e.printStackTrace();

            return s;
        }
    }


}
