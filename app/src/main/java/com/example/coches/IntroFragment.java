package com.example.coches;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;
import java.util.ArrayList;
import java.util.List;

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
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
                    );
                }
                int startSell = fullText.indexOf("sell");
                if (startSell != -1) {
                    spannable.setSpan(
                            new ForegroundColorSpan(ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark)),
                            startSell, startSell + "sell".length(),
                            SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
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
                TextView countryText = view.findViewById(R.id.txt_vehicles);
                SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                String selectedCountry = prefs.getString("country", "your country");
                countryText.setText("Explore available vehicles online in " + selectedCountry);
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
