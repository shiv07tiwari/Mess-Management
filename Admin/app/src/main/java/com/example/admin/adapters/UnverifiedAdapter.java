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
import com.example.admin.objects.RollNo;
import com.example.admin.objects.Student;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;

public class UnverifiedAdapter extends RecyclerView.Adapter<UnverifiedAdapter.MyViewHolder> {
    private ArrayList<Student> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView mRoll;
        public TextView mEmail;
        public CircularProgressButton mVerify;

        public MyViewHolder(View v) {
            super(v);
            mName = itemView.findViewById(R.id.unver_name);
            mRoll = itemView.findViewById(R.id.unver_roll);
            mEmail = itemView.findViewById(R.id.unver_email);
            mVerify = itemView.findViewById(R.id.unver_button_verify);
            //mReject = itemView.findViewById(R.id.unver_button_reject);

        }
    }

    public UnverifiedAdapter(ArrayList<Student> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public UnverifiedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.unverified_student, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mName.setText(mDataset.get(position).getName());
        holder.mRoll.setText(mDataset.get(position).getRollNo());
        holder.mEmail.setText(mDataset.get(position).getEmail());
        holder.mVerify.doneLoadingAnimation(mContext.getResources().getColor(R.color.green), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_done_white_48dp));
        holder.mVerify.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                RetrofitService apiService =
                        APIClient.getSOSClient().create(RetrofitService.class);
                RollNo r = new RollNo(mDataset.get(position).getRollNo());
                Call<String> call = apiService.verifyUser(r);
                holder.mVerify.startAnimation();

                call.enqueue(new Callback<String>() {

                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        try {
                            holder.mVerify.doneLoadingAnimation(mContext.getResources().getColor(R.color.green), BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_done_white_48dp));
                            Log.e("log", call.request().url().toString());
                            Toast.makeText(mContext,"Student Verified Successfully !",Toast.LENGTH_LONG).show();
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