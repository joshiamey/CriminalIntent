package com.example.criminalintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;
import java.util.UUID;

public class CrimePagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "crime_id";
    private ViewPager mViewPager;
    private Button mFirstButton;
    private Button mLastButton;
    private List<Crime> mCrimes;

    public static Intent newIntent(Context pckgContext, UUID inCrimeId){
        Intent intent = new Intent(pckgContext,CrimePagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID,inCrimeId);
        return intent;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crime_pager);

        mViewPager = (ViewPager) findViewById(R.id.crime_view_pager);

        mCrimes = CrimeLab.get(this).getCrimes();
        FragmentManager fmgr = getSupportFragmentManager();

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mFirstButton = (Button) findViewById(R.id.jump_to_first);
        mLastButton = (Button) findViewById(R.id.jump_to_last);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mFirstButton.setEnabled(position != 0);
                mLastButton.setEnabled(position != (mCrimes.size() - 1));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setAdapter(new FragmentStatePagerAdapter(fmgr) {
            @Override
            public Fragment getItem(int position) {
                Crime cr = mCrimes.get(position);
                return CrimeFragment.newInstance(cr.getId());
            }

            @Override
            public int getCount() {
                return mCrimes.size();
            }
        });


        for (int i = 0; i < mCrimes.size();++i){
            if(mCrimes.get(i).getId().equals(crimeId)){
                mViewPager.setCurrentItem(i);
                break;
            }
        }

        mFirstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(0,true);
            }
        });

        mLastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(mCrimes.size() - 1,true);
            }
        });
    }
}
