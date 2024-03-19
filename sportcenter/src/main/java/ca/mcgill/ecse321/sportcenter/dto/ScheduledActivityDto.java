package ca.mcgill.ecse321.sportcenter.dto;

//import java.util.*;

//import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;


//import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
import ca.mcgill.ecse321.sportcenter.model.Activity;
import ca.mcgill.ecse321.sportcenter.model.Instructor;

public class ScheduledActivityDto {
    
    private int scheduledActivityId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private Instructor instructor;
    private Activity activity;


    public ScheduledActivityDto(int scheduledActivityId, LocalDate date, LocalTime startTime, LocalTime endTime, Instructor instructor, Activity activity) {
        this.scheduledActivityId = scheduledActivityId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.instructor = instructor;
        this.activity = activity;
        }

    public int getScheduledActivityId() {
        return scheduledActivityId;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public Instructor getSupervisor(){
        return instructor;
    }

    public Activity getActivity(){
        return activity;
    }

    public void setScheduledActivityId(Integer scheduledActivityId) {
        this.scheduledActivityId = scheduledActivityId;
    }

    public void setDate(LocalDate date) {
        this.date  = date;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public void setSupervisor(Instructor instructor){
        this.instructor = instructor;
    }

    public void setActivity(Activity activity){
        this.activity = activity;
    }

    public static ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
        return new ScheduledActivityDto(scheduledActivity.getScheduledActivityId(), 
        scheduledActivity.getDate(),  scheduledActivity.getStartTime(), scheduledActivity.getEndTime(),
        scheduledActivity.getSupervisor(), scheduledActivity.getActivity());
    }
}
