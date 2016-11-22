package model;


public class Task {
	private int _ID;
	private String _Name ;
	private String _Date;
	private boolean _isDone;
	
	public static String table_task = "task";
	public static String col_id = "idserial";
	public static String col_name = "taskname";
	public static String col_isDone = "done";
	public static String col_date = "date";
	
	public Task() {
		super();
	}
	public int getID() {
		return _ID;
	}
	public void setID(int iD) {
		_ID = iD;
	}
	public String getName() {
		return _Name;
	}
	public void setName(String name) {
		_Name = name;
	}
	public String getDate() {
		return _Date;
	}
	public void setDate(String date) {
		_Date = date;
	}
	public boolean isDone() {
		return _isDone;
	}
	public void setDone(boolean isDone) {
		this._isDone = isDone;
	}
	@Override
	public String toString() {
		return "Task [ID=" + _ID + ", Name=" + _Name + ", Date=" + _Date + ", isDone=" + _isDone + "]";
	}
	public String getUpdateQuery(){
		String update = null;
		if(this._ID < 1 || this._Name == null || this._Date == null){
			update = null;
		}else{
			update = "";
			update = "UPDATE "+ table_task;
			update += "\nSET " + this.col_name + " = '" + this._Name + "' , ";
			update += this.col_date + " = '" + this._Date + "' , ";
			update += this.col_isDone + " = " + this._isDone +"\n";
			update += "WHERE " + this.col_id + " = " + this._ID;
		}
		//System.out.println("cau update la : "+ update);
		return update;
	}
	
	
}
