package com.practica.ismael.pr10_fct_irp.ui.meetings.meetingForm;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TimePicker;

import com.google.android.material.snackbar.Snackbar;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.base.DatePickerDialogFragment;
import com.practica.ismael.pr10_fct_irp.base.TimePickerDialogFragment;
import com.practica.ismael.pr10_fct_irp.data.RepositoryImpl;
import com.practica.ismael.pr10_fct_irp.data.local.AppDatabase;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;
import com.practica.ismael.pr10_fct_irp.databinding.FragmentMeetingformBinding;
import com.practica.ismael.pr10_fct_irp.ui.activity.MainViewModel;
import com.practica.ismael.pr10_fct_irp.ui.activity.ToolbarConf;
import com.practica.ismael.pr10_fct_irp.utils.KeyboardUtils;
import com.practica.ismael.pr10_fct_irp.utils.SnackbarUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

public class MeetingsFormFragment extends Fragment {

    private MeetingsFormViewModel vm;
    private MainViewModel mainVM;
    private FragmentMeetingformBinding b;
    private ToolbarConf toolbarConf;
    private long meetingId;
    private long companyId;
    private long studentId;
    private boolean timeStart;

    TimePickerDialog.OnTimeSetListener onTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            boolean flagH = false;
            boolean flagM = false;
            String aux = "";

