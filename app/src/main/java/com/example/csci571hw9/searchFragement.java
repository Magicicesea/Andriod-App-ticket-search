package com.example.csci571hw9;

//import android.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
//import android.support.fragment.*;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.csci571hw9.helper.eventObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class searchFragement extends Fragment {

    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    private AutoSuggestAdapter autoSuggestAdapter;
    private static View retView ;
    public static final String SEARCH_REQUEST_MESSAGE = "com.example.csci571hw9.searchRequest";
    public HashMap<String, String> searchRequest = new HashMap<>();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        retView = inflater.inflate(R.layout.switch_menu, container, false);
        Button searchBtn = retView.findViewById(R.id.searchBtn);
        Button currentLocaiton = retView.findViewById(R.id.currentSelection);
        Button clearBtn = retView.findViewById(R.id.clearBtn);

        clearBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                EditText keywordInput = retView.findViewById(R.id.keywordInput);
                Spinner categoryInput = retView.findViewById(R.id.categoryInput);
                EditText distanceInput = retView.findViewById(R.id.distanceInput);
                Spinner distanceUnit = retView.findViewById(R.id.distanceUnit);

                RadioGroup fromSelection = retView.findViewById(R.id.fromSelectionGroup);
                fromSelection.check(R.id.currentSelection);



                EditText otherInput = retView.findViewById(R.id.otherInput);
                otherInput.setText("");

                keywordInput.getText().clear();
                categoryInput.setSelection(0);
                distanceInput.getText().clear();
                distanceUnit.setSelection(0);

                if(getActivity().getCurrentFocus() != null){
                    getActivity().getCurrentFocus().clearFocus();
                }


            }
        });

        currentLocaiton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                EditText otherInput = retView.findViewById(R.id.otherInput);
                otherInput.setText("");
                retView.findViewById(R.id.otherInputError).setVisibility(View.GONE);
                otherInput.setEnabled(false);
            }

        });

        final Button otherLocatino = retView.findViewById(R.id.otherSelection);
        otherLocatino.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                final EditText otherInput = retView.findViewById(R.id.otherInput);
//                otherInput.setOnTouchListener(new View.OnTouchListener() {
//                    @Override
//                    public boolean onTouch(View v, MotionEvent event) {
//
//                        Log.i("onTouch","otherinput on touch");
//                        if(otherInput.getText().toString().matches(".*[^ ].*")){
//                           otherInput.setError("Please enter mandatory field");
//                        }
//                        return false;
//                    }
//                });

