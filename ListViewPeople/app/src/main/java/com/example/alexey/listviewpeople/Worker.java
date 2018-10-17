/**
 * Created by Alexey on 10.12.2017.
 */

package com.example.alexey.listviewpeople;


public class Worker
{
    private String _fullName;
    private String _phone;
    private String _address;


    Worker(String fullName, String phone, String address) {
        _fullName = fullName;
        _phone = phone;
        _address = address;
    } // Worker ctor


    String get_fullName() {
        return _fullName;
    } // get_fullName


    public void set_fullName(String fullName) {
        _fullName = fullName;
    } // set_fullName


    String get_phone() {
        return _phone;
    } // get_phone


    public void set_phone(String phone) {
        _phone = phone;
    } // set_phone


    String get_address() {
        return _address;
    } // get_address


    public void set_address(String address) {
        _address = address;
    } // set_address
} // Worker class