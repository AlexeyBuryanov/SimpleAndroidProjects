package com.example.alexey.sqlitemasterdetail;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

/**
  * Активность, представляющая отдельный экран детализации элемента. Эта
  * активность используется только на узкополосных устройствах. На устройствах размером планшета,
  * детали элемента представлены бок о бок со списком предметов в {@link PublishersListActivity}.
  */
public class TitlesDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        setSupportActionBar(findViewById(R.id.detail_toolbar));
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view ->
                Snackbar.make(view, "TitlesDetailActivity action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show());

        // Показывать кнопку «Вверх» в панели действий.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        } // if
        // savedInstanceState не имеет значения null, когда существует состояние фрагмента
        // сохраниение идёт из предыдущих конфигураций этой активности
        // (например, при повороте экрана от портрета к пейзажу).
        // В этом случае фрагмент будет автоматически повторно добавлен
        // в его контейнер, поэтому нам не нужно вручную добавлять его.
        // Для получения дополнительной информации см. Руководство по API фрагментов:
        // http://developer.android.com/guide/components/fragments.html
        if (savedInstanceState == null) {
            // Создание фрагмента детали и добавление его в активность с помощью транзакции фрагмента
            Bundle arguments = new Bundle();
            arguments.putString(TitlesDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(TitlesDetailFragment.ARG_ITEM_ID));
            TitlesDetailFragment fragment = new TitlesDetailFragment();
            fragment.setArguments(arguments);
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        } // if
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // Этот идентификатор представляет собой кнопку «Домой» или «Вверх». В случае этого
            // отображается кнопка «Вверх». Подробности см. в схеме навигации в Android Design:
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            navigateUpTo(new Intent(this, PublishersListActivity.class));
            return true;
        } // if
        return super.onOptionsItemSelected(item);
    }
} // TitlesDetailActivity