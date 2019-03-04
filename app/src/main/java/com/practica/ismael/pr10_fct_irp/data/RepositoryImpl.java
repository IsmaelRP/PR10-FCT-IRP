package com.practica.ismael.pr10_fct_irp.data;

import android.os.AsyncTask;

import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.local.MeetingDao;
import com.practica.ismael.pr10_fct_irp.data.local.StudentDao;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.CompanyDao;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class RepositoryImpl implements Repository {

    private StudentDao studentDao;
    private CompanyDao companyDao;
    private MeetingDao meetingDao;

    public RepositoryImpl(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    public RepositoryImpl(StudentDao studentDao, CompanyDao companyDao) {
        this.studentDao = studentDao;
        this.companyDao = companyDao;
    }

    public RepositoryImpl(MeetingDao meetingDao, StudentDao studentDao) {
        this.meetingDao = meetingDao;
        this.studentDao = studentDao;
    }

    public RepositoryImpl(CompanyDao companyDao) {
        this.companyDao = companyDao;
    }


    @Override
    public LiveData<List<NextMeeting>> queryNextMeetings() {
        return meetingDao.queryNextMeetings();
    }

    @Override
    public LiveData<List<Student>> queryStudents() {
        return studentDao.queryStudents();
    }

    @Override
    public LiveData<List<Company>> queryCompanies() {
        return companyDao.queryCompanies();
    }

    @Override
    public LiveData<List<Meeting>> queryMeetings() {
        return meetingDao.queryMeetings();
    }

    @Override
    public LiveData<Student> queryStudent(long studentId) {
        return studentDao.queryStudent(studentId);
    }

    @Override
    public LiveData<Company> queryCompany(long companyId) {
        return companyDao.queryCompany(companyId);
    }

    @Override
    public LiveData<Meeting> queryMeeting(long meetingId) {
        return meetingDao.queryMeeting(meetingId);
    }

    public LiveData<Resource<Long>> insertStudent(Student student) {
        MutableLiveData<Resource<Long>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                long id = studentDao.insert(student);
                result.postValue(Resource.success(id));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    public LiveData<Resource<Long>> insertCompany(Company company) {
        MutableLiveData<Resource<Long>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                long id = companyDao.insert(company);
                result.postValue(Resource.success(id));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    public LiveData<Resource<Long>> insertMeeting(Meeting meeting) {
        MutableLiveData<Resource<Long>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                long id = meetingDao.insert(meeting);
                result.postValue(Resource.success(id));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> updateStudent(Student student) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int updated = studentDao.update(student);
                result.postValue(Resource.success(updated));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> updateCompany(Company company) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int updated = companyDao.update(company);
                result.postValue(Resource.success(updated));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> updateMeeting(Meeting meeting) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int updated = meetingDao.update(meeting);
                result.postValue(Resource.success(updated));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> deleteStudent(Student student) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int deleted = studentDao.delete(student);
                result.postValue(Resource.success(deleted));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> deleteCompany(Company company) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int deleted = companyDao.delete(company);
                result.postValue(Resource.success(deleted));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }

    @Override
    public LiveData<Resource<Integer>> deleteMeeting(Meeting meeting) {
        MutableLiveData<Resource<Integer>> result = new MutableLiveData<>();
        AsyncTask.THREAD_POOL_EXECUTOR.execute(() -> {
            result.postValue(Resource.loading());
            try {
                int deleted = meetingDao.delete(meeting);
                result.postValue(Resource.success(deleted));
            } catch (Exception e) {
                result.postValue(Resource.error(e));
            }
        });
        return result;
    }


}