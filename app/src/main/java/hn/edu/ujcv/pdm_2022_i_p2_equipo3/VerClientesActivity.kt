package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterCliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityVerClientesBinding
import kotlinx.android.synthetic.main.activity_ver_clientes.*

class VerClientesActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>? = ArrayList()

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterCliente.ViewHolder>? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVerClientesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarCliente)
        inicio()
        //ADAPTER RECYCLERVIEW
        layoutManager = LinearLayoutManager(this)
        binding.contentClientes.recyclerViewClientes.layoutManager = layoutManager
        actualizarRecyclerView()
        binding.fbtnAgregarCliente.setOnClickListener {enviarListaRegistrarCliente()}

    }
    fun actualizarRecyclerView(){
        adapter = RecyclerAdapterCliente(listaClientes!!,this)
        binding.contentClientes.recyclerViewClientes.adapter = adapter
    }
    private fun inicio(){
        val intent = intent
        if(intent.getParcelableArrayListExtra<Cliente>("Clientes") != null){
            listaClientes = intent.getParcelableArrayListExtra("Clientes")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Menu") != null){
            listaMenus = intent.getParcelableArrayListExtra("Menu")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Empleados") != null){
            listaEmpleados = intent.getParcelableArrayListExtra("Empleados")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Pedidos") != null){
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Facturas") != null){
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
    }
    private fun enviarListaRegistrarCliente(){
        val intent = Intent(this, RegistrarClientesActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
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