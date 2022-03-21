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
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterFactura
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterPedido
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityVerFacturaBinding

class VerFacturaActivity : AppCompatActivity() {
    //listas que se llenaran con el getParcelable
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?   = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVerFacturaBinding

    private var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterFactura.ViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

     binding = ActivityVerFacturaBinding.inflate(layoutInflater)
     setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        inicializar()

        layoutManager = LinearLayoutManager(this)
        binding.contentFactura.recyclerViewFacturas.layoutManager = layoutManager
        actualizarRecyclerView()

        binding.fab.setOnClickListener { view ->
            enviarRegistroFactura()
        }
    }

    private fun inicializar() {
        val intent = intent
        if (intent.getParcelableArrayListExtra<Empleado>("Empleados") != null) {
            listaEmpleados = intent.getParcelableArrayListExtra("Empleados")!!
        }
        if (intent.getParcelableArrayListExtra<Pedidos>("Pedidos") != null) {
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
        if (intent.getParcelableArrayListExtra<Factura>("Facturas") != null){
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
        if(intent.getParcelableArrayListExtra<Cliente>("Clientes") != null){
            listaClientes = intent.getParcelableArrayListExtra("Clientes")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Menu") != null){
            listaMenus = intent.getParcelableArrayListExtra("Menu")!!
        }
    }
    fun actualizarRecyclerView() {
        adapter = RecyclerAdapterFactura(listaFacturas!!, this)
        binding.contentFactura.recyclerViewFacturas.adapter = adapter
    }
    private fun enviarRegistroFactura(){
        val intent = Intent(this, RegistarFacturasActivity::class.java)
        if(listaPedidos.isNullOrEmpty()){
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No se puede registar una factura")
                .setMessage("No se encuentran registros de pedidos")
                .setIcon(R.drawable.ic_cancelar)
                .setPositiveButton("Aceptar"){_,_ ->
                }.create()
            cancelDialog.show()
        }
        else if (!validarEmpleado()) {
            val cancelDialog = AlertDialog.Builder(this)
                .setTitle("No se puede realizar un pedido")
                .setMessage("No se encuentran empleados requeridos registrados")
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
            intent.putExtra("Facturas",listaFacturas)
            startActivity(intent)
        }
    }

    private fun validarEmpleado():Boolean {
        var mesero=false
        for (items in listaEmpleados!!) {
            if (items.puesto == "Cajero") {
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
        intent.putExtra("Facturas",listaFacturas)
        startActivity(intent)
        this.finish()
    }

}