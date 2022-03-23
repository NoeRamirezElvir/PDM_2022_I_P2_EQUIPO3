package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterCliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterMenu
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityVerMenusBinding
import kotlinx.android.synthetic.main.activity_ver_menus.*

class VerMenusActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?= ArrayList()

    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<RecyclerAdapterMenu.ViewHolder>? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVerMenusBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerMenusBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarMenu)
        inicio()
        //ADAPTER RECYCLERVIEW
        layoutManager = LinearLayoutManager(this)
        binding.contentMenu.recyclerViewMenu.layoutManager = layoutManager
        actualizarRecyclerView()
        binding.fbtnAgregarMenu.setOnClickListener {enviarListaRegistrarMenu()}

    }
    fun actualizarRecyclerView(){
        adapter = RecyclerAdapterMenu(listaMenus!!,this)
        binding.contentMenu.recyclerViewMenu.adapter = adapter
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
        if(intent.getParcelableArrayListExtra<Factura>("Facturas") != null){
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
    }
    private fun enviarListaRegistrarMenu(){
        val intent = Intent(this, RegistrarMenuActivity::class.java)
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
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
        this.finish()
    }



}