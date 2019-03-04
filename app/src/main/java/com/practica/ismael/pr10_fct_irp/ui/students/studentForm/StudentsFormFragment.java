package com.practica.ismael.pr10_fct_irp.ui.students.studentForm;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentStudentformBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.MainViewModel;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.utils.KeyboardUtils;
import com.practica.ismael.pr10_fct_irp.utils.SnackbarUtils;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

public class StudentsFormFragment extends Fragment {

    private StudentsFormViewModel vm;
    private FragmentStudentformBinding b;
    private MainViewModel mainVM;
    private long studentId;
    private long companyId;
    private ToolbarConf toolbarConf;
    private String nameCompany;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_studentform, container, false);
        return b.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setupViews();
        mainVM.getCompanyEdit().observe(getViewLifecycleOwner(), this::companySelected);
        Objects.requireNonNull(getArguments());
        studentId = StudentsFormFragmentArgs.fromBundle(getArguments()).getIdStudent();
        if (studentId != -1 && !vm.getRestoreData().hasBeenHandled()) {
            vm.getStudent(studentId).observe(getViewLifecycleOwner(), this::restoreStudent);
            vm.getRestoreData().getContentIfNotHandled();
        } else if (studentId != -1) {
            b.lblCompanyStudentForm.setText(vm.getCompanyNameAux().getValue());
        }
    }

    private void companySelected(Company company) {
        if (company != null) {
            companyId = company.getIdCompany();
            b.lblCompanyStudentForm.setText(company.getNameCompany());
            nameCompany = company.getNameCompany();
        }
    }

    private void restoreStudent(Student student) {
        b.txtNameStudentForm.setText(student.getNameStudent());
        b.txtEmailStudentForm.setText(student.getEmailStudent());
        b.txtTelStudentForm.setText(String.valueOf(student.getTelStudent()));
        b.txtGradeStudentForm.setText(student.getGradeStudent());
        b.txtScheduleStudentForm.setText(student.getScheduleStudent());
        companyId = student.getCompanyStudent();
        b.lblCompanyStudentForm.setText(student.getNameCompany());
    }

    private void setupViews() {
        toolbarConf.onConfigure(b.toolbarFormStudents);
        vm = ViewModelProviders.of(this,
                new StudentsFormViewModelFactory(requireActivity().getApplication(), new RepositoryImpl(
                        AppDatabase.getInstance(requireContext().getApplicationContext()).studentDao(),
                        AppDatabase.getInstance(requireContext().getApplicationContext()).companyDao())))
                .get(StudentsFormViewModel.class);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        b.btnSelectCompany.setOnClickListener(v -> selectcompany());
    }

    private void selectcompany() {
        Navigation.findNavController(requireView()).navigate(R.id.desStudentsFormToStudentsCompanySelection);
    }

    private void saveStudent() {
        Student student = new Student(0, b.txtNameStudentForm.getText().toString(), Integer.valueOf(b.txtTelStudentForm.getText().toString()),
                b.txtEmailStudentForm.getText().toString(), b.txtGradeStudentForm.getText().toString(), companyId,
                b.txtScheduleStudentForm.getText().toString(), nameCompany);
        vm.insertStudent(student);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            if (valid()) {
                if (studentId == -1) {
                    saveStudent();
                } else {
                    updateStudent();
                }
                mainVM.setCompanyEdit(null);
                getActivity().getSupportFragmentManager().popBackStack();
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

        if (b.txtNameStudentForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtGradeStudentForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.lblCompanyStudentForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtScheduleStudentForm.getText().toString().isEmpty()) {
            valid = false;
        }
        if (b.txtTelStudentForm.getText().toString().isEmpty()) {
            valid = false;
        }

        SnackbarUtils.snackbar(requireView(), "You have to fill all the fields!", Snackbar.LENGTH_SHORT);

        return valid;
    }

    private void updateStudent() {
        Student student = new Student(studentId, b.txtNameStudentForm.getText().toString(), Integer.valueOf(b.txtTelStudentForm.getText().toString()),
                b.txtEmailStudentForm.getText().toString(), b.txtGradeStudentForm.getText().toString(),
                companyId, b.txtScheduleStudentForm.getText().toString(), nameCompany);
        vm.updateStudent(student);
    }

}
