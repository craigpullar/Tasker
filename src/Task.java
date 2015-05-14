import java.util.Date;

import javax.swing.JCheckBox;
import javax.swing.JTextArea;


public class Task {
	
	private String name;
	private boolean active;
	private Date dueDate;
	private Date addDate;
	public JCheckBox doneCheckBox;
	public JTextArea displayArea;
	private int locationX;
	private int locationY;
	private String project;
	
	
	public Task(String name, JCheckBox doneCheckBox, JTextArea displayArea, String project){
		this.setName(name);
		this.setActive(true);
		this.doneCheckBox = doneCheckBox;
		this.displayArea = displayArea;
		this.setProject(project);
	}
	
	public Task(String name, String project){
		this.setName(name);
		this.setActive(true);
		this.setProject(project);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getAddDate() {
		return addDate;
	}

	public void setAddDate(Date addDate) {
		this.addDate = addDate;
	}

	public int getLocationY() {
		return locationY;
	}

	public void setLocationY(int locationY) {
		this.locationY = locationY;
	}

	public int getLocationX() {
		return locationX;
	}

	public void setLocationX(int locationX) {
		this.locationX = locationX;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

}
