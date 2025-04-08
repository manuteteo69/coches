package com.example.coches;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private IntroAdapter adapter;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

        List<IntroFragment> fragments = new ArrayList<>();
        fragments.add(IntroFragment.newInstance(1));
        fragments.add(IntroFragment.newInstance(2));
        fragments.add(IntroFragment.newInstance(3));

        adapter = new IntroAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();
    }
}
