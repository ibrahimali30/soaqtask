package com.ibrahim.soaqtask;

import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibrahim.soaqtask.model.Categories;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CategoriesFragment.OnAddNewFragment, View.OnClickListener {
    private static final String TAG = "MainActivity";

    public static TextView toolbarTitle;
    private ImageView toolbarLogo;
    private ImageButton toolbarImageButton;
    private  FragmentManager fragmentManager;

    //widgets
    Button task001_button , task002_button ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: started");

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbarLogo = toolbar.findViewById(R.id.top_bar_logo);
        toolbarTitle = toolbar.findViewById(R.id.toolbar_title);
        toolbarImageButton = toolbar.findViewById(R.id.toolbar_back);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        handleToolBarSubViews();

        //dispatch current category fragment
        toolbarImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //tasks buttons
        task001_button = findViewById(R.id.task001_button);
        task001_button.setOnClickListener(this);
        task002_button = findViewById(R.id.task002_button);
        task002_button.setOnClickListener(this);

    }

    private void handleToolBarSubViews() {
        //handle toolbar subviews
        fragmentManager.addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                if (fragmentManager.getBackStackEntryCount()>1){
                    toolbarLogo.setVisibility(View.INVISIBLE);
                    toolbarTitle.setVisibility(View.VISIBLE);
                    toolbarImageButton.setVisibility(View.VISIBLE);
                }else {
                    toolbarLogo.setVisibility(View.VISIBLE);
                    toolbarTitle.setVisibility(View.INVISIBLE);
                    toolbarImageButton.setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private void getCategories() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://souq.hardtask.co/app/app.asmx/GetCategories?categoryId=0&countryId=1";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            //calling method to parse jsonArray
                            List<Categories> allCategories = parseJsonObject( new JSONArray(response));
                            //add main catigories
                            onAddFragment(allCategories);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: error "+error);
            }
        });

        queue.add(stringRequest);
    }

    private List<Categories> parseJsonObject(JSONArray response) {

        List<Categories> categoriesList = new ArrayList<>();

        try {
            for (int i=0; i < response.length(); i++) {
                JSONObject jsonObject = response.getJSONObject(i);
                List<Categories> subCategories = new ArrayList<>();
                //recursion
                if (jsonObject.getJSONArray("SubCategories").length()!=0){
                     subCategories =parseJsonObject(jsonObject.getJSONArray("SubCategories"));

                }
                Categories categories = new Categories(
                        jsonObject.getInt("Id"),
                        jsonObject.getString("TitleEN"),
                        jsonObject.getString("TitleAR"),
                        jsonObject.getString("Photo"),
                        jsonObject.getInt("ProductCount"),
                        jsonObject.getInt("HaveModel"),
                        subCategories);

                categoriesList.add(categories);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       return categoriesList;
    }


    @Override
    public void onAddFragment(List<Categories> categories) {
        Log.d(TAG, "onAddFragment: called");

        //add new category fragment
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment fragment = new CategoriesFragment();

        //pass category list to fragment
        Bundle bundle = new Bundle();
        bundle.putString("parent_title" , toolbarTitle.getText().toString());
        bundle.putParcelableArrayList("categories" , (ArrayList<? extends Parcelable>) categories);
        fragment.setArguments(bundle);

        fragmentTransaction.replace(R.id.cat_grid_view , fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.task001_button:
                //start task 001
                getCategories();
                break;
            case R.id.task002_button:

                //add register fragment
                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Fragment registerFragment = new RegisterFragment();
                fragmentTransaction.replace(R.id.register_frame , registerFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

                break;

        }
    }
}
