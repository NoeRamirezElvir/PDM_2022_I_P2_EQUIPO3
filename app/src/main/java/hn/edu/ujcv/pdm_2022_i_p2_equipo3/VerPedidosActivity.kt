package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterEmpleado
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterPedido
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Cliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Empleado
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Menu
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Pedidos
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityVerPedidosBinding

class VerPedidosActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVerPedidosBinding

    private var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter:RecyclerView.Adapter<RecyclerAdapterPedido.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerPedidosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        inicializar()

        layoutManager = LinearLayoutManager(this)
        binding.contentPedido.recyclerViewPedidos.layoutManager = layoutManager
        actualizarRecyclerView()

        binding.fab.setOnClickListener { view ->
        enviarRegistroPedido()
        }
    }

    private fun inicializar() {
        val intent = intent
        if(intent.getParcelableArrayListExtra<Cliente>("Clientes") != null){
            listaClientes = intent.getParcelableArrayListExtra("Clientes")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Menu") != null){
            listaMenus = intent.getParcelableArrayListExtra("Menu")!!
        }
        if (intent.getParcelableArrayListExtra<Empleado>("Empleados") != null) {
            listaEmpleados = intent.getParcelableArrayListExtra("Empleados")!!
        }
        if (intent.getParcelableArrayListExtra<Pedidos>("Pedidos") != null) {
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
    }
    fun actualizarRecyclerView() {
        adapter = RecyclerAdapterPedido(listaPedidos!!, this)
        binding.contentPedido.recyclerViewPedidos.adapter = adapter
    }

    private fun enviarRegistroPedido() {
        val intent = Intent(this, RegistrarPedidoActivity::class.java)
        if(listaClientes.isNullOrEmpty() || listaMenus.isNullOrEmpty() ||
            listaEmpleados.isNullOrEmpty()){
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No se puede realizar un pedido")
                .setMessage("No se encuentran los registros requeridos")
                .setIcon(R.drawable.ic_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }
        else if (!validarEmpleado()) {
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No se puede realizar un pedido")
                .setMessage("No se encuentran empleados meseros registrados")
                .setIcon(R.drawable.ic_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }
        else{
            intent.putExtra("Clientes",listaClientes)
            intent.putExtra("Menu",listaMenus)
            intent.putExtra("Empleados", listaEmpleados)
            intent.putExtra("Pedidos", listaPedidos)
            startActivity(intent)
        }
    }

    private fun validarEmpleado():Boolean {
        var mesero=false
        for (items in listaEmpleados!!) {
            if (items.puesto == "Mesero") {
                mesero = true
                break
            }
        }
        return mesero
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
        this.finish()
    }
}