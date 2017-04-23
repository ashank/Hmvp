package com.ashank.animation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.ashank.animation.activityanimation.OneActivity;
import com.ashank.animation.activityanimation.ThreeActivity;
import com.ashank.animation.contenttransitions.ContentShareOneActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityAnamitionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityAnamitionFragment extends Fragment {

    private static final String TAG = "ActivityAnamition";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    Unbinder unbinder;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ActivityAnamitionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActivityAnamitionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivityAnamitionFragment newInstance(String param1, String param2) {
        ActivityAnamitionFragment fragment = new ActivityAnamitionFragment();
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
        View view = inflater.inflate(R.layout.fragment_activity_anamition, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView: ");
        super.onDestroyView();
        unbinder.unbind();
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @OnClick({R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button3:
                Intent intent=new Intent(getContext(),OneActivity.class);
                startActivity(intent);
                break;
            case R.id.button4:
                Intent intent1=new Intent(getActivity(),ThreeActivity.class);
                startActivity(intent1);
                break;
            case R.id.button5:
                Intent intent2=new Intent(getActivity(),ContentShareOneActivity.class);
                startActivity(intent2);
                break;
            case R.id.button6:
                break;
            case R.id.button7:
                break;
            case R.id.button8:
                break;
        }

        Log.e(TAG, "onViewClicked: " );
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}
