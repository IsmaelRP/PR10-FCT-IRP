package com.practica.ismael.pr10_fct_irp.ui.students.listStudent;

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
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentStudentsBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class StudentsFragment extends Fragment {

    private StudentsAdapter listAdapter;
    private StudentsViewModel vm;
    private ToolbarConf toolbarConf;
    private FragmentStudentsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_students, container, false);
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
        observeStudents();
        observeEditTrigger();
        observeResultMessages();
    }

    private void observeResultMessages() {
        vm.getSuccessMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(this::showMessage));
        vm.getErrorMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(v -> showMessage(getString(R.string.student_error_deleting))));
    }

    private void showMessage(String message){
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show();
    }

    private void observeEditTrigger() {
        this.vm.getEditTrigger().observe(getViewLifecycleOwner(), new EventObserver<>(this::navigateToForm));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    private void setupViews() {
        Toolbar toolbar = requireView().findViewById(R.id.toolbarStudents);
        toolbarConf.onConfigure(toolbar);
        vm = ViewModelProviders.of(this,
                new StudentsViewModelFactory(requireActivity().getApplication(),
                        new RepositoryImpl(AppDatabase.getInstance(requireContext().getApplicationContext())
                                .studentDao(), AppDatabase.getInstance(requireContext().getApplicationContext())
                                .companyDao()))).get(StudentsViewModel.class);
        listAdapter = new StudentsAdapter(vm);

        b.lstStudents.setHasFixedSize(true);
        b.lstStudents.setLayoutManager(new GridLayoutManager(requireContext(), 1)); //TODO: considerar si poner 2 columnas en landscape
        b.lstStudents.setItemAnimator(new DefaultItemAnimator());
        b.lstStudents.setAdapter(listAdapter);
        b.fabStudents.setOnClickListener(v -> navigateToForm(null));
        b.lblEmptyStudents.setOnClickListener(v -> navigateToForm(null));
    }

    private void navigateToForm(Student student) {
        if (student == null) {
            Navigation.findNavController(requireView()).navigate(R.id.desStudentsToStudentsForm);
        } else {
            StudentsFragmentDirections.DesStudentsToStudentsForm action = StudentsFragmentDirections.desStudentsToStudentsForm().setIdStudent((int) student.getIdStudent());
            Navigation.findNavController(requireView()).navigate(action);
        }
    }

    private void observeStudents() {
        vm.getStudents().observe(getViewLifecycleOwner(), students -> {
            listAdapter.submitList(students);
            b.lblEmptyStudents.setVisibility(students.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preferences_menu, menu);
    }

}
