package com.practica.ismael.pr10_fct_irp.ui.companies.listCompany;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.PopupMenu;
import com.practica.ismael.pr10_fct_irp.R;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.squareup.picasso.Picasso;
import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CompaniesAdapter extends ListAdapter<Company, CompaniesAdapter.ViewHolder> {

    private final CompaniesViewModel vm;

    public CompaniesAdapter(CompaniesViewModel vm) {
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
        this.vm = vm;
    }



    @NonNull
    @Override
    public CompaniesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CompaniesAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.fragment_list_companies, parent, false), vm);
    }


    @Override
    public void onBindViewHolder(@NonNull CompaniesAdapter.ViewHolder holder, int position) {
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

    class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView lblNameCompany;
        private final TextView lblAddressCompany;
        private final ImageView imgLogoCompany;
        private final Picasso picasso = Picasso.get();
        private final ImageView imgPopupMenu;
        private final CompaniesViewModel vm;

        ViewHolder(View itemView, CompaniesViewModel vm) {
            super(itemView);
            lblNameCompany = ViewCompat.requireViewById(itemView, R.id.lblNameCompanyList);
            lblAddressCompany = ViewCompat.requireViewById(itemView, R.id.lblAddressCompanyList);
            imgLogoCompany = ViewCompat.requireViewById(itemView, R.id.imgLogoCompanyList);
            imgPopupMenu = ViewCompat.requireViewById(itemView, R.id.imgPopupMenuCompanyList);
            this.vm = vm;
            imgPopupMenu.setOnClickListener(this::showPopup);
        }

        private void showPopup(View v) {
            PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
            popupMenu.inflate(R.menu.popup_menu);
            popupMenu.setOnMenuItemClickListener(this::onMenuItemClick);
            popupMenu.show();
        }


        public void bind(Company company) {
            lblNameCompany.setText(company.getNameCompany());
            lblAddressCompany.setText(company.getAddressCompany());
            picasso.load(company.getLogoCompany())
                    .placeholder(R.drawable.ic_empty_logo_black_24dp)
                    .error(R.drawable.ic_empty_logo_black_24dp)
                    .into(imgLogoCompany);
        }

        private boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.mnuEdit:
                    vm.editCompany(getItem(getAdapterPosition()));
                    break;
                case R.id.mnuDelete:
                    vm.deleteCompany(getItem(getAdapterPosition()));
                    break;
                default:
                    return false;
            }
            return true;
        }

    }

}
