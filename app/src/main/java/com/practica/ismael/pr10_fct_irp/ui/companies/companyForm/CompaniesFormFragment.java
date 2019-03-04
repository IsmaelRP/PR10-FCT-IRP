package com.practica.ismael.pr10_fct_irp.ui.companies.companyForm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.EventObserver;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentCompanyformBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.utils.KeyboardUtils;
import com.practica.ismael.pr10_fct_irp.utils.SnackbarUtils;

import java.util.Objects;

import javax.net.ssl.KeyStoreBuilderParameters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class CompaniesFormFragment extends Fragment {

    private CompaniesFormViewModel vm;
    private FragmentCompanyformBinding b;
    private ToolbarConf toolbarConf;
    private long companyId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_companyform, container, false);
        return b.getRoot();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
        Objects.requireNonNull(getArguments());
        companyId = CompaniesFormFragmentArgs.fromBundle(getArguments()).getIdCompany();
        if (companyId != -1 && !vm.getRestoreData().hasBeenHandled()) {
            vm.getCompany(companyId).observe(getViewLifecycleOwner(), this::restoreCompany);
            vm.getRestoreData().getContentIfNotHandled();
        }
        observeResultMessages();
    }

    private void observeResultMessages() {
        vm.getSuccessMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(this::showMessageBack));
        vm.getErrorMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(v -> showMessage("Company's name already exists!")));
    }

    private void showMessageBack(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).addCallback(
                new BaseTransientBottomBar.BaseCallback<Snackbar>() {
                    @Override
                    public void onDismissed(Snackbar transientBottomBar, int event) {
                        super.onDismissed(transientBottomBar, event);
                        requireActivity().onBackPressed();
                    }
                }).show();
    }

    private void showMessage(String message) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void restoreCompany(Company company) {
        b.txtNameCompanyForm.setText(company.getNameCompany());
        b.txtAddressCompanyForm.setText(company.getAddressCompany());
        b.txtCifCompanyForm.setText(company.getCif());
        b.txtEmailCompanyForm.setText(company.getEmailCompany());
        b.txtTelCompanyForm.setText(String.valueOf(company.getTelCompany()));
        b.txtTutorCompanyForm.setText(company.getNameTutor());
        b.txtTelTutorCompanyForm.setText(String.valueOf(company.getTelTutor()));
        b.txtLogoCompanyForm.setText(company.getLogoCompany());
    }

    private void setupViews() {
        vm = ViewModelProviders.of(this,
                new CompaniesFormViewModelFactory(requireActivity().getApplication(),
                        new RepositoryImpl(AppDatabase.getInstance(requireContext().getApplicationContext())
                                .companyDao()))).get(CompaniesFormViewModel.class);
        toolbarConf.onConfigure(b.toolbarFormCompanies);
    }

    private void saveCompany() {
        Company company = new Company(0, b.txtNameCompanyForm.getText().toString(), b.txtCifCompanyForm.getText().toString(),
                b.txtAddressCompanyForm.getText().toString(), Integer.valueOf(b.txtTelCompanyForm.getText().toString()), b.txtEmailCompanyForm.getText().toString(),
                b.txtLogoCompanyForm.getText().toString(), b.txtTutorCompanyForm.getText().toString(), Integer.valueOf(b.txtTelTutorCompanyForm.getText().toString()));
        vm.insertCompany(company);
    }

    private void updateCompany() {
        Company company = new Company(companyId, b.txtNameCompanyForm.getText().toString(), b.txtCifCompanyForm.getText().toString(),
                b.txtAddressCompanyForm.getText().toString(), Integer.valueOf(b.txtTelCompanyForm.getText().toString()), b.txtEmailCompanyForm.getText().toString(),
                b.txtLogoCompanyForm.getText().toString(), b.txtTutorCompanyForm.getText().toString(), Integer.valueOf(b.txtTelTutorCompanyForm.getText().toString()));
        vm.updateCompany(company);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            KeyboardUtils.hideSoftKeyboard(requireActivity());
            if (valid()) {
                if (companyId == -1) {
                    saveCompany();
                } else {
                    updateCompany();
                }
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyboardUtils.hideSoftKeyboard(requireActivity());
    }

    private boolean valid() {
        boolean valid = true;

        if (b.txtNameCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtCifCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtAddressCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtTelCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtEmailCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtTutorCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtTelTutorCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtLogoCompanyForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (!valid) {
            SnackbarUtils.snackbar(requireView(), "You have to fill all the fields!", Snackbar.LENGTH_SHORT);
        }
        return valid;
    }
}
