package hn.uth.pedidos.ui.creacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import hn.uth.pedidos.database.Pedido;
import hn.uth.pedidos.databinding.FragmentCreacionBinding;
import hn.uth.pedidos.ui.pedidos.PedidosViewModel;

public class CreacionFragment extends Fragment {

    private FragmentCreacionBinding binding;

    private CreacionViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCreacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(CreacionViewModel.class);

        binding.btnGuardar.setOnClickListener( v -> {
            Pedido nuevo = new Pedido(binding.tilautor.getEditText().getText().toString(),
                    binding.tildireccion.getEditText().getText().toString(),
                    "Creado",binding.tildetalle.getEditText().getText().toString() );

            viewModel.insert(nuevo);

        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}