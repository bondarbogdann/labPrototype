package com.bbondar.mobilecomputingprorotype;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rajawali3d.view.SurfaceView;


public class BodyFragment extends Fragment {

    private BodyRenderer renderer;

    public BodyFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_body, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Set body model
        SurfaceView rajawaliSurface = (SurfaceView) view.findViewById(R.id.rajawali_surface);
        renderer = new BodyRenderer(getActivity());
        rajawaliSurface.setSurfaceRenderer(renderer);
    }

    public BodyRenderer getRenderer(){
        return renderer;
    }
}
