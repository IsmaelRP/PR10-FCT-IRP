package com.practica.ismael.pr10_fct_irp.data;

import com.practica.ismael.pr10_fct_irp.base.Resource;
import com.practica.ismael.pr10_fct_irp.data.local.model.Company;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;


@SuppressWarnings({"WeakerAccess", "unused"})
public interface Repository {

    //  Students
    LiveData<List<Student>> queryStudents();
    LiveData<Student> queryStudent(long studentId);
    LiveData<Resource<Long>> insertStudent(Student student);
    LiveData<Resource<Integer>> updateStudent(Student student);
    LiveData<Resource<Integer>> deleteStudent(Student student);

    //  Companies
    LiveData<List<Company>> queryCompanies();
    LiveData<Company> queryCompany(long companyId);
    LiveData<Resource<Long>> insertCompany(Company company);
    LiveData<Resource<Integer>> updateCompany(Company company);
    LiveData<Resource<Integer>> deleteCompany(Company company);

    //  Meetings
    LiveData<List<Meeting>> queryMeetings();
    LiveData<Meeting> queryMeeting(long meetingId);
    LiveData<Resource<Long>> insertMeeting(Meeting meeting);
    LiveData<Resource<Integer>> updateMeeting(Meeting meeting);
    LiveData<Resource<Integer>> deleteMeeting(Meeting meeting);

    //  NextMeetings
    LiveData<List<NextMeeting>> queryNextMeetings();
}