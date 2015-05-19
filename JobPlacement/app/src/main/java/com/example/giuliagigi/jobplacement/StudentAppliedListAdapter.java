package com.example.giuliagigi.jobplacement;

import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by pietro on 19/05/2015.
 */
public class StudentAppliedListAdapter extends RecyclerView.Adapter<StudentAppliedListAdapter.ViewHolder> implements View.OnClickListener {


    private FragmentActivity context;
    List<Student> mdataset;
    GlobalData globalData;

    public StudentAppliedListAdapter(FragmentActivity context, List<Student> students)
    {
        globalData=(GlobalData)context.getApplication();
        this.context=context;
        mdataset=students;

    }

    // Create new views (invoked by the layout manager)
    @Override
    public StudentAppliedListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.student_row, parent, false);
        // set the view's size, margins, paddings and layout parameters

        v.setClickable(true);
        v.setOnClickListener(StudentAppliedListAdapter.this);

        ViewHolder vh = new ViewHolder(v);
        v.setTag(vh);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        Bitmap img = null;
        try {
            img = mdataset.get(position).getProfilePhoto();

        } catch (Exception e) {
            img = null;
        }

        if (img != null) {
            holder.profile.setImageBitmap(img);
        } else
            holder.profile.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_profile));
           holder.studentName.setText(mdataset.get(position).getName());

        List<Degree> degrees = mdataset.get(position).getDegrees();
        if (!degrees.isEmpty()) {
            Collections.sort(degrees);

            holder.studentDegree.setText(degrees.get(0).getType() + " " + degrees.get(0).getStudies());
            Integer mark = null;
            try {
                mark = degrees.get(0).getMark();
            } catch (Exception e) {
                mark = null;
            }
            if (mark != null) {
                holder.studentGrade.setText(context.getResources().getString(R.string.Mark) + String.valueOf(mark));
            } else {
                holder.studentGrade.setText(context.getResources().getString(R.string.noMark));
            }
        } else {
            holder.studentDegree.setText(context.getResources().getString(R.string.noDegree));
            holder.studentGrade.setText("");
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mdataset.size();
    }




    @Override
    public void onClick(View v) {


        ViewHolder vh=(ViewHolder)v.getTag();

        //Pass Object to fragment
        FragmentManager fragmentManager = context.getSupportFragmentManager();

        //New Fragment
        ProfileManagement fragment=ProfileManagement.newInstance(false,mdataset.get(vh.getPosition()));
        // Insert the fragment by replacing any existing fragment
        // Insert the fragment by replacing any existing fragment

        fragmentManager.beginTransaction()
                .replace(R.id.tab_Home_container, fragment)
                .addToBackStack("OfferSearch")
                .commit();

        // Highlight the selected item, update the title, and close the drawer
        // Highlight the selected item, update the title, and close the drawer
        Toolbar toolbar= globalData.getToolbar();
        toolbar.setTitle("Student");


    }




    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profile;
        TextView studentName ;
        TextView studentDegree ;
        TextView studentGrade ;


        public ViewHolder(View v) {
            super(v);
            profile = (ImageView) v.findViewById(R.id.profile_img);
            studentName = (TextView) v.findViewById(R.id.student_name_tv);
            studentDegree = (TextView) v.findViewById(R.id.student_degree_tv);
            studentGrade = (TextView) v.findViewById(R.id.student_grade_tv);

        }


    }


}
