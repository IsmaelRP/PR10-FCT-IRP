package com.practica.ismael.pr10_fct_irp.ui.meetings.listMeeting;

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
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentMeetingsBinding;
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

public class MeetingsFragment extends Fragment {

    private MeetingsAdapter listAdapter;
    private MeetingsViewModel vm;
    private ToolbarConf toolbarConf;
    private FragmentMeetingsBinding b;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_meetings, container, false);
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
        observeMeetings();
        observeEditTrigger();
        observeResultMessages();
    }

    private void observeResultMessages() {
        vm.getSuccessMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(this::showMessage));
        vm.getErrorMessage().observe(getViewLifecycleOwner(),
                new EventObserver<>(this::showMessage));
    }

    private void showMessage(String message) {
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
        Toolbar toolbar = requireView().findViewById(R.id.toolbarMeetings);
        toolbarConf.onConfigure(toolbar);
        vm = ViewModelProviders.of(this,
                new MeetingsViewModelFactory(requireActivity().getApplication(),
                        new RepositoryImpl(AppDatabase.getInstance(requireContext().getApplicationContext())
                                .meetingDao()))).get(MeetingsViewModel.class);
        listAdapter = new MeetingsAdapter(vm);

        b.lstMeetings.setHasFixedSize(true);
        b.lstMeetings.setLayoutManager(new GridLayoutManager(requireContext(), 1)); //TODO: considerar si poner 2 columnas en landscape
        b.lstMeetings.setItemAnimator(new DefaultItemAnimator());
        b.lstMeetings.setAdapter(listAdapter);
    }

    private void navigateToForm(Meeting meeting) {
        MeetingsFragmentDirections.DesMeetingsToMeetingsForm action = MeetingsFragmentDirections.desMeetingsToMeetingsForm().setIdMeeting(meeting.getIdMeeting());
        Navigation.findNavController(requireView()).navigate(action);

    }

    private void observeMeetings() {
        vm.getMeetings().observe(getViewLifecycleOwner(), meetings -> {
            listAdapter.submitList(meetings);
            b.lblEmptyMeetings.setVisibility(meetings.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });


    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preferences_menu, menu);
    }
}
