package com.example.alexey.sqlitemasterdetail;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
  * Фрагмент, представляющий единый экран детализации элемента.
  * Этот фрагмент либо содержится в {@link PublishersListActivity}
  * в двухпанельном режиме (на планшетах) или {@link TitlesDetailActivity}
  * на телефонах.
  */
public class TitlesDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    /**
      * Аргумент фрагмента, представляющий идентификатор элемента, который этот фрагмент
      * представляет.
      */
    public static final String ARG_ITEM_ID = "item_id";

    private int _pubId;
    private DatabaseAdapter _dbAdapter;
    private TitlesCursorAdapter _adapter;

    /**
      * Обязательный пустой конструктор для менеджера фрагментов для создания экземпляра
      * (например, при изменении ориентации экрана).
      */
    public TitlesDetailFragment() {}


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            _pubId = getArguments().getInt(ARG_ITEM_ID);

            Activity activity = getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);;
            if (appBarLayout != null) {
                appBarLayout.setTitle("Publishers");
            } // if
        } // if
    }

    /**
     * Запускается при создании Loader
     * @param i      - номер лоадера
     * @param bundle - параметры для лоадера
     * */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new TitlesLoader(getActivity(), _dbAdapter, _pubId);
    }
    /** Запускается после окончания загрузки данных в курсор*/
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        _adapter.setCursor(cursor);
        _adapter.notifyDataSetChanged();
    }
    /** Запускается после уничтожения курсора*/
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        _adapter.setCursor(null);
        _adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        _dbAdapter = DatabaseAdapter.getInstance(getActivity());
        _dbAdapter.open();
        getLoaderManager().initLoader(0, null, this);

        RecyclerView rv = rootView.findViewById(R.id.item_detail);
        _adapter = new TitlesCursorAdapter(getActivity(), null);
        rv.setAdapter(_adapter);

        return rootView;
    }
} // TitlesDetailFragment