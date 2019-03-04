package com.practica.ismael.pr10_fct_irp.ui.nextmeetings;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;

import org.w3c.dom.Text;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class NextMeetingsAdapter extends ListAdapter<NextMeeting, NextMeetingsAdapter.ViewHolder> {

    private final NextMeetingsViewModel vm;

    public NextMeetingsAdapter(NextMeetingsViewModel vm) {
        super(new DiffUtil.ItemCallback<NextMeeting>() {
            @Override
            public boolean areItemsTheSame(@NonNull NextMeeting oldItem, @NonNull NextMeeting newItem) {
                return oldItem.getIdMeeting() == newItem.getIdMeeting();
            }

            @Override
            public boolean areContentsTheSame(@NonNull NextMeeting oldItem, @NonNull NextMeeting newItem) {
                return TextUtils.equals(oldItem.getNameStudent(), newItem.getNameStudent()) &&
                        TextUtils.equals(oldItem.getNameCompany(), newItem.getNameCompany()) &&
                        TextUtils.equals(oldItem.getDateMeeting(), newItem.getDateMeeting());
            }
        });
        this.vm = vm;
    }

    @NonNull
    @Override
    public NextMeetingsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NextMeetingsAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_next_meetings_list, parent, false), vm);
    }

    @Override
    public void onBindViewHolder(@NonNull NextMeetingsAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdMeeting();
    }

    @Override
    public NextMeeting getItem(int position) {
        return super.getItem(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNameStudentMeetingList;
        private final TextView lblNameCompanyMeetingList;
        private final TextView lblDateMeetingList;
        private final TextView lblTimeStartMeetingList;
        private final TextView lblTimeEndMeetingList;
        private final TextView lblinitialMeeting;
        private final ImageView imgPopupMenuNextMeetingsList;

        private final NextMeetingsViewModel vm;

        ViewHolder(View itemView, NextMeetingsViewModel vm) {
            super(itemView);
            lblNameStudentMeetingList = ViewCompat.requireViewById(itemView, R.id.lblNameStudentMeetingList);
            lblNameCompanyMeetingList = ViewCompat.requireViewById(itemView, R.id.lblNameCompanyMeetingList);
            lblDateMeetingList = ViewCompat.requireViewById(itemView, R.id.lblDateMeetingList);
            lblTimeStartMeetingList = ViewCompat.requireViewById(itemView, R.id.lblTimeStartMeetingList);
            lblTimeEndMeetingList = ViewCompat.requireViewById(itemView, R.id.lblTimeEndMeetingList);
            imgPopupMenuNextMeetingsList = ViewCompat.requireViewById(itemView, R.id.imgPopupMenuNextMeetingsList);
            lblinitialMeeting = ViewCompat.requireViewById(itemView, R.id.lblinitialMeeting);

            imgPopupMenuNextMeetingsList.setOnClickListener(this::showPopup);
            this.vm = vm;
        }

        private void doVisit(boolean initial) {
            if (initial){
                NextMeeting item = getItem(getAdapterPosition());
                item.setDateMeeting("");
                vm.addNextMeeting(item);
            }else{
                vm.addNextMeeting(getItem(getAdapterPosition()));
            }
        }

        private void showPopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            if (lblDateMeetingList.getVisibility() == View.VISIBLE){
                popupMenu.inflate(R.menu.popup_visit_menu);
            }else{
                popupMenu.inflate(R.menu.popup_initial_menu);
            }
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            popupMenu.show();
        }

        public void bind(NextMeeting meeting) {
            lblNameStudentMeetingList.setText(meeting.getNameStudent());
            lblNameCompanyMeetingList.setText(meeting.getNameCompany());
            if (meeting.getDateMeeting() == null || meeting.getDateMeeting().isEmpty()) {
                lblDateMeetingList.setVisibility(View.INVISIBLE);
                lblTimeStartMeetingList.setVisibility(View.INVISIBLE);
                lblTimeEndMeetingList.setVisibility(View.INVISIBLE);
                lblinitialMeeting.setVisibility(View.VISIBLE);
            } else {
                lblDateMeetingList.setText(meeting.getDateMeeting());
                lblTimeStartMeetingList.setText(meeting.getTimeStart());
                lblTimeEndMeetingList.setText(meeting.getTimeEnd());
                lblinitialMeeting.setVisibility(View.INVISIBLE);
            }

        }

        private boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuMeet:
                    doVisit(false);
                    break;
                case R.id.mnuInitial:
                    doVisit(true);
                default:
                    return false;
            }
            return true;
        }

    }
}
