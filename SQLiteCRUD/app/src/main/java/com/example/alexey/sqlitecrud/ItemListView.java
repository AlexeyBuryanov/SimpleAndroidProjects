package com.example.alexey.sqlitecrud;

import java.util.Comparator;
import java.util.Objects;

/**
 * Created by Alexey on 31.01.2018.
 * Элемент нашего списка ListView.
 */
public class ItemListView {

    private long _id;
    private String _firstName;
    private String _middleName;
    private String _address;
    private String _gender;

    public ItemListView(long id, String firstName, String middleName, String address, String gender) {
        _id = id;
        _firstName = firstName;
        _middleName = middleName;
        _address = address;
        _gender = gender;
    }
    public ItemListView(String firstName, String middleName, String address, String gender) {
        _firstName = firstName;
        _middleName = middleName;
        _address = address;
        _gender = gender;
    }

    public long get_id() { return _id; }
    public void set_id(long id) { _id = id; }

    public String get_firstName() { return _firstName; }
    public void set_firstName(String firstName) { _firstName = firstName; }

    public String get_middleName() { return _middleName; }
    public void set_middleName(String middleName) { _middleName = middleName; }

    public String get_address() { return _address; }
    public void set_address(String address) { _address = address; }

    public String get_gender() { return _gender; }
    public void set_gender(String gender) { _gender = gender; }

    public static final Comparator<ItemListView> COMPARE_BY_ID_ASC = (o1, o2) -> {
        if (o1.get_id() == o2.get_id()) return 0;
        else if (o1.get_id() > o2.get_id()) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_ID_DESC = (o1, o2) -> {
        if (o1.get_id() == o2.get_id()) return 0;
        else if (o1.get_id() < o2.get_id()) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_FIRSTNAME_ASC = (o1, o2) -> {
        if (Objects.equals(o1.get_firstName(), o2.get_firstName())) return 0;
        else if (o1.get_firstName().compareTo(o2.get_firstName()) >= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_FIRSTNAME_DESC = (o1, o2) -> {
        if (Objects.equals(o1.get_firstName(), o2.get_firstName())) return 0;
        else if (o1.get_firstName().compareTo(o2.get_firstName()) <= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_MIDDLENAME_ASC = (o1, o2) -> {
        if (Objects.equals(o1.get_middleName(), o2.get_middleName())) return 0;
        else if (o1.get_middleName().compareTo(o2.get_middleName()) >= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_MIDDLENAME_DESC = (o1, o2) -> {
        if (Objects.equals(o1.get_middleName(), o2.get_middleName())) return 0;
        else if (o1.get_middleName().compareTo(o2.get_middleName()) <= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_ADDRESS_ASC = (o1, o2) -> {
        if (Objects.equals(o1.get_address(), o2.get_address())) return 0;
        else if (o1.get_address().compareTo(o2.get_address()) >= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_ADDRESS_DESC = (o1, o2) -> {
        if (Objects.equals(o1.get_address(), o2.get_address())) return 0;
        else if (o1.get_address().compareTo(o2.get_address()) <= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_GENDER_ASC = (o1, o2) -> {
        if (Objects.equals(o1.get_gender(), o2.get_gender())) return 0;
        else if (o1.get_gender().compareTo(o2.get_gender()) >= 0) return 1;
        else return -1;
    };
    public static final Comparator<ItemListView> COMPARE_BY_GENDER_DESC = (o1, o2) -> {
        if (Objects.equals(o1.get_gender(), o2.get_gender())) return 0;
        else if (o1.get_gender().compareTo(o2.get_gender()) <= 0) return 1;
        else return -1;
    };
} // ItemListView