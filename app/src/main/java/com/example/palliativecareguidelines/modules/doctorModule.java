package com.example.palliativecareguidelines.modules;


public class doctorModule {
    private String fullname,birthdate,address,email,phone,password,confirmPass;

    public doctorModule(String fullname, String address, String birthdate, String email, String phone, String password, String confirmPass) {
        this.fullname = fullname;
        this.address = address;
        this.birthdate = birthdate;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.confirmPass = confirmPass;
    }
    public  doctorModule() {

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPass() {
        return confirmPass;
    }

    public void setConfirmPass(String confirmPass) {
        this.confirmPass = confirmPass;
    }
}