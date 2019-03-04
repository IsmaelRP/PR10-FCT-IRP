package com.practica.ismael.pr10_fct_irp.data.local;

import com.practica.ismael.pr10_fct_irp.base.BaseDao;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CompanyDao extends BaseDao<Company> {

    @Query("SELECT * FROM Company WHERE idCompany = :companyId")
    LiveData<Company> queryCompany(long companyId);

    @Query("SELECT * FROM Company ORDER BY nameCompany")
    LiveData<List<Company>> queryCompanies();



}