package com.practica.ismael.pr10_fct_irp.ui.meetings.listMeeting;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class MeetingsAdapter extends ListAdapter<Meeting, MeetingsAdapter.ViewHolder> {

    private final MeetingsViewModel vm;

    public MeetingsAdapter(MeetingsViewModel vm) {
        super(new DiffUtil.ItemCallback<Meeting>() {
            @Override
            public boolean areItemsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
                return oldItem.getIdMeeting() == newItem.getIdMeeting();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Meeting oldItem, @NonNull Meeting newItem) {
                return TextUtils.equals(oldItem.getDateMeeting(), newItem.getDateMeeting()) &&
                        TextUtils.equals(oldItem.getTimeStart(), newItem.getTimeStart()) &&
                        TextUtils.equals(oldItem.getTimeEnd(), newItem.getTimeEnd()) &&
                        TextUtils.equals(oldItem.getStudentNameMeeting(), newItem.getStudentNameMeeting()) &&
                        TextUtils.equals(oldItem.getCompanyNameMeeting(), newItem.getCompanyNameMeeting());
            }
        });
        this.vm = vm;
    }

    @NonNull
    @Override
    public MeetingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeetingsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_meetings, parent, false), vm);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdMeeting();
    }

    @Override
    public Meeting getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNameStudentMeeting;
        private final TextView lblNameCompanyMeeting;
        private final TextView lblDateMeeting;
        private final TextView lblTimeStartMeeting;
        private final TextView lblTimeEndMeeting;
        private final ImageView imgPopupMenuMeeting;
        private final MeetingsViewModel vm;

        public ViewHolder(View itemView, MeetingsViewModel vm) {
            super(itemView);
            lblNameStudentMeeting = ViewCompat.requireViewById(itemView, R.id.lblNameStudentMeetingList);
            lblNameCompanyMeeting = ViewCompat.requireViewById(itemView, R.id.lblNameCompanyMeetingList);
            lblDateMeeting = ViewCompat.requireViewById(itemView, R.id.lblDateMeetingList);
            lblTimeStartMeeting = ViewCompat.requireViewById(itemView, R.id.lblTimeStartMeetingList);
            lblTimeEndMeeting = ViewCompat.requireViewById(itemView, R.id.lblTimeEndMeetingList);
            imgPopupMenuMeeting = ViewCompat.requireViewById(itemView, R.id.imgPopupMenuMeetingList);
            this.vm = vm;
            imgPopupMenuMeeting.setOnClickListener(this::showPopup);
        }

        private void showPopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            popupMenu.show();
        }

        public void bind(Meeting meeting) {
            lblNameStudentMeeting.setText(meeting.getStudentNameMeeting());
            lblNameCompanyMeeting.setText(meeting.getCompanyNameMeeting());
            lblDateMeeting.setText(meeting.getDateMeeting());
            lblTimeStartMeeting.setText(String.valueOf(meeting.getTimeStart()));
            lblTimeEndMeeting.setText(String.valueOf(meeting.getTimeEnd()));
        }

        private boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuEdit:
                    vm.editMeeting(getItem(getAdapterPosition()));
                    break;
                case R.id.mnuDelete:
                    vm.deleteMeeting(getItem(getAdapterPosition()));
                    break;
                default:
                    return false;
            }
            return true;
        }
    }
}
