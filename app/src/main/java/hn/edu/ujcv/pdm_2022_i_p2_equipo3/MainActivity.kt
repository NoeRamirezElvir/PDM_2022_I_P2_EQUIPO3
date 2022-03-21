package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?= ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicio()

        btnVistaCliente.setOnClickListener {enviarListaCliente()}
        btnVistaMenu.setOnClickListener { enviarListaMenu() }
        btnVistaEmpleado.setOnClickListener { enviarListaEmpleado() }
        btnVistaPedidos.setOnClickListener { enviarListaPedido() }
        btnVistaFactura.setOnClickListener { enviarListaFactura() }

        val actionbar = supportActionBar
        actionbar!!.title = "Restaurante UJCV"
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
        if(intent.getParcelableArrayListExtra<Pedidos>("Pedidos") != null){
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
        if(intent.getParcelableArrayListExtra<Factura>("Facturas") != null){
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }

    }
    private fun enviarListaCliente(){
        val intent = Intent(this, VerClientesActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
    }
    private fun enviarListaMenu(){
        val intent = Intent(this, VerMenusActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
    }
    private fun enviarListaEmpleado() {
        val intent = Intent(this, VerEmpleadosActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
    }
    private fun enviarListaPedido() {
        val intent = Intent(this, VerPedidosActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
    }
    private fun enviarListaFactura(){
        val intent = Intent(this,VerFacturaActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
    }

}