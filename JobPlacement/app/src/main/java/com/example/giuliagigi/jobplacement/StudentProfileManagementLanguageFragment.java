package com.example.giuliagigi.jobplacement;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.parse.ParseException;



public class StudentProfileManagementLanguageFragment extends ProfileManagementFragment {
    private static String BUNDLE_DESCRIPTION = "bundle_description";
    private static String BUNDLE_LEVEL = "bundle_level";
    private static String BUNDLE_HASCHANGED = "bundle_recycled";

    private Student currentUser;
    private Spinner languageLevel;
    private EditText languageDesc;
    Button confirm, delete;
    private Language language;


    /* ----------------- CONSTRUCTORS GETTERS SETTERS ------------------------------------------- */

    public StudentProfileManagementLanguageFragment() { super(); }
    public static StudentProfileManagementLanguageFragment newInstance(Language language) {
        StudentProfileManagementLanguageFragment fragment = new StudentProfileManagementLanguageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        fragment.setLanguage(language);
        return fragment;
    }

    public void setLanguage(Language language){
        this.language = language;
    }


    /* ----------------- STANDARD CALLBACKS ---------------------------------------------------- */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        currentUser = (Student)application.getUserObject();
        isListenerAfterDetach = true;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        int level;
        String description;
        if (getArguments().getBoolean(BUNDLE_HASCHANGED)) {

            level = getArguments().getInt(BUNDLE_LEVEL);
            description = getArguments().getString(BUNDLE_DESCRIPTION);

        } else {

            if (language.getLevel() != null)
                level = Language.getLevelID(language.getLevel());
            else
                level = 0;

            if (language.getDescription() != null)
                description = language.getDescription();
            else
                description = null;
        }

        root = inflater.inflate(R.layout.fragment_student_profile_management_language, container, false);

        languageLevel = (Spinner) root.findViewById(R.id.language_management_spinnerLevel);
        languageLevel.setAdapter(new StringAdapter(Language.LEVELS));
        languageLevel.setSelection(level);

        confirm = (Button) root.findViewById(R.id.language_management_confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                saveChanges();
            }
        });

        delete = (Button) root.findViewById(R.id.language_management_delete_button);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUser.removeLanguage(language);
                currentUser.saveInBackground();

                try {
                    language.delete();
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                root.setVisibility(View.INVISIBLE);
            }
        });

        languageDesc = (EditText) root.findViewById(R.id.language_management_description_area);
        if (description != null)
            languageDesc.setText(String.valueOf(description));
        else
            languageDesc.setText(INSERT_FIELD);

        textFields.add(languageDesc);

        OnFieldChangedListener hasChangedListener = new OnFieldChangedListener();
        for (EditText et : textFields)
            et.addTextChangedListener(hasChangedListener);

        setEnable(host.isEditMode());
        return root;
    }

    @Override
    public void onDetach() {
        super.onDetach();

        getArguments().putBoolean(BUNDLE_HASCHANGED,hasChanged);

        if(hasChanged){

            getArguments().putInt(BUNDLE_LEVEL,languageLevel.getSelectedItemPosition());
            getArguments().putString(BUNDLE_DESCRIPTION, languageDesc.getText().toString());
        }
    }

    /* ----------------- AUXILIARY METHODS ----------------------------------------------------- */

    @Override
    public void saveChanges(){

        super.saveChanges();

        currentUser.addLanguage(language);
        currentUser.saveInBackground();

        language.setLevel((String) languageLevel.getSelectedItem());
        if(!languageDesc.getText().toString().equals(INSERT_FIELD)) language.setDescription((String) languageDesc.getText().toString());
        language.saveInBackground();

    }

    @Override
    public void setEnable(boolean enable) {
        super.setEnable(enable);

        languageLevel.setEnabled(enable);
        languageDesc.setEnabled(enable);


        int visibility;
        if(enable)
            visibility = View.VISIBLE;
        else
            visibility = View.INVISIBLE;

        confirm.setVisibility(visibility);
        delete.setVisibility(visibility);
    }

}
