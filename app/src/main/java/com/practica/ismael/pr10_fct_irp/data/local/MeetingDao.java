package com.practica.ismael.pr10_fct_irp.data.local;

import com.practica.ismael.pr10_fct_irp.base.BaseDao;
import com.practica.ismael.pr10_fct_irp.data.local.model.Meeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface MeetingDao extends BaseDao<Meeting> {

    @Query("SELECT * FROM Meeting WHERE idMeeting = :meetingId")
    LiveData<Meeting> queryMeeting(long meetingId);

    @Query("SELECT * FROM Meeting ORDER BY dateMeeting")
    LiveData<List<Meeting>> queryMeetings();

    @Query("SELECT m.idMeeting, s.nameStudent, s.idStudent, c.nameCompany, c.idCompany, m.dateMeeting, m.timeStart, m.timeEnd FROM Student s INNER JOIN Company c ON c.idCompany = s.companyStudent LEFT JOIN Meeting m ON m.studentMeeting = s.idStudent GROUP BY s.idStudent ORDER BY m.dateMeeting")
    LiveData<List<NextMeeting>> queryNextMeetings();
}
