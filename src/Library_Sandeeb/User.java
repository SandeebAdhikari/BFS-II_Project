package Library_Sandeeb;

import org.json.simple.JSONObject;

public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String phoneNumber;

    public User(int id, String firstName, String lastName, String address, String phoneNumber){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
    public int getId(){
        return id;
    }

    public void setId(int id){
        if(id > 0){
            this.id = id;
        }else{
            System.out.println("Please Enter a valid ID number!!");
        }
    }

    public String getFirstName(){
        return firstName;
    }

    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    public String getLastName(){
        return lastName;
    }

    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }

    public void setPhoneNumber(){
        if(phoneNumber.length() > 10){
            this.phoneNumber  = phoneNumber;
        }else{
            System.out.println("Please enter a valid phone number!!");
        }
    }

    public JSONObject toJSON(){
        JSONObject studentObj = new JSONObject();
        studentObj.put("id", id);
        studentObj.put("firstName", firstName);
        studentObj.put("lastName", lastName);
        studentObj.put("address", address);
        studentObj.put("phoneNumber", phoneNumber);
        return studentObj;
    }

    @Override
    public String toString() {
        return "Student ID: " + id +"\n First Name: "+firstName+"\n Last Name: "+"\n Address: "+address+"\n Phone Number: "+phoneNumber+"\n";
    }
}
