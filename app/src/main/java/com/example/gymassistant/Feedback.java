package com.example.gymassistant;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import javax.microedition.khronos.egl.EGLDisplay;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Feedback#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Feedback extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Feedback() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Feedback.
     */
    // TODO: Rename and change types and number of parameters
    public static Feedback newInstance(String param1, String param2) {
        Feedback fragment = new Feedback();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_feedback, container,false);
        final EditText fUserName=(EditText) view.findViewById(R.id.fusername);
        final EditText feedback_text=(EditText) view.findViewById(R.id.feedback_text);
        Button btnFeedback=(Button) view.findViewById(R.id.feedback_button);
        btnFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);

                i.setType("message/html");
                i.putExtra(Intent.EXTRA_EMAIL,new String[]{"gymassistant1234@gmail.com"} );
                i.putExtra(Intent.EXTRA_SUBJECT,"Feedback From APP");
                i.putExtra(Intent.EXTRA_TEXT,"Username:"+fUserName.getText()+"\n Message:"+feedback_text.getText());
                try {
                    startActivity(Intent.createChooser(i,"Please select Email"));

                }
                catch (android.content.ActivityNotFoundException ex){
                    Toast.makeText(getActivity(),"There are no Email clients",Toast.LENGTH_SHORT).show();

                }
                startActivityForResult(i,1);
            }


        });
        return view;
    }


}
