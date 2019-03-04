package com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentSelectionCompaniesBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.MainViewModel;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection.StudentsCompanyAdapter;
import com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection.StudentsCompanyViewModel;
import com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection.StudentsCompanyViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class StudentsCompanyFragment extends Fragment {

    private StudentsCompanyAdapter listAdapter;
    private StudentsCompanyViewModel vm;
    private MainViewModel mainVm;
    private ToolbarConf toolbarConf;
    private FragmentSelectionCompaniesBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_selection_companies, container, false);
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
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    private void setupViews() {
        Toolbar toolbar = requireView().findViewById(R.id.toolbarCompaniesSelection);
        toolbarConf.onConfigure(toolbar);
        vm = ViewModelProviders.of(this,
                new StudentsCompanyViewModelFactory(requireActivity().getApplication(), new RepositoryImpl(
                        AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao())))
                .get(StudentsCompanyViewModel.class);
        mainVm = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        listAdapter = new StudentsCompanyAdapter(position -> selectCompany(listAdapter.getItem(position)));

        b.lstCompaniesSelection.setHasFixedSize(true);
        b.lstCompaniesSelection.setLayoutManager(new GridLayoutManager(requireContext(), 1));
        b.lstCompaniesSelection.setItemAnimator(new DefaultItemAnimator());
        b.lstCompaniesSelection.setAdapter(listAdapter);
    }

    void selectCompany(Company company) {
        mainVm.setCompanyEdit(company);
        getActivity().getSupportFragmentManager().popBackStack();
    }

    private void observeCompanies() {
        vm.getCompanies().observe(getViewLifecycleOwner(), companies -> {
            listAdapter.submitList(companies);
            b.lblEmptyCompaniesSelection.setVisibility(companies.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }
}