            if (hourOfDay < 10) {
                flagH = true;
            }
            if (minute < 10) {
                flagM = true;
            }
            if (timeStart) {
                if (flagH) {
                    aux += "0" + hourOfDay + " : ";
                } else {
                    aux += hourOfDay + " : ";
                }
                if (flagM) {
                    aux += "0" + minute;
                } else {
                    aux += minute;
                }
                b.lblTimeStartMeetingForm.setText(aux);
            } else {
                if (flagH) {
                    aux += "0" + hourOfDay + " : ";
                } else {
                    aux += hourOfDay + " : ";
                }
                if (flagM) {
                    aux += "0" + minute;
                } else {
                    aux += minute;
                }
                b.lblTimeEndMeetingForm.setText(aux);
            }
        }
    };

    DatePickerDialog.OnDateSetListener onDate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            b.lblDateMeetingForm.setText(String.valueOf(dayOfMonth) + " / " + String.valueOf(monthOfYear + 1)
                    + " / " + String.valueOf(year));
            SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");
            try {
                mainVM.setActualDate(format.parse(b.lblDateMeetingForm.getText().toString()));
            } catch (ParseException e) {
                b.lblDateMeetingForm.setText("Error");
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        b = DataBindingUtil.inflate(inflater, R.layout.fragment_meetingform, container, false);
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
        meetingId = MeetingsFormFragmentArgs.fromBundle(getArguments()).getIdMeeting();
        studentId = MeetingsFormFragmentArgs.fromBundle(getArguments()).getIdStudent();
        if (meetingId != -1) {
            vm.getMeeting(meetingId).observe(getViewLifecycleOwner(), this::restoreMeeting);
            vm.getRestoreData().getContentIfNotHandled();
        } else if (studentId != -1 && !vm.getRestoreData().hasBeenHandled()) {
            vm.getStudent(studentId).observe(getViewLifecycleOwner(), this::restoreStudent);
            vm.getRestoreData().getContentIfNotHandled();
        }
        setupDatePicker();
        setupDate();
    }

    private void setupDate() {
        if (mainVM.getActualDate() != null) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");
            b.lblDateMeetingForm.setText(format.format(mainVM.getActualDate()));
        }
    }

    private void setupDatePicker() {
        b.lblDateMeetingForm.setOnClickListener(v -> showDatePickerDialog());
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void restoreStudent(Student student) {
        Calendar currentTime = Calendar.getInstance();
        if (!mainVM.getInitial()) {
            currentTime.setTime(mainVM.getActualDate());
            currentTime.add(Calendar.DATE, mainVM.getVisitDays());
            mainVM.setActualDate(currentTime.getTime());
        } else {
            mainVM.setActualDate(currentTime.getTime());
        }

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("hh : mm");
        Date date = new Date(System.currentTimeMillis());
        Date dateEnd = new Date(System.currentTimeMillis() + 900000);
        //He supuesto que cada reunión durará 15 minutos, por lo que, cuando el usuario vaya
        //a rellenar los datos, estará por defecto que la reunión dura desde este mismo instante,
        //hasta 15 minutos despues, modificable por supuesto con el TimePicker

        b.lblNameStudentMeetingForm.setText(student.getNameStudent());
        b.lblNameCompanyMeetingForm.setText(student.getNameCompany());
        b.lblDateMeetingForm.setText(format.format(currentTime.getTime()));
        b.lblTimeStartMeetingForm.setText(formatTime.format(date));
        b.lblTimeEndMeetingForm.setText(formatTime.format(dateEnd));
        companyId = student.getCompanyStudent();


        b.lblTimeStartMeetingForm.setOnClickListener(v -> showTimePicker(true));
        b.lblTimeEndMeetingForm.setOnClickListener(v -> showTimePicker(false));
    }

    private void showTimePicker(boolean firstTime) {
        TimePickerDialogFragment timePicker;
        timeStart = firstTime;

        if (firstTime) {
            timePicker = TimePickerDialogFragment.newInstance(Integer.valueOf(b.lblTimeStartMeetingForm.getText().toString().substring(0, 2)), Integer.valueOf(b.lblTimeStartMeetingForm.getText().toString().substring(5, 7)), true);
        } else {
            timePicker = TimePickerDialogFragment.newInstance(Integer.valueOf(b.lblTimeEndMeetingForm.getText().toString().substring(0, 2)), Integer.valueOf(b.lblTimeEndMeetingForm.getText().toString().substring(5, 7)), true);
        }
        timePicker.setListener(onTime);
        timePicker.show(getFragmentManager(), "TimePicker");
    }

    private void restoreMeeting(Meeting meeting) {
        b.lblNameStudentMeetingForm.setText(meeting.getStudentNameMeeting());
        b.lblNameCompanyMeetingForm.setText(meeting.getCompanyNameMeeting());
        b.lblDateMeetingForm.setText(meeting.getDateMeeting());
        b.lblTimeStartMeetingForm.setText(meeting.getTimeStart());
        b.lblTimeEndMeetingForm.setText(meeting.getTimeEnd());
        b.txtObservationsMeetingForm.setText(meeting.getObservations());

        companyId = meeting.getCompanyMeeting();
        studentId = meeting.getStudentMeeting();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd / MM / yyyy");
        try {
            mainVM.setActualDate(format.parse(b.lblDateMeetingForm.getText().toString()));
        } catch (ParseException e) {
            b.lblDateMeetingForm.setText("Error");
        }

        if (vm.getRestoreData().hasBeenHandled()) {
            b.lblDateMeetingForm.setText(format.format(mainVM.getActualDate()));
        }
        b.lblTimeStartMeetingForm.setOnClickListener(v -> showTimePicker(true));
        b.lblTimeEndMeetingForm.setOnClickListener(v -> showTimePicker(false));
    }


    private void setupViews() {
        vm = ViewModelProviders.of(this,
                new MeetingsFormViewModelFactory(requireActivity().getApplication(),
                        new RepositoryImpl(AppDatabase.getInstance(requireContext().getApplicationContext())
                                .meetingDao(), AppDatabase.getInstance(requireContext().getApplicationContext())
                                .studentDao()))).get(MeetingsFormViewModel.class);
        mainVM = ViewModelProviders.of(requireActivity()).get(MainViewModel.class);
        toolbarConf.onConfigure(b.toolbarFormMeetings);
        b.txtObservationsMeetingForm.setOnEditorActionListener((v, actionId, event) -> {
            donePressed();
            return true;
        });

    }

    private void showDatePickerDialog() {
        DatePickerDialogFragment date = new DatePickerDialogFragment();
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        if (mainVM.getActualDate() != null) {
            calender.setTime(mainVM.getActualDate());
            args.putInt("year", calender.get(Calendar.YEAR));
            args.putInt("month", calender.get(Calendar.MONTH));
            args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        }

        date.setArguments(args);
        date.setCallBack(onDate);
        date.show(getFragmentManager(), "Date Picker");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        KeyboardUtils.hideSoftKeyboard(requireActivity());
        mainVM.setActualDate(null);
    }

    private void updateMeeting() {
        Meeting meeting = new Meeting(meetingId, b.lblDateMeetingForm.getText().toString(),
                b.lblTimeStartMeetingForm.getText().toString(), b.lblTimeEndMeetingForm.getText().toString(),
                b.txtObservationsMeetingForm.getText().toString(), companyId, studentId, b.lblNameStudentMeetingForm.getText().toString(),
                b.lblNameCompanyMeetingForm.getText().toString());
        vm.updateMeeting(meeting);
        mainVM.setActualDate(null);
    }

    private void insertMeeting() {
        Meeting meeting = new Meeting(0, b.lblDateMeetingForm.getText().toString(),
                b.lblTimeStartMeetingForm.getText().toString(), b.lblTimeEndMeetingForm.getText().toString(),
                b.txtObservationsMeetingForm.getText().toString(), companyId, studentId, b.lblNameStudentMeetingForm.getText().toString(),
                b.lblNameCompanyMeetingForm.getText().toString());
        vm.insertMeeting(meeting);
        mainVM.setActualDate(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.form_save_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void donePressed() {
        KeyboardUtils.hideSoftKeyboard(requireActivity());
        if (valid()) {
            if (meetingId != -1) {
                updateMeeting();
            } else if (studentId != -1) {
                insertMeeting();
            }
            getActivity().getSupportFragmentManager().popBackStack();
        } else {
            SnackbarUtils.snackbar(requireView(), "The meeting can't end before it has started!", Snackbar.LENGTH_SHORT);
        }
    }

    private boolean valid() {
        boolean valid = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatTime = new SimpleDateFormat("hh : mm");

        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();

        try {
            start.setTime(formatTime.parse(b.lblTimeStartMeetingForm.getText().toString()));
            end.setTime(formatTime.parse(b.lblTimeEndMeetingForm.getText().toString()));

            if (start.before(end)){
                valid = true;
            }
        } catch (ParseException e) {
            return false;
        }
        return valid;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mnuSave) {
            donePressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
