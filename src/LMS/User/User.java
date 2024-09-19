package Library_Sandeeb.User;

import org.json.simple.JSONObject;

public class User {
    public int id;
    public String firstName;
    public String lastName;
    public String address;
    public String phoneNumber;
    public String role;

    public User(int id, String firstName, String lastName, String address, String phoneNumber, String role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        if (id > 0) {
            this.id = id;
        } else {
            System.out.println("Please enter a valid ID number!!");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber.length() > 10) {
            this.phoneNumber = phoneNumber;
        } else {
            System.out.println("Please enter a valid phone number!!");
        }
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public JSONObject toJSON() {
        JSONObject userObj = new JSONObject();
        userObj.put("id", id);
        userObj.put("firstName", firstName);
        userObj.put("lastName", lastName);
        userObj.put("address", address);
        userObj.put("phoneNumber", phoneNumber);
        userObj.put("role", role);
        return userObj;
    }

    @Override
    public String toString() {
        String roleText = (role != null) ? role.toUpperCase() : "UNKNOWN ROLE";
        String idLabel = role.equalsIgnoreCase("Teacher") ? "Teacher ID" : "Student ID";

        return "<html>Role: " + roleText + "<br>" + idLabel + ": " + id +
                "<br>First Name: " + firstName + "<br>Last Name: " + lastName +
                "<br>Address: " + address + "<br>Phone Number: " + phoneNumber + "</html>";
    }
}