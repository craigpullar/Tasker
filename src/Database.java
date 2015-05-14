import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Database {

	private Connection c;
	private Statement statement;
	static final String JDBC_DRIVER = "org.sqlite.JDBC";  
	static final String DB_URL = "jdbc:sqlite:tasker.db";
	private ArrayList<Task> tasks;
	private ArrayList<String> projects;
	private Task task;
	
	public Database() {
		try {
			Class.forName(JDBC_DRIVER);
			c = DriverManager.getConnection(DB_URL);
			tasks = new ArrayList<Task>();
			projects = new ArrayList<String>();
			} 
		catch (Exception e) {
			e.printStackTrace();
			}
		
		try {
			statement = c.createStatement();
			String sql = "CREATE TABLE Projects " +
						"(ID INTEGER PRIMARY KEY NOT NULL," +
						"Name TEXT NOT NULL," +
						"Size INT NOT NULL)";
			statement.executeUpdate(sql);
			sql = "CREATE TABLE Tasks" +
						"(ID INTEGER PRIMARY KEY NOT NULL," +
						"Name TEXT NOT NULL," +
						"Project TEXT NOT NULL)";
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public ArrayList<Task> loadTasks(String project) {
		String sql = "SELECT * FROM Tasks WHERE Project = '" + project + "';";
		try {
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				String taskName = result.getString("Name");
				task = new Task(taskName,"Project");
				tasks.add(task);
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tasks;

	}
	
	public ArrayList<String> loadProjects() {
		String sql = "SELECT * FROM Projects;";
		try {
			ResultSet result = statement.executeQuery(sql);
			while (result.next()) {
				String name = result.getString("Name");
				this.projects.add(name);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return projects;
	}
	
	public void addTask(Task task) {
			String sql = "INSERT INTO Tasks (ID,Name,Project) VALUES (null,'" + task.getName() + "','" + task.getProject() + "');";
			try {
				this.statement.executeUpdate(sql);
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	
	public void deleteTask(Task task){
		String sql = "DELETE FROM Tasks WHERE Name = '" + task.getName() + "';";
		try {
			this.statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void addProject(String projectName) {
		String sql = "INSERT INTO Projects (ID,Name) VALUES (null,'" + projectName + "');";
		try {
			this.statement.executeUpdate(sql);
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void deleteProject(String projectName){
		String sql = "DELETE FROM Projects WHERE Name = '" + projectName + "';";
		try {
			this.statement.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sql = "DELETE FROM Tasks WHERE Project = '" + projectName + "';";
		try {
			this.statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exit () {
		try {
			statement.close();
			c.commit();
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
