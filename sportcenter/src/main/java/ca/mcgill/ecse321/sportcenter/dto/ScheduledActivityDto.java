package ca.mcgill.ecse321.sportcenter.dto;

import java.util.*;

import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.time.LocalTime;

import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;
import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
//import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

public class ScheduledActivityDto {
    
    private int scheduledActivityId;
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;

    public ScheduledActivityDto(int scheduledActivityId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        this.scheduledActivityId = scheduledActivityId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
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

    public static ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
        return new ScheduledActivityDto(scheduledActivity.getScheduledActivityId(), scheduledActivity.getDate(),  scheduledActivity.getStartTime(), scheduledActivity.getEndTime());
    }
}
