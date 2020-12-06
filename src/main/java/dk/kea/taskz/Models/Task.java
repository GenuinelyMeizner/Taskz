package dk.kea.taskz.Models;

import dk.kea.taskz.Models.Enums.Complexity;
import dk.kea.taskz.Models.Enums.Status;
import dk.kea.taskz.Models.Enums.Priority;

import java.time.LocalDate;

public class Task
{
    private int taskId;
	private int parentSubProjectId;
    private String taskName;
	private LocalDate deadline;
	private double estimatedTime;
	private Enum<Status> status;
	private Enum<Priority> priority;
	private Enum<Complexity> complexity;
	private String member;
	private String tag; 
	private int isDifficult;

	public Task() {

	}

    public Task(int taskId, int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity,
				LocalDate deadline, double estimatedTime, Enum<Status> status, String member, String tag, int isDifficult)
    {
        this.taskId = taskId;
        this.parentSubProjectId = parentSubProjectId;
        this.taskName = taskName;
        this.deadline = deadline;
        this.estimatedTime = estimatedTime;
        this.status = status;
        this.priority = priority;
        this.complexity = complexity;
        this.member = member;
		this.tag = tag;
		this.isDifficult = isDifficult;
        
	}


	public Task(int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
				 Enum<Status> status, String member)
{
	this.taskId = taskId;
	this.parentSubProjectId = parentSubProjectId;
	this.taskName = taskName;
	this.deadline = deadline;
	this.estimatedTime = estimatedTime;
	this.status = status;
	this.priority = priority;
	this.complexity = complexity;
	this.member = member;
}

	public Task(int parentSubProjectId, String taskName, Enum<Priority> priority, Enum<Complexity> complexity, LocalDate deadline, double estimatedTime,
				Enum<Status> status, String member, String tag)
	{
		this.taskId = taskId;
		this.parentSubProjectId = parentSubProjectId;
		this.taskName = taskName;
		this.deadline = deadline;
		this.estimatedTime = estimatedTime;
		this.status = status;
		this.priority = priority;
		this.complexity = complexity;
		this.member = member;
		this.tag = tag;
		
	}



	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public LocalDate getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	public double getEstimatedTime() {
		return estimatedTime;
	}

	public void setEstimatedTime(double estimatedTime) {
		this.estimatedTime = estimatedTime;
	}

	public Enum<Status> getStatus() {
		return status;
	}

	public void setStatus(Enum<Status> status) {
		this.status = status;
	}

	public Enum<Priority> getPriority() {
		return priority;
	}

	public void setPriority(Enum<Priority> priority) {
		this.priority = priority;
	}

	public Enum<Complexity> getComplexity() {
		return complexity;
	}

	public void setComplexity(Enum<Complexity> complexity) {
		this.complexity = complexity;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}

	public int getParentSubProjectId() {
		return parentSubProjectId;
	}

	public void setParentSubProjectId(int parentSubProjectId) {
		this.parentSubProjectId = parentSubProjectId;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		tag = tag;
	}

	public int getIsDifficult() {
		return isDifficult;
	}

	@Override
	public String toString() {
    	return "Sub project id: " + parentSubProjectId + "\nTask name: " + taskName + "\nPrio: " + priority + "\nComplexity: " + complexity + "\ndeadline: " + deadline + "\nestimatidtime: " + estimatedTime + "\nStatus: " + status;
	}
}


