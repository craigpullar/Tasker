import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class TaskFrame extends JFrame{
	

	private JPanel panel;
	private JTextArea displayArea;
	private JTextField inputField;
	private JButton addTaskButton;
	private JButton newTaskButton;
	private JButton newProjectButton;
	private JButton deleteProjectButton;
	private JPopupMenu settingsMenu;
	private JCheckBox doneCheckBox;
	private JComboBox projectSelector;
	private int frameHeight;
	private int frameWidth;
	private ArrayList<Task> tasks;
	private ArrayList<String> projects;
	private Database database;
	private String inputToggle;
	
	//--[[INITIATE]]--\\
	public TaskFrame(){
		//Set up Panel
		this.frameHeight = 500;
		this.frameWidth = 300;
		this.database = new Database();
		this.projects = this.database.loadProjects();
		
		
		this.setResizable(false);
		this.panel = new JPanel();
		this.setTitle("Tasker");
		this.setContentPane(this.panel);
		this.setPreferredSize(new Dimension(this.frameWidth,this.frameHeight));
	    this.setMinimumSize(new Dimension(this.frameWidth,this.frameHeight));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.panel.setLayout(null);
		this.panel.setBackground(Color.white);
		
		//Draw Objects
		this.drawTaskInput();
		this.drawAddTaskButton();
		this.drawProjectSelector();
		this.drawNewTaskButton();
		this.drawNewProjectButton();
		this.drawSettingsMenu();
		this.drawTasks();

		//Show Panel
		this.pack();
		this.setVisible( true );

	}
	
	//*--[[DRAW FUNCTIONS]]--*\\
	public void drawTaskInput() {
		int height = 25;
		int width = 220;
		int locationX = 5;
		int locationY = 430;
		
		this.inputField = new JTextField();
		this.inputField.setSize(width,height);
		this.inputField.setLocation(locationX, locationY);
		this.inputField.setVisible(false);
		this.pack();
		this.panel.add(this.inputField);
	}
	
	public void drawSettingsMenu() {
		this.settingsMenu = new JPopupMenu();
		JMenuItem m = new JMenuItem("Add Project");
		m.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				toggleProjectInput();
			}
		});
		this.settingsMenu.add(m);
		this.settingsMenu.addSeparator();
		m = new JMenuItem("Delete Project");
		m.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				deleteProject();			
			}
		});
		this.settingsMenu.add(m);
		this.settingsMenu.setLocation(this.getLocationOnScreen());
		this.settingsMenu.setSize(50,50);
		this.pack();
		this.panel.add(this.settingsMenu);
	}
	
	public void drawNewTaskButton() {
		int height = 20;
		int width = 20;
		int locationX = 160;
		int locationY = 5;
		
		this.newTaskButton = new JButton();
		try {
		    Image img = ImageIO.read(getClass().getResource("rec/Add.bmp"));
		    this.newTaskButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		this.newTaskButton.setSize(width,height);
		this.newTaskButton.setLocation(locationX,locationY);
		this.newTaskButton.setBorderPainted(false);
		this.newTaskButton.setVisible(true);
		this.newTaskButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				toggleTaskInput();
			}
		});
		this.pack();
		this.panel.add(this.newTaskButton);
	}
	
	public void drawNewProjectButton() {
		int height = 20;
		int width = 20;
		int locationX = 130;
		int locationY = 5;
		
		this.newProjectButton = new JButton();
		try {
		    Image img = ImageIO.read(getClass().getResource("rec/settings.bmp"));
		    this.newProjectButton.setIcon(new ImageIcon(img));
		  } catch (IOException ex) {
		  }
		this.newProjectButton.setSize(width, height);
		this.newProjectButton.setLocation(locationX, locationY);
		this.newProjectButton.setVisible(true);
		this.newProjectButton.setBorderPainted(false);
		this.newProjectButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				if (settingsMenu.isVisible()){
					settingsMenu.setVisible(false);
					if (addTaskButton.isVisible()){
						toggleProjectInput();	
					}

				}
				else {
					settingsMenu.setVisible(true);
				}
			}
		});
		this.pack();
		this.panel.add(this.newProjectButton);
	}
	
	public void drawAddTaskButton() {
		int height = 25;
		int width = 50;
		int locationX = 230;
		int locationY = 430;
		
		this.addTaskButton= new JButton();
		this.addTaskButton.setSize(width, height);	
		this.addTaskButton.setLocation(locationX, locationY);
		this.addTaskButton.setFont( new Font("dialog",1,8));
		this.addTaskButton.setText("Add");
		this.addTaskButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt) {
				String toggle = getInputToggle();
				if (toggle == "task") {
					addTask();
				}
				else {
					addProject();
				}

			}
		});
		this.addTaskButton.setVisible(false);
		this.pack();
		this.panel.add(this.addTaskButton);
	
	}
	
	public void drawProjectSelector() {
		int height = 20;
		int width = 110;
		int locationY = 5;
		int locationX = 5;
		
		this.projectSelector = new JComboBox();
		this.projectSelector.setSize(width,height);
		this.projectSelector.setLocation(locationX,locationY);
		this.populateProjectSelector();
		this.projectSelector.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
				clearTasks();
				drawTasks();
			}
		});
		this.pack();
		this.panel.add(this.projectSelector);
	}
	
	public void populateProjectSelector() {
		this.projectSelector.removeAllItems();
		if (this.projects.size() > 0) {
			for (int i = 0; i < this.projects.size(); i++) {
				String project = this.projects.get(i);
				this.projectSelector.addItem(project);
			}
		}
	}
	
	public void drawTasks () {
		String project = (String) this.projectSelector.getSelectedItem();
		this.tasks = this.database.loadTasks(project);
		int limit = this.tasks.size();
		for(int i = 0; i < limit; i++){
			Task task = tasks.get(i);
			task.setLocationY(i*25 + 50);
			task.setLocationX(30);
			task.displayArea = new JTextArea();
			this.panel.add(task.displayArea);
			task.displayArea.setSize(280,20);
			task.displayArea.setLocation(task.getLocationX(),task.getLocationY());
			task.displayArea.setText(task.getName());
			this.pack();
			task.displayArea.setVisible(true);
			task.doneCheckBox = new JCheckBox();
			this.panel.add(task.doneCheckBox);
			task.doneCheckBox.setSize(20,20);
			task.doneCheckBox.setLocation(task.getLocationX() - 25, task.getLocationY());
			task.doneCheckBox.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent evt) {
					for (int i = 0; i < tasks.size(); ++i){
						Task task = (Task) tasks.get(i);
						if (task.doneCheckBox.isSelected()) {
							removeTask(i);
						}
					}
				}
			});
			this.pack();
			task.doneCheckBox.setVisible(true);
		}
	}
	
	public void clearTasks() {
		int limit = this.tasks.size();
		for(int i = 0; i < limit; i++){
			Task task = this.tasks.get(i);
			task.displayArea.setVisible(false);
			task.doneCheckBox.setVisible(false);
		}
		this.tasks.removeAll(tasks);
	}
	//End of draw functions\\
	
	
	//--[[TOGGLE FUNCTIONS]]--\\
	public void toggleTaskInput() {
		if (this.addTaskButton.isVisible() && this.inputToggle == "task") {
			this.inputField.setVisible(false);
			this.addTaskButton.setVisible(false);
		}
		else if (this.addTaskButton.isVisible() && this.inputToggle == "project"){
			this.inputToggle = "task";
		}
		else {
			this.inputField.setVisible(true);
			this.addTaskButton.setVisible(true);
			this.inputToggle = "task";
		}
	}
	
	public void toggleProjectInput() {
		if (this.addTaskButton.isVisible() && this.inputToggle == "project") {
			this.inputField.setVisible(false);
			this.addTaskButton.setVisible(false);
		}
		else if (this.addTaskButton.isVisible() && this.inputToggle == "task") {
			this.inputToggle = "project";	
		}
		else {
			this.inputField.setVisible(true);
			this.addTaskButton.setVisible(true);
			this.inputToggle = "project";
		}
	}
	
	public String getInputToggle(){
		return this.inputToggle;
	}
	//End of toggle functions\\
	
	//--[[DATABASE FUNCTIONS]]--\\
	public void addTask() {
		String taskName = this.inputField.getText();
		String project = (String) this.projectSelector.getSelectedItem();
		Task task = new Task(taskName, this.doneCheckBox, this.displayArea,project);
		this.clearTasks();
		this.database.addTask(task);
		this.drawTasks();
		this.inputField.setText("");
		this.toggleTaskInput();
	}
	
	public void addProject() {
		String projectName = this.inputField.getText();
		this.database.addProject(projectName);
		this.projects.add(projectName);
		this.clearTasks();
		this.drawTasks();
		this.populateProjectSelector();
		this.inputField.setText("");
		this.toggleProjectInput();
	}
	
	public void deleteProject() {
		int index = this.projectSelector.getSelectedIndex();
		String project = projects.get(index);
		this.clearTasks();
		this.projects.remove(index);
		this.database.deleteProject(project);
		this.drawTasks();
		this.populateProjectSelector();
	}
	
	public void removeTask(int index) {
		Task task = (Task) tasks.get(index);
		clearTasks();
		this.database.deleteTask(task);
		this.drawTasks();
	}
	//End of database functions\\
	
}
