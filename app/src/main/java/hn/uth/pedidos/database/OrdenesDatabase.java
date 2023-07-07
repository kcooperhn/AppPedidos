package hn.uth.pedidos.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(version = 1, exportSchema = false, entities = {Pedido.class})
public abstract class OrdenesDatabase extends RoomDatabase {
    public abstract PedidoDao pedidoDao();

    private static volatile OrdenesDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    //GENERANDO UNA INSTANCIA MEDIANTE PATRÓN DE SOFTWARE SINGLETON
    static OrdenesDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (OrdenesDatabase.class){
                if(INSTANCE == null){

                    Callback miCallback = new Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);

                            databaseWriteExecutor.execute(() -> {
                                PedidoDao dao = INSTANCE.pedidoDao();
                                dao.deleteAll();

                                //AQUI PUEDO LLENAR VALORES POR DEFECTO EN MI BASE DE DATOS
                                Pedido nuevo1 = new Pedido("Roberto Hernandez","Residencial Agua Salada","Entregado","Dos cajas de leche" );
                                Pedido nuevo2 = new Pedido("Susana Caballero","Residencial Vista Hermosa","Entregado","Una pizza suprema" );
                                Pedido nuevo3 = new Pedido("Alberto Contreras","Residencial Casabella","Entregado","dos pizzas supremas" );

                                dao.insert(nuevo1);
                                dao.insert(nuevo2);
                                dao.insert(nuevo3);

                            });

                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), OrdenesDatabase.class, "ordenes_db").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}
