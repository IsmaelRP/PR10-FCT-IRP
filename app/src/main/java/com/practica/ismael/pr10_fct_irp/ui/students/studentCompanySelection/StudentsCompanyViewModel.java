package com.practica.ismael.pr10_fct_irp.ui.students.studentCompanySelection;

import android.app.Application;
import com.practica.ismael.pr10_fct_irp.data.Repository;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import java.util.List;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class StudentsCompanyViewModel extends ViewModel {

    private final Application application;
    private final LiveData<List<Company>> companies;
    private Repository repository;

    StudentsCompanyViewModel(Application application, Repository repository) {
        this.application = application;
        this.repository = repository;
        companies = this.repository.queryCompanies();
    }

    public LiveData<List<Company>> getCompanies() {
        return companies;
    }

    public void selectCompany(){

    }

}
