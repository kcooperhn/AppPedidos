package hn.uth.pedidos.ui.pedidos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.ArrayList;

import hn.uth.pedidos.OnItemClickListener;
import hn.uth.pedidos.R;
import hn.uth.pedidos.database.Pedido;
import hn.uth.pedidos.databinding.FragmentPedidosBinding;

public class PedidosFragment extends Fragment implements OnItemClickListener<Pedido> {

    private FragmentPedidosBinding binding;

    private PedidosAdapter adaptador;
    private PedidosViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentPedidosBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        viewModel = new ViewModelProvider(this).get(PedidosViewModel.class);

        adaptador = new PedidosAdapter(new ArrayList<>(), this);


        viewModel.getPedidoDataset().observe(getViewLifecycleOwner(), pedidos -> {
            adaptador.setItems(pedidos);
        });

        setupRecyclerView();

        return root;
    }

    private void setupRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext());
        binding.rvPedidos.setLayoutManager(linearLayoutManager);
        binding.rvPedidos.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Pedido data) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("pedido", data);
        NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main);
        navController.navigate(R.id.nav_creacion, bundle);
    }
}