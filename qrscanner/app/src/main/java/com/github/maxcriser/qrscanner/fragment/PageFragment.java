package com.github.maxcriser.qrscanner.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.maxcriser.qrscanner.R;

public class PageFragment extends Fragment {
    public static final String ARG_PAGE = "ARG_PAGE";

    private int mPage;

    public static PageFragment newInstance(final int page) {
        final Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        final PageFragment fragment = new PageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(ARG_PAGE);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        switch (getArguments().getInt(ARG_PAGE))
        {
            case 1: {
                return inflater.inflate(R.layout.fragment_home, container, false);
            }
            default: {
                return inflater.inflate(R.layout.fragment_database, container, false);
            }
        }
    }
}