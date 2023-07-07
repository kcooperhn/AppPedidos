package hn.uth.pedidos.ui.pedidos;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hn.uth.pedidos.OnItemClickListener;
import hn.uth.pedidos.database.Pedido;
import hn.uth.pedidos.databinding.PedidoItemBinding;

public class PedidosAdapter extends RecyclerView.Adapter<PedidosAdapter.ViewHolder> {

    private List<Pedido> dataset;
    private OnItemClickListener<Pedido> manejadorEventoClick;

    public PedidosAdapter(List<Pedido> dataset, OnItemClickListener<Pedido> manejadorEventoClick) {
        this.dataset = dataset;
        this.manejadorEventoClick = manejadorEventoClick;
    }

    @NonNull
    @Override
    public PedidosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PedidoItemBinding binding = PedidoItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidosAdapter.ViewHolder holder, int position) {
        Pedido pedidoMostrar = dataset.get(position);
        holder.binding.tvNombre.setText(pedidoMostrar.getAutor());
        holder.binding.tvDireccion.setText(pedidoMostrar.getDireccionentrega());
        holder.setOnClickListener(pedidoMostrar, manejadorEventoClick);
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void setItems(List<Pedido> pedidos){
        this.dataset = pedidos;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PedidoItemBinding binding;
        public ViewHolder(@NonNull PedidoItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void setOnClickListener(Pedido pedidoMostrar, OnItemClickListener<Pedido> listener) {
            this.binding.imgSearch.setOnClickListener(v -> listener.onItemClick(pedidoMostrar));
        }
    }
}
