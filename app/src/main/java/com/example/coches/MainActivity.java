package com.example.coches;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private IntroAdapter adapter;
    private ImageView dot1, dot2, dot3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Iniciar ViewPager y los indicadores
        viewPager = findViewById(R.id.viewPager);
        dot1 = findViewById(R.id.dot1);
        dot2 = findViewById(R.id.dot2);
        dot3 = findViewById(R.id.dot3);

        // Crear la lista de fragments (3 pantallas de introducción)
        List<IntroFragment> fragments = new ArrayList<>();
        fragments.add(IntroFragment.newInstance(1));
        fragments.add(IntroFragment.newInstance(2));
        fragments.add(IntroFragment.newInstance(3));

        adapter = new IntroAdapter(this, fragments);
        viewPager.setAdapter(adapter);

        // Actualizar los "dots" según la página actual
        viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                updateDots(position);
            }
        });
    }

    private void updateDots(int position) {
        if (position == 2) {
            // Ocultar los dots en el último fragment
            dot1.setVisibility(View.GONE);
            dot2.setVisibility(View.GONE);
            dot3.setVisibility(View.GONE);
        } else {
            // Asegurarse de que los dots estén visibles en las otras pantallas
            dot1.setVisibility(View.VISIBLE);
            dot2.setVisibility(View.VISIBLE);
            dot3.setVisibility(View.VISIBLE);

            int unselectedColor = ContextCompat.getColor(this, android.R.color.darker_gray);
            int selectedColor;
            switch (position) {
                case 0:
                    selectedColor = ContextCompat.getColor(this, android.R.color.holo_red_dark);
                    break;
                case 1:
                    selectedColor = ContextCompat.getColor(this, android.R.color.black);
                    break;
                default:
                    selectedColor = unselectedColor;
                    break;
            }
            dot1.setColorFilter(position == 0 ? selectedColor : unselectedColor);
            dot2.setColorFilter(position == 1 ? selectedColor : unselectedColor);
            dot3.setColorFilter(position == 2 ? selectedColor : unselectedColor);
        }
    }}
