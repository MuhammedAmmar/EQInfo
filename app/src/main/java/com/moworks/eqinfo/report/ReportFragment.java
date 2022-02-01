package com.moworks.eqinfo.report;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.moworks.eqinfo.R;
import com.moworks.eqinfo.ScrollChildSwipeRefreshLayout;
import com.moworks.eqinfo.ServiceLocator;
import com.moworks.eqinfo.data.source.local.EqEntity;


public class ReportFragment extends Fragment {

    private  ReportViewModel reportViewModel;
    private  ReportAdapter reportAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_report, container, false);
        ScrollChildSwipeRefreshLayout swipeRefreshLayout =  root.findViewById(R.id.swipe_layout);
        RecyclerView recyclerView = root.findViewById(R.id.report_recycler);
        ImageView noInternet = root.findViewById(R.id.no_internet);
        ReportViewModel.Factory factory = new ReportViewModel.Factory(requireActivity().getApplication(), ServiceLocator.getRepository(requireContext()));
        setHasOptionsMenu(true);

        reportViewModel = new ViewModelProvider(this, factory).get(ReportViewModel.class);

        setUpSwipeLayout(swipeRefreshLayout , recyclerView);

        reportAdapter = new ReportAdapter(item -> {
            Navigation.findNavController(requireView()).navigate(ReportFragmentDirections.actionReportFragmentToDetailsFragment(item));

        });

        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(reportAdapter);

        reportViewModel.getReport().observe(getViewLifecycleOwner(), eqEntities -> {
            if (eqEntities != null) {
                if (eqEntities.isEmpty() && !reportViewModel.getIsLoading()){
                    noInternet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }else {
                    noInternet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                    reportAdapter.submitList(EqEntity.asEqModel(eqEntities));
                }
            }

        });


        return root ;
    }


    private void setUpSwipeLayout(ScrollChildSwipeRefreshLayout refreshLayout , View scrollUpChild ){
        if (refreshLayout != null ) {
            refreshLayout.setEnabled(true);
            refreshLayout.setColorSchemeColors(
                    ContextCompat.getColor(requireActivity(), R.color.orange_400),
                    ContextCompat.getColor(requireActivity(), R.color.orange_400),
                    ContextCompat.getColor(requireActivity(), R.color.orange_400)
            );
            refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    refreshLayout.setRefreshing(true);
                    reportViewModel.setIsLoading(true);
                    ServiceLocator.getRepository(requireContext()).refreshData();

                    new Handler(Looper.getMainLooper()).postDelayed(()-> {
                        refreshLayout.setRefreshing(false);
                    } , 2000);
                }
            });

            refreshLayout.scrollUpChild = scrollUpChild;
        }
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu , menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return  NavigationUI.onNavDestinationSelected(item ,  Navigation.findNavController(requireView()))
                || super.onOptionsItemSelected(item);
    }
}
