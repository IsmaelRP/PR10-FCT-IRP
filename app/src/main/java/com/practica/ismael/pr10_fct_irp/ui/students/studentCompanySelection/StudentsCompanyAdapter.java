package com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class StudentsCompanyAdapter extends ListAdapter<Company, StudentsCompanyAdapter.ViewHolder> {

    private final OnSelectionListener onSelectionListener;

    public StudentsCompanyAdapter(OnSelectionListener onSelectionListener) {
        super(new DiffUtil.ItemCallback<Company>() {
            @Override
            public boolean areItemsTheSame(@NonNull Company oldItem, @NonNull Company newItem) {
                return oldItem.getIdCompany() == newItem.getIdCompany();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Company oldItem, @NonNull Company newItem) {
                return TextUtils.equals(oldItem.getNameCompany(), newItem.getNameCompany()) &&
                        TextUtils.equals(oldItem.getAddressCompany(), newItem.getAddressCompany()) &&
                        TextUtils.equals(oldItem.getLogoCompany(), newItem.getLogoCompany());
            }
        });
        this.onSelectionListener = onSelectionListener;
    }

    @NonNull
    @Override
    public StudentsCompanyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentsCompanyAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_selection_companies, parent, false), onSelectionListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentsCompanyAdapter.ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getIdCompany();
    }

    @Override
    public Company getItem(int position) {
        return super.getItem(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private final TextView lblNameCompany;
        private final TextView lblAddressCompany;
        private final ImageView imgLogoCompany;
        private final Picasso picasso = Picasso.get();

        ViewHolder(View itemView, OnSelectionListener onSelectionListener) {
            super(itemView);
            lblNameCompany = ViewCompat.requireViewById(itemView, R.id.lblNameCompanyListSelection);
            lblAddressCompany = ViewCompat.requireViewById(itemView, R.id.lblAddressCompanyListSelection);
            imgLogoCompany = ViewCompat.requireViewById(itemView, R.id.imgLogoCompanyListSelection);


            if (onSelectionListener != null){
                itemView.setOnClickListener(v1 -> onSelectionListener.onClick(getAdapterPosition()));
            }
        }

        public void bind(Company company) {
            lblNameCompany.setText(company.getNameCompany());
            lblAddressCompany.setText(company.getAddressCompany());
            picasso.load(company.getLogoCompany())
                    .placeholder(R.drawable.ic_empty_logo_black_24dp)
                    .error(R.drawable.ic_empty_logo_black_24dp)
                    .into(imgLogoCompany);
        }
    }
}
