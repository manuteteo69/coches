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
        if (position == 2) { // Si estamos en el tercer fragment, ocultamos todos los dots
            dot1.setVisibility(View.GONE);
            dot2.setVisibility(View.GONE);
            dot3.setVisibility(View.GONE);
        } else {
            // Aseguramos que los dots sean visibles en las otras páginas
            dot1.setVisibility(View.VISIBLE);
            dot2.setVisibility(View.VISIBLE);
            dot3.setVisibility(View.VISIBLE);

            // Asignamos los drawables de base
            dot1.setImageResource(R.drawable.dot_selected);
            dot2.setImageResource(R.drawable.dot_unselected);
            dot3.setImageResource(R.drawable.dot_unselected);

            // Según la página, modificamos el color del dot seleccionado (dot1)
            // Para fragment 1 (position 0), pintamos de rojo; para fragment 2 (position 1), de negro.
            if (position == 0) {
                dot1.setColorFilter(ContextCompat.getColor(this, R.color.newred));
            } else if (position == 1) {
                dot1.setColorFilter(ContextCompat.getColor(this, android.R.color.black));
            }

            // En este ejemplo, los otros dots se dejan sin ColorFilter para mostrar su color original definido en el XML.
            dot2.clearColorFilter();
            dot3.clearColorFilter();
        }
    }

}
