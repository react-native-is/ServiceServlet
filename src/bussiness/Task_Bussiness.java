package bussiness;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import connection.PostgreSQL;
import model.Task;

public class Task_Bussiness {
	private Task task;
	private PostgreSQL postgreSQL;

	public Task_Bussiness() {
		super();
		task = new Task();
		postgreSQL = new PostgreSQL();
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	/*
	 * Xử lý nghiệp vụ
	 */

	public ArrayList<Task> getAllTask() {
		ArrayList<Task> tasks = new ArrayList<Task>();
		Task task = new Task();

		String sql = "SELECT * FROM " + Task.table_task;
		sql += "\nORDER BY " + Task.col_id ;

		ResultSet result = postgreSQL.getQuery(sql);

		int count = 0;

		try {
			if (result == null) {
				System.out.println("Loi khi truy van getAllTask");
				return null;
			} else {
				while (result.next()) {
					count++;
					// Get data from the current row and use it
					int id = result.getInt(Task.col_id);
					String taskName = result.getString(Task.col_name);
					String date = result.getString(Task.col_date);
					boolean isDone = result.getBoolean(Task.col_isDone);

					// set data to task
					task = new Task();
					
					task.setID(id);
					task.setName(taskName);
					task.setDate(date);
					task.setDone(isDone);

					// add task to arraylist tasks
					tasks.add(task);
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			return null;
		}

		if (count == 0) {
			System.out.println("No records found");
			return null;
		}

		return tasks;
	}

	public boolean updateTask() {
		int result = 0;
		String update = task.getUpdateQuery();
		result = postgreSQL.updateQuery(update);
		
		System.out.println("Ket qua update : "+result);
		
		return result > 0 ? true : false ;
	}
	
	public Task getTaskByID(int id){
		Task task = new Task();
		
		task.setID(id);
		
		String sql = "SELECT * FROM " + Task.table_task ;
		sql += " WHERE "+Task.col_id + " = " + id;
		
		ResultSet result = postgreSQL.getQuery(sql);

		int count = 0;

		try {
			if (result == null) {
				System.out.println("Loi khi truy van getAllTask");
				return null;
			} else {
				while (result.next()) {
					++count;
					// Get data from the current row and use it
					String taskName = result.getString(Task.col_name);
					String date = result.getString(Task.col_date);
					boolean isDone = result.getBoolean(Task.col_isDone);

					// set data to task
					task.setName(taskName);
					task.setDate(date);
					task.setDone(isDone);
				}
			}
		} catch (SQLException e) {
			// e.printStackTrace();
			System.out.println("Loi khi lay Task theo ID");
			return null;
		}

		if (count == 0) {
			System.out.println("No records found");
			return null;
		}
		return task;
	}
	/*
	 * Kết thúc xử lý nghiệp vụ
	 */

}
