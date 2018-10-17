package com.example.alexey.sqlitecrudexpandable;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.ExpandableListView;
import android.widget.Toast;

/**
 * Разработать приложение, которое позволяет просматривать, редактировать, добавлять
 * и удалять (CRUD) данные. Данные отображаются в дереве из двух связанных таблиц
 * (Таблицы спроектировать самостоятельно). Обязательно использовать картинки для групп
 * и дочерних элементов. Реализовать сортировку. Использовать Custom tree adapter.
 * */
public class MainActivity extends AppCompatActivity {

    private ExpandableListView _expandableListView;
    private Toolbar _toolbar;
    private FloatingActionButton _fab;
    ///**раскрывающаяся секция ExpandableListView*/
    //private LinkedHashMap<String, GroupElement> _section = new LinkedHashMap<>();
    ///**список секции ExpandableListView*/
    //private ArrayList<GroupElement> _sectionList = new ArrayList<>();
    //private CustomAdapterExpList _adapterListView;
    private CustomCursorTreeAdapter _adapterListView;
    private DatabaseAdapter _dbAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTitle("ExpandableListView SQLite");
        setContentView(R.layout.activity_main);
        _toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(_toolbar);
        _fab = findViewById(R.id.fab);
        _fab.setOnClickListener(view -> {
            // TODO: Добавление записи
        });
        _expandableListView = findViewById(R.id.expandableListView);
        _dbAdapter = new DatabaseAdapter(getApplicationContext());
        _adapterListView = new CustomCursorTreeAdapter(_dbAdapter.open(), getApplicationContext());
        _expandableListView.setAdapter(_adapterListView);
        _expandableListView.setOnChildClickListener((parent, v, groupPosition, childPosition, id) -> {
            //// получить заголовок группы
            //GroupElement groupElement = _sectionList.get(groupPosition);
            //// получить подэлемент
            //ChildElement childElement =  groupElement.getChildList().get(childPosition);
            //Toast.makeText(getBaseContext(), "Клик по дочернему элементу" + childElement.get_name()
            //        + " " + childElement.get_price(), Toast.LENGTH_LONG).show();
            Cursor cursor = _adapterListView.getChild(groupPosition, childPosition);
            Toast.makeText(getApplicationContext(), cursor.getString(1), Toast.LENGTH_SHORT).show();
            return false;
        });
        _expandableListView.setOnGroupClickListener((parent, v, groupPosition, id) -> {
            //// получить заголовок группы
            //GroupElement groupElement = _sectionList.get(groupPosition);
            //Toast.makeText(getBaseContext(), "Клик по заголовку " + groupElement.get_name(),
            //        Toast.LENGTH_LONG).show();
            Cursor cursor = _adapterListView.getGroup(groupPosition);
            Toast.makeText(getApplicationContext(), cursor.getString(1), Toast.LENGTH_SHORT).show();
            return false;
        });
    } // onCreate

    @Override
    protected void onDestroy() {
        super.onDestroy();
        _dbAdapter.close();
    }

    //private void refresh() {
    //    _dbAdapter = new DatabaseAdapter(getApplicationContext());
    //    _dbAdapter.open();
    //    ArrayList<GroupElement> groups = new ArrayList<>();
    //    ArrayList<Publisher> publishers = _dbAdapter.getPublishers();
    //    publishers.forEach(publisher ->
    //            groups.add(new GroupElement(android.R.drawable.ic_menu_gallery, publisher.get_name(), publisher.get_id())));
    //    ArrayList<Title> titles = _dbAdapter.getTitles();
    //    for (int i = 0; i < groups.size(); i++) {
    //        ArrayList<ChildElement> childs = new ArrayList<>();
    //        Title title = titles.get(i);
    //        if (title != null && title.get_pubId() == groups.get(i).get_pubId()) {
    //            childs.add(new ChildElement(title.get_name(), title.get_price(), title.get_type(), android.R.drawable.ic_menu_gallery));
    //        } // if
    //        groups.get(i).setChildList(childs);
    //        _section.put(groups.get(i).get_name(), groups.get(i));
    //        _sectionList.add(groups.get(i));
    //    } // for i
    //    _adapterListView = new CustomAdapterExpList(getApplicationContext(), groups);
    //    _expandableListView.setAdapter(_adapterListView);
    //    _dbAdapter.close();
    //}

    //private int addToListView(int groupIcon, String groupName, int groupPubId, String childName,
    //                          int childPrice, String childType, int childIcon) {
    //    // проверяем хэш-карту, если группа уже существует
    //    GroupElement sectionGroup = _section.get(groupName);
    //    // добавляем группу, если она не существует
    //    if (sectionGroup == null) {
    //        sectionGroup = new GroupElement(groupIcon, groupName, groupPubId);
    //        _section.put(groupName, sectionGroup);
    //        _sectionList.add(sectionGroup);
    //    } // i
    //
    //    // получить подэлементы группы
    //    ArrayList<ChildElement> childElements = sectionGroup.getChildList();
    //    // размер списка дочерних элементов
    //    int listSize = childElements.size();
    //
    //    // создание нового дочернего элемента и добавить его в группу
    //    ChildElement childElement = new ChildElement(childName, childPrice, childType, childIcon);
    //    childElements.add(childElement);
    //    sectionGroup.setChildList(childElements);
    //
    //    // найти позицию группы внутри списка
    //    int groupPosition = 0;
    //    groupPosition = _sectionList.indexOf(sectionGroup);
    //    return groupPosition;
    //}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuItemExit:
                finish();
                break;
        } // switch

        return super.onOptionsItemSelected(item);
    }
} // MainActivity