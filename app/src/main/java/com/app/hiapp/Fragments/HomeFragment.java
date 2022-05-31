package com.app.hiapp.Fragments;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.app.hiapp.Adapter.PostAdapter;
import com.app.hiapp.Adapter.SectionViewPager;
import com.app.hiapp.Adapter.StoryAdapter;
import com.app.hiapp.MainActivity;
import com.app.hiapp.Models.Post;
import com.app.hiapp.Models.Story;
import com.app.hiapp.Permissions.Permissions;
import com.app.hiapp.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private TabLayout tabLayout;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        viewPager = view.findViewById(R.id.containers);
        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

         setViewPager();
        return view;
    }


    private void setViewPager(){
        SectionViewPager sectionViewPager = new SectionViewPager(getChildFragmentManager());
        sectionViewPager.addFragment(new StatusFragment());
        sectionViewPager.addFragment(new FeedFragment());
        sectionViewPager.addFragment(new VideoFragment());
        viewPager.setAdapter(sectionViewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Status");
        tabLayout.getTabAt(1).setText("Feed");
        tabLayout.getTabAt(2).setText("Video");

    }


}
