package dto;

import java.util.Date;

import helpers.DateInterval;
import helpers.DateUtil;

public class DateIntervalDTO {

	private String start;
	private String end;
	
	public DateIntervalDTO(DateInterval di, String format)
	{
		this.start = DateUtil.getInstance().getString(di.getStart(), format);
		this.end = DateUtil.getInstance().getString(di.getEnd(), format);
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	
}
