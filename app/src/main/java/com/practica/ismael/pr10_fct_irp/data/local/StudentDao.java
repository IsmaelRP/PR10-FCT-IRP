package com.practica.ismael.pr10_fct_irp.data.local;

import com.practica.ismael.pr10_fct_irp.base.BaseDao;
import com.practica.ismael.pr10_fct_irp.data.local.model.NextMeeting;
import com.practica.ismael.pr10_fct_irp.data.local.model.Student;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import static android.graphics.BlurMaskFilter.Blur.INNER;


@Dao
public interface StudentDao extends BaseDao<Student> {

    @Query("SELECT * FROM Student WHERE idStudent = :studentId")
    LiveData<Student> queryStudent(long studentId);

    @Query("SELECT * FROM Student ORDER BY nameStudent")
    LiveData<List<Student>> queryStudents();

}