package hn.uth.pedidos.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class PedidoRepository {
    private PedidoDao dao;
    private LiveData<List<Pedido>> dataset;

    public PedidoRepository(Application app) {
        OrdenesDatabase db = OrdenesDatabase.getDatabase(app);
        this.dao = db.pedidoDao();
        this.dataset = dao.getPedidos();
    }

    public LiveData<List<Pedido>> getDataset() {
        return dataset;
    }

    public void insert(Pedido nuevo){
        //INSERTANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        OrdenesDatabase.databaseWriteExecutor.execute(() -> {
            dao.insert(nuevo);
        });
    }

    public void update(Pedido actualizar){
        //ACTUALIZANDO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        OrdenesDatabase.databaseWriteExecutor.execute(() -> {
            dao.update(actualizar);
        });
    }

    public void delete(Pedido borrar){
        //BORRANDO UN REGISTRO DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        OrdenesDatabase.databaseWriteExecutor.execute(() -> {
            dao.delete(borrar);
        });
    }

    public void deleteAll(){
        //BORRANDO TODOS LOS REGISTROS DE FORMA ASINCRONA, PARA NO AFECTAR LA INTERFAZ DE USUARIO
        OrdenesDatabase.databaseWriteExecutor.execute(() -> {
            dao.deleteAll();
        });
    }
}
