package com.practica.ismael.pr10_fct_irp.ui.students.listStudent;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;


import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsAdapter extends ListAdapter<Student, StudentsAdapter.ViewHolder> {

    private final StudentsViewModel vm;

    public StudentsAdapter(StudentsViewModel vm) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getIdStudent() == newItem.getIdStudent();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return TextUtils.equals(oldItem.getNameStudent(), newItem.getNameStudent()) &&
                        TextUtils.equals(oldItem.getGradeStudent(), newItem.getGradeStudent()) &&
                        oldItem.getCompanyStudent() == newItem.getCompanyStudent() &&
                        TextUtils.equals(oldItem.getScheduleStudent(), newItem.getScheduleStudent());
            }
        });
        this.vm = vm;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_students, parent, false), vm);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdStudent();
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNameStudent;
        private final TextView lblGradeStudent;
        private final TextView lblCompanyStudent;
        private final TextView lblScheduleStudent;
        private final ImageView imgPopupMenu;
        private final StudentsViewModel vm;

        public ViewHolder(View itemView, StudentsViewModel vm) {
            super(itemView);
            lblNameStudent = ViewCompat.requireViewById(itemView, R.id.lblNameStudentList);
            lblGradeStudent = ViewCompat.requireViewById(itemView, R.id.lblGradeStudentList);
            lblCompanyStudent = ViewCompat.requireViewById(itemView, R.id.lblCompanyStudentList);
            lblScheduleStudent = ViewCompat.requireViewById(itemView, R.id.lblScheduleStudentList);
            imgPopupMenu = ViewCompat.requireViewById(itemView, R.id.imgPopupMenuStudentList);
            this.vm = vm;
            imgPopupMenu.setOnClickListener(this::showPopup);
        }

        private void showPopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            popupMenu.show();
        }

        public void bind(Student student) {
            lblNameStudent.setText(student.getNameStudent());
            lblGradeStudent.setText(student.getGradeStudent());
            lblScheduleStudent.setText(student.getScheduleStudent());
            lblCompanyStudent.setText(String.valueOf(student.getNameCompany()));
        }

        private boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuEdit:
                    vm.editStudent(getItem(getAdapterPosition()));
                    break;
                case R.id.mnuDelete:
                    vm.deleteStudent(getItem(getAdapterPosition()));
                    break;
                default:
                    return false;
            }
            return true;
        }
    }
}
