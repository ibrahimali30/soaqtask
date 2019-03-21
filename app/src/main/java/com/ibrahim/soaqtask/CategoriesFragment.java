package com.ibrahim.soaqtask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.ibrahim.soaqtask.model.Categories;

import java.util.List;

public class CategoriesFragment extends Fragment {
    private static final String TAG = "CategoriesFragment";

    List<Categories> finalCategories;
    CharSequence title;

    private OnAddNewFragment mOnAddNewFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.fragment_categories, container, false);
        Log.d(TAG, "onCreateView: called");

        //get passed categories
        Bundle bundle = getArguments();
        List<Categories> categories = null;
        if (bundle != null) {
            categories = bundle.getParcelableArrayList("categories");
            title = bundle.getString("parent_title");
            Log.d(TAG, "onCreateView: title"+title);
        }
         finalCategories = categories;

        //init cat grid view
        GridView gridview =  view.findViewById(R.id.gridview);
        final List<Categories> finalCategories1 = categories;
        gridview.setAdapter(new CategoriesGrideAdapter(getActivity() , categories));

        //grid item click listener
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //call main activity method throw interface
                mOnAddNewFragment.onAddFragment(finalCategories.get(i).getSubCategories());
                //name tool bar with cat title
                MainActivity.toolbarTitle.setText(finalCategories1.get(i).getTitleAr());
            }
        });
        Log.d(TAG, "onCreateView: passed cat = "+categories);
        return view;
    }




    //public interface between this fragment and main activity to handle adding new cat fragments and select events
    public interface OnAddNewFragment {
        void onAddFragment(List<Categories> categories);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mOnAddNewFragment = (MainActivity) getActivity();
    }
    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: called ");
        //name toolbar with the passed parent title
        MainActivity.toolbarTitle.setText(title);
        super.onDestroy();
    }
}
