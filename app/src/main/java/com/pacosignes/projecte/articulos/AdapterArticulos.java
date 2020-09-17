package com.pacosignes.projecte.articulos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pacosignes.projecte.R;

import java.util.ArrayList;
import java.util.List;

public class AdapterArticulos extends RecyclerView.Adapter<AdapterArticulos.ArticulosViewHolder> implements Filterable {
    private ArrayList<Articulo> articulos;
    private ArrayList<Articulo> articulosFull;
    private IArticuloListener listener;

    public AdapterArticulos(ArrayList<Articulo> articulos, IArticuloListener listener) {
        this.articulos = articulos;
        this.listener = listener;
        articulosFull = new ArrayList<>(articulos);

    }

    @NonNull
    @Override
    public ArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articulos_card_view,parent,false);
        return new ArticulosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticulosViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return articulos.size();
    }

    @Override
    public Filter getFilter() {
        return filtrado;
    }

    private Filter filtrado= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Articulo> filtredList= new ArrayList<>();

            if(constraint== null || constraint.length()==0){
                filtredList.addAll(articulosFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Articulo a:articulosFull
                     ) {
                    if(String.valueOf(a.getRef()).contains(filterPattern)){
                        filtredList.add(a);
                    }
                }
            }

            FilterResults results= new FilterResults();
            results.values = filtredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            articulos.clear();
            articulos.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };

    public class ArticulosViewHolder extends  RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView tvDescList;
        private TextView tvRefList;
        public ArticulosViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescList=itemView.findViewById(R.id.tvcvDescArticulo);
            tvRefList=itemView.findViewById(R.id.tvcvRefArticulo);
            itemView.setOnClickListener(this);
        }

        public void bind(int position){

            String ref= String.valueOf(articulos.get(position).getRef());
            tvRefList.setText(ref);
            tvDescList.setText(articulos.get(position).getDescripcion());
        }

        @Override
        public void onClick(View v) {
            listener.onArticuloSeleccionado(articulos.get(getAdapterPosition()));
        }
    }
}
