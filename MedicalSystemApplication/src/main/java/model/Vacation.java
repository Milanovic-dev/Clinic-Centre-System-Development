package model;

import java.util.Date;

import javax.persistence.*;

@Entity
public class Vacation 
{
	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "startDate", nullable = false)
	private Date startDate;
	
	@Column(name = "endDate", nullable = false)
	private Date endDate;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
	
	public Vacation() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Vacation(Date startDate, Date endDate, User vacationUser) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.user = vacationUser;
	}
	
	public Vacation(VacationRequest request)
	{
		this.startDate = request.getDate();
		this.endDate = request.getEnd();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
