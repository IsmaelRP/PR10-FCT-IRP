package com.practica.ismael.pr10_fct_irp.ui.activity;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.practica.ismael.pr10_fct_irp.R;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsFragment  extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
    private MainViewModel vm;
    private final SharedPreferences.OnSharedPreferenceChangeListener onSharedPreferenceChangeListener = this;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        vm = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        setupViews();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    @Override
    public void onPause() {
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
        super.onPause();
    }

    @SuppressWarnings("ConstantConditions")
    private void setupViews() {
        ((AppCompatActivity) requireActivity()).setSupportActionBar(requireActivity().findViewById(R.id.toolbarSettings));
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        vm.setHomePage(sharedPreferences.getString(getString(R.string.mainDest), getString(R.string.desDefault)));
        vm.setVisitDays(sharedPreferences.getInt(getString(R.string.daysKey), 15));
    }
}
