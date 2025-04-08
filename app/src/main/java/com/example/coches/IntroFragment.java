package com.example.coches;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int screenNumber = getArguments() != null ? getArguments().getInt(ARG_SCREEN) : 1;
        View view;

        switch (screenNumber) {
            case 1:
                view = inflater.inflate(R.layout.fragment_intro1, container, false);
                Spinner spinner = view.findViewById(R.id.spinner_country);
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(), R.array.countries, android.R.layout.simple_spinner_item);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                // Guardar selección en SharedPreferences
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        SharedPreferences prefs = requireContext().getSharedPreferences("prefs", Context.MODE_PRIVATE);
                        prefs.edit().putString("country", parent.getItemAtPosition(position).toString()).apply();
                    }
                    @Override public void onNothingSelected(AdapterView<?> parent) {}
                });

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
        ViewPager2 vp = getActivity().findViewById(R.id.viewPager);
        vp.setCurrentItem(vp.getCurrentItem() + 1, true);
    }

    private void skipToEnd() {
        ViewPager2 vp = getActivity().findViewById(R.id.viewPager);
        vp.setCurrentItem(2, true);
    }
}
