package com.example.criminalintent;

import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import android.text.format.DateFormat;
import java.util.List;

public class CrimeListFragment extends Fragment {
    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mSolvedImageView;
        private Crime mCrime;

        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());

            String date = DateFormat.format("EEEE,MMM dd,yyyy",crime.getDate()).toString();

            mDateTextView.setText(date);
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }
        public CrimeHolder(LayoutInflater inflater,ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_crime,parent,false));

            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),
//                    mCrime.getTitle() + " clicked!",Toast.LENGTH_SHORT)
//                    .show();nt
            Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }

    }
    private class SeriousCrimeHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView mTitleTextView;
        private TextView mDateTextView;
        private Crime mCrime;
        private ImageView mSolvedImageView;


        public SeriousCrimeHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_police_crime,parent,false));

            itemView.setOnClickListener(this);
            mTitleTextView = (TextView) itemView.findViewById(R.id.crime_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.crime_date);
            mSolvedImageView = (ImageView) itemView.findViewById(R.id.crime_solved);

        }


        public void bind(Crime crime){
            mCrime = crime;
            mTitleTextView.setText(crime.getTitle());
            String date = DateFormat.format("EEEE,MMM dd,yyyy",crime.getDate()).toString();

            mDateTextView.setText(date);
            mSolvedImageView.setVisibility(mCrime.isSolved() ? View.VISIBLE : View.GONE);
        }

        @Override
        public void onClick(View view) {
//            Toast.makeText(getActivity(),
//                    mCrime.getTitle() + " clicked!",Toast.LENGTH_SHORT)
//                    .show();
            Intent intent = CrimeActivity.newIntent(getActivity(),mCrime.getId());
            startActivity(intent);
        }
    }
   private class CrimeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private List<Crime> mCrimes;
        private static final int TYPE_NORMAL_CRIME = 0;
        private static final int TYPE_SERIOUS_CRIME = 1;

        public CrimeAdapter(List<Crime> crimes){
            mCrimes = crimes;
        }

       @Override
       public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            if(holder.getItemViewType() == TYPE_SERIOUS_CRIME){
                SeriousCrimeHolder seriousCrimeHolder = (SeriousCrimeHolder) holder;
                seriousCrimeHolder.bind(mCrimes.get(position));
            }else{
                CrimeHolder crimeHolder = (CrimeHolder) holder;
                crimeHolder.bind(mCrimes.get(position));
            }
       }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());

            if(viewType == TYPE_SERIOUS_CRIME){
                return new SeriousCrimeHolder(layoutInflater,parent);
            }
            return new CrimeHolder(layoutInflater,parent);

        }

        @Override
        public int getItemCount() {
            return mCrimes.size();
        }

       @Override
       public int getItemViewType(int position) {
            Crime crime = mCrimes.get(position);
            if(crime.isRequiresPolice()){
                return TYPE_SERIOUS_CRIME;
            }
            return TYPE_NORMAL_CRIME;

       }
   }

    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mCrimeAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_crime_list,container,false);

        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.crime_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;

    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CrimeLab crimeLab = CrimeLab.get(getActivity());
        if(mCrimeAdapter == null) {
            mCrimeAdapter = new CrimeAdapter(crimeLab.getCrimes());
            mCrimeRecyclerView.setAdapter(mCrimeAdapter);
        }else{
            mCrimeAdapter.notifyDataSetChanged();
//            mCrimeAdapter.notifyItemChanged(getId());
        }
    }
}
