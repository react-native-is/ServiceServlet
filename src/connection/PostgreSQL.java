package connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class PostgreSQL {
	private static String userName = "admin";
	private static String passWord = "admin";
	private static String databaseName = "TaskManager";
	private static String URL = "jdbc:postgresql://127.0.0.1:5432/"+databaseName;
	
	private Connection connection ;
	private String className = this.getClass().getSimpleName().toString();
	
	public PostgreSQL() {
		super();
		
		//Khởi tạo connection
		this.connection = this.connect();
	}
	public Connection connect(){
		Connection conn = null;
		try {
			
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection(URL,userName,passWord);
			
		} catch (ClassNotFoundException e) {
			System.out.println("Error when select driver for server PostgreSQL");
			e.printStackTrace();
		} catch (SQLException e) {
			System.out.println("Error when connect server PostgreSQL");
			e.printStackTrace();
		}
		return conn;
	}
	public ResultSet getQuery(String sql){
		ResultSet result = null;
		System.out.println(className + " method getQuery - query : \n" + sql);
		try {
			Statement statement = connection.createStatement();
			result = statement.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Loi khi truy van cau lenh : \n"+sql);
		}
		return result;
	}
	public int updateQuery(String sql){
		int changes = 0;
		System.out.println(className + " method updateQuery - query : \n" + sql);
		try {
			Statement statement = connection.createStatement();
			changes = statement.executeUpdate(sql);
			
			//System.out.println(changes);
		} catch (SQLException e) {
			System.out.println("Loi khi truy van cau lenh : \n"+sql);
			changes = 0;
		}
		return changes;
	}
}
