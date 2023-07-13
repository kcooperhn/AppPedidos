package hn.uth.pedidos.ui.creacion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;

import hn.uth.pedidos.R;
import hn.uth.pedidos.database.Pedido;
import hn.uth.pedidos.databinding.FragmentCreacionBinding;

public class CreacionFragment extends Fragment {

    private FragmentCreacionBinding binding;

    private CreacionViewModel viewModel;

    private boolean esEdicion;
    private Pedido pedidoEditar;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        esEdicion = false;
        binding = FragmentCreacionBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        viewModel = new ViewModelProvider(this).get(CreacionViewModel.class);

        try{
            pedidoEditar = (Pedido)getArguments().getSerializable("pedido");
        }catch(Exception e){
            pedidoEditar = null;
        }

        llenarCamposPantalla(pedidoEditar);

        binding.btnGuardar.setOnClickListener( v -> {
            Pedido nuevo = new Pedido(binding.tilautor.getEditText().getText().toString(),
                    binding.tildireccion.getEditText().getText().toString(),
                    "Creado",binding.tildetalle.getEditText().getText().toString());

            String mensajeExito = getString(R.string.mensaje_creacion_pedido);
            if(esEdicion){
                nuevo.setIdPedido(pedidoEditar.getIdPedido());
                nuevo.setEstado(pedidoEditar.getEstado());
                viewModel.update(nuevo);
                mensajeExito = getString(R.string.mensaje_edicion_pedido);
            }else{
                viewModel.insert(nuevo);
            }

            NavController navController = Navigation.findNavController(this.getActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.nav_pedidos);

            Snackbar.make(binding.clCrearPedido, mensajeExito, Snackbar.LENGTH_LONG).show();

        });

        return root;
    }

    private void llenarCamposPantalla(Pedido pedido) {
        if(pedido == null){
            //ES UNA CREACION, NO ENVIE NINGUN PEDIDO ANTERIOR
            binding.btnGuardar.setText(R.string.btn_guardar_crear);
        }else{
            //ES UNA EDICION, ESTOY ENVIANDO UN PEDIDO PARA EDITAR
            binding.tilautor.getEditText().setText(pedido.getAutor());
            binding.tildireccion.getEditText().setText(pedido.getDireccionentrega());
            binding.tildetalle.getEditText().setText(pedido.getDetalle());
            esEdicion = true;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}