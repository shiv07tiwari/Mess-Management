package com.example.admin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.admin.R;
import com.example.admin.objects.Feedback;

import java.util.ArrayList;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.MyViewHolder> {
    private ArrayList<Feedback> mDataset;
    private Context mContext;

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView mName;
        public TextView mTime;
        public TextView mDate;
        public ImageView mRating;
        public TextView mFeedback;

        public MyViewHolder(View v) {
            super(v);
            mName = itemView.findViewById(R.id.stuName);
            mDate = itemView.findViewById(R.id.stuDate);
            mTime = itemView.findViewById(R.id.stuTime);
            mRating = itemView.findViewById(R.id.studRating);
            mFeedback = itemView.findViewById(R.id.stuFeed);

        }
    }

    public FeedbackAdapter(ArrayList<Feedback> myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public FeedbackAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feedback_layout, parent, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        holder.mName.setText(mDataset.get(position).getRollNo());
        holder.mDate.setText(mDataset.get(position).getCurrentDate());
        holder.mTime.setText(mDataset.get(position).getTimeSlot());
        holder.mFeedback.setText(mDataset.get(position).getComplaint());
    }

    @Override
    public int getItemCount() {
        if (mDataset != null)
            return mDataset.size();
        else
            return 0;
    }
}