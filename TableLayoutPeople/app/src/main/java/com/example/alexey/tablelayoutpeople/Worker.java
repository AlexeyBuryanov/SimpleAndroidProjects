package com.example.alexey.tablelayoutpeople;


/**
 * Created by Alexey on 20.12.2017.
 * Класс представляет человека/рабочего/коллеги.
 */
public class Worker
{
    private String _fullName;
    private String _phone;
    private String _gender;
    private String _address;


    Worker(String fullName, String phone, String gender, String address) {
        set_fullName(fullName);
        set_phone(phone);
        set_gender(gender);
        set_address(address);
    } // Worker ctor


    String get_gender() {
        return _gender;
    } // get_gender
    void set_gender(String gender) {
        _gender = gender;
    } // set_gender


    String get_fullName() {
        return _fullName;
    } // get_fullName
    void set_fullName(String fullName) {
        _fullName = fullName;
    } // set_fullName


    String get_phone() {
        return _phone;
    } // get_phone
    void set_phone(String phone) {
        _phone = phone;
    } // set_phone


    String get_address() {
        return _address;
    } // get_address
    void set_address(String address) {
        _address = address;
    } // set_address
} // Worker class