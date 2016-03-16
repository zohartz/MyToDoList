package mygui.com.zohar.mytodolist;

/**
 * Created by zohar on 16/03/2016.
 */
public class Task {

    private int _id;
    private String _category;
    private String _priority;
    private String _location;
    private String _dueDate;
    private String _description;
    private String _memname;
    private String _status;

    public Task(){
    }

    public Task(int _id,String _category ,String _priority,String _location,String _description,String _memname ){
        this._id=_id;
        this._category = _category;
        this._priority=_priority;
        this._location=_location;
        this._description=_description;
        this._memname=_memname;
        this._status="waiting";

    }

    public Task(String _category ,String _priority,String _location,String _description,String _memname ){

        this._category = _category;
        this._priority=_priority;
        this._location=_location;
        this._description=_description;
        this._memname=_memname;
        this._status="waiting";
    }

  /*  public Task(String category ,String priority,String location, String  description  ){
        this._category = category;
        this._priority=priority;
        this._location=location;
        this._description=description;
    }*/




    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String get_category() {
        return _category;
    }

    public void set_category(String _category) {
        this._category = _category;
    }

    public String get_priority() {
        return _priority;
    }

    public void set_priorityy(String _priority) {
        this._priority = _priority;
    }

    public String get_location() {
        return _location;
    }

    public void set_location(String _location) {
        this._location = _location;
    }

    public String get_dueDate() {
        return _dueDate;
    }

    public void set_dueDate(String _dueDate) {
        this._dueDate = _dueDate;
    }

    public String get_memName() {
        return _memname;
    }

    public void set_memName(String _teamName) {
        this._memname = _teamName;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }


}