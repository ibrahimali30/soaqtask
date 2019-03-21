package com.ibrahim.soaqtask;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ibrahim.soaqtask.model.City;
import com.ibrahim.soaqtask.model.Country;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RegisterFragment extends Fragment {
    private static final String TAG = "RegisterFragment";

    List<Country> mCountryList;
    List<City> mCityList;

    //views
    EditText nameEditText , passEditText ;
    Spinner countrySpinner , citySpinner , codeSpinner;
    Button changeLanButton;
    TextView termsAndConditionsTextView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: called");
        View view = inflater.inflate(R.layout.register_fragment_layout , container , false);

        nameEditText = view.findViewById(R.id.name_edit_text);
        passEditText = view.findViewById(R.id.pass_edit_text);
        codeSpinner = view.findViewById(R.id.spinner1);
        countrySpinner = view.findViewById(R.id.spinner2);
        citySpinner = view.findViewById(R.id.spinner3);

        termsAndConditionsTextView = view.findViewById(R.id.terms_conditions);
        termsAndConditionsTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent termsWebView = new Intent(getActivity() , TermsAndConditionsActivity.class);
                startActivity(termsWebView);
            }
        });

        changeLanButton = view.findViewById(R.id.change_lang);
        changeLanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               changeToOtherLanguage();
            }
        });

        getCountries();

        return view;
    }

    private void getCountries() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://souq.hardtask.co/app/app.asmx/GetCountries";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse: "+response);

                        parseCountriesJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: error "+error);
            }
        });

        queue.add(stringRequest);

    }

    private void parseCountriesJson(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            mCountryList = new ArrayList<>();
            for (int i=0 ; i<jsonArray.length() ; i++){
                JSONObject countryObject = jsonArray.getJSONObject(i);
                Country country = new Country(countryObject.getInt("Id"),
                        countryObject.getString("TitleEN") ,
                        countryObject.getString("TitleAR"));

                mCountryList.add(country);
                Log.d(TAG, "parseCountriesJson: countries "+mCountryList);
                setCountrySpinerAdapter();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void setCountrySpinerAdapter() {
        Log.d(TAG, "setCountrySpinerAdapter: called");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, getCountryList());
        countrySpinner.setAdapter(spinnerArrayAdapter);
        countrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                getCities(mCountryList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getCities(int id) {
        Log.d(TAG, "getCities: called");
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        String url ="http://souq.hardtask.co/app/app.asmx/GetCities?countryId="+id;
        Log.d(TAG, "getCities: "+url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d(TAG, "onResponse cities: "+response);
                        parseCitiesJson(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "onErrorResponse: error "+error);
            }
        });

        queue.add(stringRequest);
    }

    private void parseCitiesJson(String response) {
        Log.d(TAG, "parseCitiesJson: called" +response);
        mCityList = new ArrayList<>();
        try {
            JSONArray citiesJsonArray = new JSONArray(response);

            for (int i=0 ; i<citiesJsonArray.length() ; i++){
                JSONObject cityObject = citiesJsonArray.getJSONObject(i);
                City city = new City(cityObject.getInt("Id"),
                        cityObject.getString("TitleAR"),
                        cityObject.getString("TitleEN"),
                        cityObject.getInt("CountryId")
                        );
                Log.d(TAG, "parseCitiesJson: "+city);
                mCityList.add(city);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(getActivity(),   android.R.layout.simple_spinner_item, getcitiesList());
        citySpinner.setAdapter(spinnerArrayAdapter);

    }

    private String[] getcitiesList() {
//        Log.d(TAG, "getcitiesList: "+mCityList.get(0));
        String [] cities = new String[mCityList.size()];
        if (Locale.getDefault().getDisplayLanguage().equals("English")){
            for (int i =0 ; i< mCityList.size() ; i++){
                cities[i] = mCityList.get(i).getTitleEn();
            }
        }else {
            for (int i =0 ; i< mCityList.size() ; i++){
                cities[i] = mCityList.get(i).getTitleAr();
            }
        }
//        Log.d(TAG, "getCountryList: return "+mCityList.size()+cities[0]);
        return cities;
    }

    private String[] getCountryList() {
        Log.d(TAG, "getCountryList: called "+Locale.ENGLISH.getCountry());
        String [] coutries = new String[mCountryList.size()];
        if (Locale.getDefault().getDisplayLanguage().equals("English")){
            for (int i =0 ; i< mCountryList.size() ; i++){
                coutries[i] = mCountryList.get(i).getTitleEn();
            }
        }else {
            for (int i =0 ; i< mCountryList.size() ; i++){
                coutries[i] = mCountryList.get(i).getTitleAr();
            }
        }
//        Log.d(TAG, "getCountryList: return "+coutries[0]);
        return coutries;
    }


    private void changeToOtherLanguage() {
        String languageToLoad = null;
        Log.d(TAG, "changeToOtherLanguage: "+Locale.getDefault().getDisplayLanguage());
        switch (Locale.getDefault().getDisplayLanguage()){
            case "English":
                languageToLoad  = "ar";
                break;

            case "العربية":
                languageToLoad  = "en";
                break;
        }
        Log.d(TAG, "changeToOtherLanguage: "+Locale.getDefault().getDisplayLanguage());
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getActivity().getBaseContext().getResources().updateConfiguration(config , getActivity().getBaseContext().getResources().getDisplayMetrics());
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        ft.detach(this);
        ft.attach(this);
        ft.commit();
    }

}
