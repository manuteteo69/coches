package com.example.coches;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IntroFragment extends Fragment {
    private static final String ARG_SCREEN = "screen";

    public static IntroFragment newInstance(int screenNumber) {
        IntroFragment fragment = new IntroFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SCREEN, screenNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        int screenNumber = getArguments() != null ? getArguments().getInt(ARG_SCREEN) : 1;
        View view;

        switch (screenNumber) {
            case 1:
                view = inflater.inflate(R.layout.fragment_intro1, container, false);
                // Configuramos el Spinner usando un adapter personalizado para mostrar banderas.
                Spinner spinner = view.findViewById(R.id.spinner_country);
                List<CountryItem> countries = new ArrayList<>();
                countries.add(new CountryItem("Spain", R.drawable.flag_spain));
                countries.add(new CountryItem("United Kingdom", R.drawable.flag_uk));
                countries.add(new CountryItem("United States", R.drawable.flag_us));
                countries.add(new CountryItem("Portugal", R.drawable.flag_portugal));
                CountryAdapter countryAdapter = new CountryAdapter(requireContext(), countries);
                spinner.setAdapter(countryAdapter);

                // Guardamos la selección en SharedPreferences
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                        // Guardamos el nombre del país
                        prefs.edit().putString("country", countries.get(position).countryName).apply();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                // Resaltamos "buy" y "sell" en rojo en el TextView
                TextView txtIntro = view.findViewById(R.id.txt_intro);
                String fullText = txtIntro.getText().toString(); // Se espera "Marketplace to buy and sell cars."
                SpannableString spannable = new SpannableString(fullText);
                int startBuy = fullText.indexOf("buy");
                if (startBuy != -1) {
                    spannable.setSpan(
                            new ForegroundColorSpan(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)),
                            startBuy, startBuy + "buy".length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
                int startSell = fullText.indexOf("sell");
                if (startSell != -1) {
                    spannable.setSpan(
                            new ForegroundColorSpan(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)),
                            startSell, startSell + "sell".length(),
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
                txtIntro.setText(spannable);

                // Configuramos los botones Next y Skip
                view.findViewById(R.id.btn_next).setOnClickListener(v -> nextPage());
                view.findViewById(R.id.btn_skip).setOnClickListener(v -> skipToEnd());
                break;

            case 2:
                view = inflater.inflate(R.layout.fragment_intro2, container, false);
                view.findViewById(R.id.btn_next).setOnClickListener(v -> nextPage());
                view.findViewById(R.id.btn_skip).setOnClickListener(v -> skipToEnd());
                break;

            case 3:
                view = inflater.inflate(R.layout.fragment_intro3, container, false);
                // Se asume que en fragment_intro3.xml tienes un TextView con id "txt_vehicles"
                // que actuará como subtítulo dinámico (alineado a la izquierda, con el mismo top que en los otros fragments)
                TextView txtVehicles = view.findViewById(R.id.txt_vehicles);
                SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                String selectedCountry = prefs.getString("country", "your country");

                // Definir número de coches según país (puedes ajustar estos valores)
                Map<String, Integer> carsMap = new HashMap<>();
                carsMap.put("Spain", 12345);
                carsMap.put("United Kingdom", 30000);
                carsMap.put("United States", 50000);
                carsMap.put("Portugal", 8000);

                int carsForSale = carsMap.getOrDefault(selectedCountry, 0);
                // Formatear el número para incluir comas (ej. "12,345")
                String numberStr = NumberFormat.getInstance().format(carsForSale);

                // Construir el texto final: solo el número se colorea en rojo
                String dynamicText = numberStr + " cars for sale in " + selectedCountry;
                SpannableString spannableDynamic = new SpannableString(dynamicText);
                // Pintar solo el número (desde índice 0 hasta la longitud del número)
                spannableDynamic.setSpan(
                        new ForegroundColorSpan(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)),
                        0,
                        numberStr.length(),
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                );
                txtVehicles.setText(spannableDynamic);

                view.findViewById(R.id.btn_start).setOnClickListener(v -> {
                    startActivity(new Intent(getActivity(), HomeActivity.class));
                    requireActivity().finish();
                });
                break;

            default:
                view = inflater.inflate(R.layout.fragment_intro1, container, false);
        }
        return view;
    }

    private void nextPage() {
        ViewPager2 vp = requireActivity().findViewById(R.id.viewPager);
        vp.setCurrentItem(vp.getCurrentItem() + 1, true);
    }

    private void skipToEnd() {
        ViewPager2 vp = requireActivity().findViewById(R.id.viewPager);
        vp.setCurrentItem(2, true);
    }
}
