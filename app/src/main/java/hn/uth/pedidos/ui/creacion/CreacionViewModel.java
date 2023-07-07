package hn.uth.pedidos.ui.creacion;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import hn.uth.pedidos.database.Pedido;
import hn.uth.pedidos.database.PedidoRepository;

public class CreacionViewModel extends AndroidViewModel {
    private PedidoRepository repository;

    public CreacionViewModel(@NonNull Application app) {
        super(app);
        this.repository = new PedidoRepository(app);
    }

    public PedidoRepository getRepository() {
        return repository;
    }

    public void insert(Pedido nuevo){
        repository.insert(nuevo);
    }

    public void update(Pedido actualizar){
        repository.update(actualizar);
    }


}