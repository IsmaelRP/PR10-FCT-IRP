package com.practica.ismael.pr10_fct_irp.ui.companies.listCompany;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.EventObserver;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentCompaniesBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.ui.companies.listCompany.CompaniesAdapter;
import com.practica.ismael.pr10_fct_irp.ui.companies.listCompany.CompaniesViewModel;
import com.practica.ismael.pr10_fct_irp.ui.companies.listCompany.CompaniesViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class CompaniesFragment extends Fragment {

    private CompaniesAdapter listAdapter;
    private CompaniesViewModel vm;
    private ToolbarConf toolbarConf;
    private FragmentCompaniesBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_companies, container, false);
        return b.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
        observeCompanies();
        observeResultMessages();
    }

    private void observeResultMessages() {
        vm.getSuccessMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(this::showMessage));
        vm.getErrorMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(v -> showMessage(getString(R.string.company_error_deleting))));
        vm.getEditTrigger().observe(getViewLifecycleOwner(), new EventObserver<>(this::navigateToForm));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    private void setupViews() {
        Toolbar toolbar = requireView().findViewById(R.id.toolbarCompanies);
        toolbarConf.onConfigure(toolbar);
        vm = ViewModelProviders.of(this,
                new CompaniesViewModelFactory(requireActivity().getApplication(), new RepositoryImpl(
                        AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao())))
                .get(CompaniesViewModel.class);
        listAdapter = new CompaniesAdapter(vm);

        b.lstCompanies.setHasFixedSize(true);
        b.lstCompanies.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        b.lstCompanies.setItemAnimator(new DefaultItemAnimator());
        b.lstCompanies.setAdapter(listAdapter);
        b.fabCompanies.setOnClickListener(v -> navigateToForm(null));
        b.lblEmptyCompanies.setOnClickListener(v -> navigateToForm(null));
    }

    private void showMessage(String message) {
        Snackbar.make(b.lblEmptyCompanies, message, Snackbar.LENGTH_SHORT).show();
    }

    private void observeCompanies() {
        vm.getCompanies().observe(getViewLifecycleOwner(), companies -> {
            listAdapter.submitList(companies);
            b.lblEmptyCompanies.setVisibility(companies.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    private void navigateToForm(Company company) {
        if (company == null) {
            Navigation.findNavController(requireView()).navigate(R.id.desCompaniesToCompaniesForm);
        } else {
            CompaniesFragmentDirections.DesCompaniesToCompaniesForm action = CompaniesFragmentDirections.desCompaniesToCompaniesForm().setIdCompany((int) company.getIdCompany());
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preferences_menu, menu);
    }

}
