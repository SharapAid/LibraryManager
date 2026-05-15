package Model.Entity;

public class Client {
    private String name;
    private String phone;
    private String address;
    private String email;
    private int index;

    public Client(){}

    public Client(String name, String phone, String address, String email, int index){
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