//                otherInput.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.i("onclick","onclick on touch");
//                        if(otherInput.getText().toString().matches(".*[^ ].*")){
//                           otherInput.setError("Please enter mandatory field");
//                        }
//                        //return false;
//                    }
//                });
                otherInput.setEnabled(true);
            }

        });

        //Button currentLocaiton = retView.findViewById(R.id.currentSelection);
        //retView.findViewById(R.id.otherInput).setOnTouchListener();


        searchBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {


                EditText keywordInput = retView.findViewById(R.id.keywordInput);
                Spinner categoryInput = retView.findViewById(R.id.categoryInput);
                EditText distanceInput = retView.findViewById(R.id.distanceInput);
                Spinner distanceUnit = retView.findViewById(R.id.distanceUnit);

                boolean tmp = false;


                if(!Pattern.matches(".*[^ ].*",keywordInput.getText().toString())){
                    tmp = true;
                    keywordInput.setText("");
                    keywordInput.clearFocus();
                    retView.findViewById(R.id.keywordError).setVisibility(View.VISIBLE);
                }else{
                    retView.findViewById(R.id.keywordError).setVisibility(View.GONE);
                }


                TextView otherInputCheck = (TextView) retView.findViewById(R.id.otherInput);
                if(otherInputCheck.isEnabled() && !Pattern.matches(".*[^ ].*",otherInputCheck.getText().toString())){
                    //Log.i("error","space matched");
                    //retView.findViewById(R.id.keywordLable).setError("Please enter mandatory field");
                    otherInputCheck.clearFocus();
                    otherInputCheck.setText("");
                    retView.findViewById(R.id.otherInputError).setVisibility(View.VISIBLE);
                    tmp = true;
                }else{
                    retView.findViewById(R.id.otherInputError).setVisibility(View.GONE);
                }

                if(tmp){
                    Toast validationError = Toast.makeText(getContext(),"Please fix all fields with errors",Toast.LENGTH_SHORT);
                    validationError.show();
                    return;
                }




                searchRequest.put("keyword", keywordInput.getText().toString());
                searchRequest.put("category", categoryInput.getSelectedItem().toString());
                searchRequest.put("distanceInput", distanceInput.getText().toString());
                switch (distanceUnit.getSelectedItem().toString()){
                    case "Miles":
                        searchRequest.put("distanceUnit", "miles");
                        break;
                    case "Kilometers":
                        searchRequest.put("distanceUnit", "km");
                        break;
                }
                //searchRequest.put("distanceUnit", distanceUnit.getSelectedItem().toString());


                RadioGroup fromSelection = retView.findViewById(R.id.fromSelectionGroup);
                Button selectedButton = fromSelection.findViewById(fromSelection.getCheckedRadioButtonId());
                Integer fromSelectionId = fromSelection.indexOfChild(selectedButton);
                searchRequest.put("fromSelection", fromSelectionId.toString());
                switch(fromSelectionId){
                    case 0:
                        searchRequest.put("fromSelectionId","0");
                        break;
                    case 1:
                        EditText otherInput = retView.findViewById(R.id.otherInput);

                        searchRequest.put("fromSelectionId","1");
                        searchRequest.put("otherInput",otherInput.getText().toString());
                        break;
                }

                Intent intent = new Intent(getContext(), showEvnetList.class);
                intent.putExtra(SEARCH_REQUEST_MESSAGE,searchRequest);

                startActivity(intent);

            }
        });

        final AppCompatAutoCompleteTextView autoCompleteTextView =
                retView.findViewById(R.id.keywordInput);
        //final TextView selectedText = retView.findViewById(R.id.selected_item);

        //Setting up the adapter for AutoSuggest
        autoSuggestAdapter = new AutoSuggestAdapter(getActivity(),
                android.R.layout.simple_dropdown_item_1line);

        autoCompleteTextView.setThreshold(2);
        autoCompleteTextView.setAdapter(autoSuggestAdapter);

//        autoCompleteTextView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                if(autoCompleteTextView.getText().toString().matches(".*[^ ].*")){
//                    autoCompleteTextView.setError("Please enter mandatory field");
//                }
//                return false;
//            }
//        });


        autoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int
                    count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                        AUTO_COMPLETE_DELAY);

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        handler = new Handler(new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                if (msg.what == TRIGGER_AUTO_COMPLETE) {
                    if (!TextUtils.isEmpty(autoCompleteTextView.getText())) {
                        makeApiCall(autoCompleteTextView.getText().toString());
                    }
                }
                return false;
            }
        });


        return retView;
    }

    //call for auto complete keyword pool
    private void makeApiCall(String text) {

        ApiCall.autoCompleteKeyword(getActivity(), text, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                //Log.i("response",response.toString());

                //parsing logic, please change it as per your requirement
                List<String> stringList = new ArrayList<>();

                try {
                    JSONObject responseObject = response;
                    if(!response.has("_embedded") || !response.getJSONObject("_embedded").has("attractions")
                            || responseObject.getJSONObject("_embedded").getJSONArray("attractions").length() == 0){
                        showEvnetList.setNoResults();
                        return;
                    }else{
                        showEvnetList.resetNoResults();
                    }

                    JSONArray results = responseObject.getJSONObject("_embedded").getJSONArray("attractions");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject row = results.getJSONObject(i);
                        stringList.add(row.getString("name"));
                    }

                    //Log.i("results",stringList.toString());

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //IMPORTANT: set data here and notify
                autoSuggestAdapter.setData(stringList);
                autoSuggestAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response","error to get response");
            }
        });
    }




}
