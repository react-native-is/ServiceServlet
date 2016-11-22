package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.el.parser.Token;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import bussiness.Task_Bussiness;
import connection.PostgreSQL;
import model.Task;

/**
 * https://www.mkyong.com/java/how-do-convert-java-object-to-from-json-format-gson-api/
 * use gson of Google to build JSON 
 * user-guide:
 * https://sites.google.com/site/gson/gson-user-guide
 */
/**
 * Servlet implementation class Task_Servlet
 */
@WebServlet("/Task_Servlet")
public class Task_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Task_Bussiness task_Bussiness;

	public Task_Servlet() {
		super();
		task_Bussiness = new Task_Bussiness();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
		// String media = (String) request.getParameter("media");
		// System.out.println(media);

		response.setContentType("application/json");
		PrintWriter writer = response.getWriter();

		Task task = new Task();
		Gson gson = new Gson();

		ArrayList<Task> tasks = task_Bussiness.getAllTask();
		JsonElement arrayTask;
		JsonObject jsonObject = new JsonObject();

		// test String return from gson.toJson
		// String test = gson.toJson(tasks, tasks.getClass());

		if (tasks != null) {
			jsonObject = new JsonObject();

			// add simple element to json object key -value
			jsonObject.addProperty("result", "OK");

			// get Array of element use gson.toJsonTree
			arrayTask = gson.toJsonTree(tasks, tasks.getClass());

			// add json element to json object with key and value is array
			jsonObject.add("dataset", arrayTask);
		} else {
			jsonObject = new JsonObject();
			// add simple element to json object key -value
			jsonObject.addProperty("result", "ERROR");
		}

		writer.println(jsonObject.toString());

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		request.setCharacterEncoding("UTF-8");
		
		StringBuffer jsonBuffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader reader = request.getReader();
			while ((line = reader.readLine()) != null) {
				jsonBuffer.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error when get data from request");
		}
		
		String stringData = jsonBuffer.toString();
		
		/*
		 * stringData like this : 
		 * {"action":"update","_ID":1,"_Name":"PhatNguyen edited","_Date":"21-11-2016","_isDone":true}
		 */
		
		JsonObject jsondata = new JsonObject();

		JsonParser parser = new JsonParser();
		jsondata = parser.parse(stringData).getAsJsonObject();

		String action = jsondata.get("action").getAsString();
		int _ID = jsondata.get("_ID").getAsInt();
		String _Name = jsondata.get("_Name").getAsString();
		String _Date = jsondata.get("_Date").getAsString();
		Boolean _isDone = jsondata.get("_isDone").getAsBoolean();

		/*
		 * System.out.println("Data lay duoc :\n"+ "action : " + action);
		 * System.out.println("_Date : " + _Date); System.out.println("_ID : " +
		 * _ID); System.out.println("_Name : " + _Name);
		 * System.out.println("_isDone : " + _isDone);
		 */
		Task task = new Task();
		task.setID(_ID);
		task.setName(_Name);
		task.setDate(_Date);
		task.setDone(_isDone);
		
		task_Bussiness.setTask(task);
		
		Boolean result = task_Bussiness.updateTask();
		JsonObject object = new JsonObject();
		object.addProperty("result", result);
		
		response.getWriter().println(object.toString());
	}

}
