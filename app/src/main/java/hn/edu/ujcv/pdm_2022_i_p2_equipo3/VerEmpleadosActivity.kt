package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.RecyclerAdapterEmpleado
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Cliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Empleado
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Menu
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Pedidos
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityVerEmpleadosBinding

class VerEmpleadosActivity : AppCompatActivity() {
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()

    private var layoutManager:RecyclerView.LayoutManager? = null
    private var adapter:RecyclerView.Adapter<RecyclerAdapterEmpleado.ViewHolder>? = null

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityVerEmpleadosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityVerEmpleadosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        inicio()

        layoutManager = LinearLayoutManager(this)
        binding.contentEmpleado.recyclerViewEmpleado.layoutManager = layoutManager
        actualizarRecyclerView()

        binding.fab.setOnClickListener {
            enviarRegistrarEmpleado()
        }
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
    }
    fun actualizarRecyclerView() {
        adapter = RecyclerAdapterEmpleado(listaEmpleados!!, this)
        binding.contentEmpleado.recyclerViewEmpleado.adapter = adapter
    }
    private fun enviarRegistrarEmpleado() {
        val intent = Intent(this, RegistrarEmpleadoActivity::class.java)
        intent.putExtra("Clientes", listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
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