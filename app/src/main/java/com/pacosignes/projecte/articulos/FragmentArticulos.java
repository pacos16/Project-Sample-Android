package com.pacosignes.projecte.articulos;


import androidx.appcompat.app.ActionBar;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.pacosignes.projecte.R;
import java.util.ArrayList;

public class FragmentArticulos extends Fragment implements IArticuloListener{
    private AdapterArticulos adapterArticulos;
    private RecyclerView rvArticulos;
    private ArrayList<Articulo> articulos;
    private ActionBar actionBar;

    public FragmentArticulos(ArrayList<Articulo> articulos, ActionBar actionBar) {
        this.articulos = articulos;
        this.actionBar=actionBar;
        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        rvArticulos= getActivity().findViewById(R.id.rvArticulos);

        actionBar.setTitle("Artículos");
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#0C68E8")));
        actionBar.show();

        adapterArticulos=new AdapterArticulos(articulos,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager
                (getContext(),LinearLayoutManager.VERTICAL,false);
        rvArticulos.setAdapter(adapterArticulos);

        rvArticulos.setLayoutManager(layoutManager);
    }



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem searchItem= menu.findItem(R.id.svActionSearch);
        SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterArticulos.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.lista_articulos,container,false);
    }

    @Override
    public void onArticuloSeleccionado(Articulo a) {
        FragmentArticuloDetalle f= new FragmentArticuloDetalle(a);
        getActivity().getSupportFragmentManager()
                .beginTransaction().replace(R.id.clSplahWrapper,f)
                .addToBackStack(null).commit();
    }
}
