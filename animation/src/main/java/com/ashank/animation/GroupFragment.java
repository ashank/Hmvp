package com.ashank.animation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link GroupFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link GroupFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupFragment extends Fragment {
    private static final String TAG = "GroupFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;


    @BindView(R.id.reclerview1)
    RecyclerView mReclerview;

    List<String> peers = new ArrayList();
    private GroupAdapter mWifiDevicesAdapter;

    public GroupFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GroupFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GroupFragment newInstance(String param1, String param2) {
        GroupFragment fragment = new GroupFragment();
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

        View view =inflater.inflate(R.layout.fragment_group, container, false);
        // Inflate the layout for this fragment
        mReclerview = (RecyclerView) view.findViewById(R.id.reclerview1);
        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        Animation animation= AnimationUtils.loadAnimation(getActivity(),android.R.anim.slide_in_left);


        //自定义控制动画
       /* CustomAnimationController controller=new CustomAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(CustomAnimationController.ORDER_CUSTOM);
        controller.setOnIndexListener(new CustomAnimationController.Callback() {
            @Override
            public int onIndex(CustomAnimationController controller, int count, int index) {

                Log.e("sd", "onIndex: "+index+count );

                return 0;
            }
        });*/

        //正常组动画
        LayoutAnimationController controller=new LayoutAnimationController(animation);
        controller.setDelay(0.1f);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);


        mReclerview.setLayoutAnimation(controller);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        peers.add(0,"sd");
        peers.add(1,"sd");
        peers.add(2,"sd");
        peers.add(3,"sd");
        peers.add(4,"sd");
        peers.add(5,"sd");
        peers.add(6,"sd");
        mReclerview.setLayoutManager(linearLayoutManager);
        mWifiDevicesAdapter = new GroupAdapter(peers, getActivity());
        mReclerview.setAdapter(mWifiDevicesAdapter);



        super.onActivityCreated(savedInstanceState);
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

    @Override
    public void onDestroyView() {
        Log.e(TAG, "onDestroyView: ");
        super.onDestroyView();
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
