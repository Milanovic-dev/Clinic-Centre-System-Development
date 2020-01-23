package dto;

import java.util.Date;

import helpers.DateUtil;

public class WeekDTO {

	private String weekStart;
	private String weekEnd;
	
	public WeekDTO(Date weekStart, Date weekEnd)
	{
		this.weekStart = DateUtil.getInstance().getString(weekStart, "dd-MM-yyyy HH:mm");
		this.weekEnd = DateUtil.getInstance().getString(weekEnd, "dd-MM-yyyy HH:mm");
	}
	
	public String getWeekStart() {
		return weekStart;
	}
	public void setWeekStart(String weekStart) {
		this.weekStart = weekStart;
	}
	public String getWeekEnd() {
		return weekEnd;
	}
	public void setWeekEnd(String weekEnd) {
		this.weekEnd = weekEnd;
	}
	
	
}
