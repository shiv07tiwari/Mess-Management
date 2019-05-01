package com.example.admin.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import com.example.admin.R;
import com.example.admin.networking.APIClient;
import com.example.admin.networking.RetrofitService;
import com.example.admin.objects.Rebate;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class RebateAdapter extends RecyclerView.Adapter<RebateAdapter.MyViewHolder> {

    private ArrayList<Rebate> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mRoll;
        public TextView mFrom;
        public TextView mTo;
        public TextView mAmount;
        public CircularProgressButton mVerify;

        public MyViewHolder(View v) {
            super(v);

            mRoll = itemView.findViewById(R.id.rebate_roll);
            mFrom = itemView.findViewById(R.id.rebate_from);
            mTo = itemView.findViewById(R.id.rebate_to);
            mAmount = itemView.findViewById(R.id.rebate_amount);
            mVerify = itemView.findViewById(R.id.rebate_button_verify);
            //mReject = itemView.findViewById(R.id.unver_button_reject);

        }
    }

    public RebateAdapter(ArrayList<Rebate> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public RebateAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                             int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mess_rebate_item, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {


        holder.mRoll.setText("Roll Number : "+mDataset.get(position).getName());
        holder.mTo.setText("End Date : "+String.valueOf(mDataset.get(position).getToDate()));
        int amount = (mDataset.get(position).getFromDate()-mDataset.get(position).getToDate())*600;
        holder.mFrom.setText(String.valueOf("Start Date : "+mDataset.get(position).getFromDate()));

        holder.mAmount.setText("Total Amount : "+String.valueOf(amount));

        holder.mVerify.doneLoadingAnimation(mContext.getResources().getColor(R.color.green), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_done_white_48dp));
        holder.mVerify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RetrofitService apiService =
                        APIClient.getSOSClient().create(RetrofitService.class);
                Rebate r = new Rebate(mDataset.get(position).getName(),mDataset.get(position).getFromDate(),mDataset.get(position).getToDate());
                Call<String> call = apiService.verifyRebate(r);
                holder.mVerify.startAnimation();

                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            holder.mVerify.doneLoadingAnimation(mContext.getResources().getColor(R.color.green), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_done_white_48dp));
                            Log.e("log", call.request().url().toString());
                            Toast.makeText(mContext,"Rebate Approved Successfully !",Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.e("log", call.request().url().toString());
                        Log.e("ERROR2", t.toString());
                    }

                });
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else
            return 0;
    }
}