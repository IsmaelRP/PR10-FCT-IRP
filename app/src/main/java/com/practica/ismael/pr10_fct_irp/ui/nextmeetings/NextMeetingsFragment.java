package com.practica.ismael.pr10_fct_irp.ui.nextmeetings;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.EventObserver;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentNextmeetingsBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.MainViewModel;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.ui.nextmeetings.NextMeetingsAdapter;
import com.practica.ismael.pr10_fct_irp.ui.nextmeetings.NextMeetingsViewModel;
import com.practica.ismael.pr10_fct_irp.ui.nextmeetings.NextMeetingsViewModelFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;

public class NextMeetingsFragment extends Fragment {

    FragmentNextmeetingsBinding b;
    private NextMeetingsAdapter listAdapter;
    private NextMeetingsViewModel vm;
    private ToolbarConf toolbarConf;
    private MainViewModel mainVM;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_nextmeetings, container, false);
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
        observeAddTrigger();
    }

    private void observeAddTrigger() {
        this.vm.getAddTrigger().observe(getViewLifecycleOwner(), new EventObserver<>(this::navigateToForm));
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        toolbarConf = (ToolbarConf) context;
    }

    private void setupViews() {
        Toolbar toolbar = requireView().findViewById(R.id.toolbarNextMeetings);
        toolbarConf.onConfigure(toolbar);
        vm = ViewModelProviders.of(this,
                new NextMeetingsViewModelFactory(requireActivity().getApplication(),
                        new RepositoryImpl(AppDatabase.getInstance(requireContext().getApplicationContext())
                                .meetingDao()))).get(NextMeetingsViewModel.class);
        listAdapter = new NextMeetingsAdapter(vm);

        b.lstNextMeetings.setHasFixedSize(true);
        b.lstNextMeetings.setLayoutManager(new GridLayoutManager(requireContext(), 1)); //TODO: considerar si poner 2 columnas en landscape
        b.lstNextMeetings.setItemAnimator(new DefaultItemAnimator());
        b.lstNextMeetings.setAdapter(listAdapter);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
    }

    private void navigateToForm(NextMeeting nextMeeting) {
        if (nextMeeting.getDateMeeting().isEmpty()){
            mainVM.setInitial(true);
        }else{
            SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");
            mainVM.setInitial(false);
            try {
                mainVM.setActualDate(format.parse(nextMeeting.getDateMeeting()));
            } catch (ParseException e) {
                mainVM.setActualDate(null);
            }
        }
        NextMeetingsFragmentDirections.DesNextMeetingsToMeetingsForm action = NextMeetingsFragmentDirections.desNextMeetingsToMeetingsForm().setIdStudent(nextMeeting.getIdStudent());
        Navigation.findNavController(requireView()).navigate(action);

    }

    private void observeMeetings() {
        vm.getData().observe(getViewLifecycleOwner(), meetings -> {
            listAdapter.submitList(meetings);
            b.lblEmptyNextMeetings.setVisibility(meetings.isEmpty() ? View.VISIBLE : View.INVISIBLE);
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.preferences_menu, menu);
    }
}
