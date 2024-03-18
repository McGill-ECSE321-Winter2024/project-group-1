package ca.mcgill.ecse321.sportcenter.dto;

import ca.mcgill.ecse321.sportcenter.model.ScheduledActivity;
//import ca.mcgill.ecse321.sportcenter.model.Activity.ClassCategory;

public class ScheduledActivityDto {
    
    private int scheduledActivityId;
    private Date date;
    private Time startTime;
    private Time endTime;

    public ScheduledActivityDto(int scheduledActivityId, Date date, Time startTime, Time endTime) {
        this.scheduledActivityId = scheduledActivityId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        }

    public int getScheduledActivityId() {
        return scheduledActivityId;
    }

    public Date getDate() {
        return date;
    }

    public Time getStartTime() {
        return startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public static ScheduledActivityDto convertToDto(ScheduledActivity scheduledActivity) {
        return new ScheduledActivityDto(scheduledActivity.getScheduledActivityId(), scheduledActivity.getDate(),  scheduledActivity.getStartTime(), scheduledActivity.getEndTime());
    }
}
