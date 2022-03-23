package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.hardware.camera2.TotalCaptureResult
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.ListViewAdapter
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.ModelListView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistrarPedidoBinding
import kotlinx.android.synthetic.main.activity_registrar_pedido.*
import kotlinx.android.synthetic.main.content_registar_facturas.*
import kotlinx.android.synthetic.main.content_registrar_empleado.*
import kotlinx.android.synthetic.main.content_registrar_pedido.*
import kotlinx.android.synthetic.main.fragment_first8.*
import java.lang.Exception

class RegistrarPedidoActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?= ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistrarPedidoBinding
    var lista=ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarPedidoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        var listView = findViewById<ListView>(R.id.lstView)
        var list = mutableListOf<ModelListView>()
        var adapter = ListViewAdapter(this, R.layout.list_view, list)

        setSupportActionBar(binding.toolbar)
        inicializar()

        binding.fab.setOnClickListener { view ->
            if (lista.contains(spinnerMenu.selectedItem.toString())) {
                Toast.makeText(this, "Ya ha agregado este Menu", Toast.LENGTH_SHORT).show()
            }else if(txtNoPedido.text.isNullOrEmpty()){
                Toast.makeText(this, "No. pedido esta Vacío", Toast.LENGTH_SHORT).show()
            }else{
                agregarMenu(list, adapter)
            }

        }

        btnRegistrarPedido.setOnClickListener {
            /*if (lista.isEmpty())
                Toast.makeText(this, "Ingrese Menus", Toast.LENGTH_LONG).show()
            else if (!validarBoton()){
                Toast.makeText(this, "Valide campos", Toast.LENGTH_LONG).show()
            }else{
                mostrarDialog(list)
            }*/
            when{
                lista.isEmpty() ->Toast.makeText(this, "Ingrese Menus", Toast.LENGTH_LONG).show()
                !validarBoton() -> Toast.makeText(this, "Valide campos", Toast.LENGTH_LONG).show()
                validarPedido() -> Toast.makeText(this,"El No.Pedido ya se ha Registrado",Toast.LENGTH_LONG).show()
                else ->  mostrarDialog(list)

            }
        }

        if (Build.VERSION.SDK_INT > 25) {
            fab.tooltipText = "Agregar menu"
        }

        listView.adapter = adapter
    }
    private fun enviarListaPedidos(){
        val intent = Intent(this, VerPedidosActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        enviarListaPedidos()
        this.finish()
    }

    private fun inicializar() {
        validar()
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
        if (intent.getParcelableArrayListExtra<Empleado>("Pedidos") != null) {
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
        if(intent.getParcelableArrayListExtra<Factura>("Facturas") != null){
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
        llenarSpinnerCliente()
        llenarSpinnerEmpleado()
        llenarSpinnerMenu()
    }

    private fun llenarSpinnerCliente() {
        var clientes = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)
        for (item in listaClientes!!) {
            clientes.addAll(item.nombre)
        }
        spinnerCliente.adapter = clientes
    }

    private fun llenarSpinnerEmpleado() {
        var empleados = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)
        for (items in listaEmpleados!!) {
            if (items.puesto == "Mesero") {
                empleados.addAll(items.nombre)
            }
        }
        spinnerEmpleado.adapter = empleados
    }

    private fun llenarSpinnerMenu() {
        var menu = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)
        for (items in listaMenus!!) {
           menu.addAll(items.codigo)
        }
        spinnerMenu.adapter = menu
    }

    private fun agregarMenu(list: MutableList<ModelListView>, adapter: ListViewAdapter) {
        var codigoMenu:String? = null
        var nombreMenu:String? = null
        var precio:Double? = null
        for (items in listaMenus!!) {
            if (spinnerMenu.selectedItem.toString() == items.codigo) {
                codigoMenu = items.codigo
                nombreMenu = items.nombre
                precio = items.precio
                break
            }
        }
        lista.add(codigoMenu!!)
        var subtotal = precio
        list.add(ModelListView(codigoMenu!!, nombreMenu!!, precio!!, 1, subtotal!!))
        adapter.notifyDataSetChanged()
    }

    private fun mostrarDialog(list: MutableList<ModelListView>) {
        var mensaje = "Cliente: " + spinnerCliente.selectedItem.toString() +
                "\nEmpleado: " + spinnerEmpleado.selectedItem.toString() +
                "\nNo. Pedido: " + txtNoPedido.text.toString() +
                "\n\nMenu: \n"
        for (items in list) {
            mensaje += items.codigo + " " + items.nombre +
                    " \nSubtotal: L" + items.subtotal + "\n\n"
        }
        var total = calcular(list)
        mensaje += "\nTotal a Pagar: L" + total
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar el siguiente pedido?")
            .setMessage(mensaje)
            .setIcon(R.drawable.icono_nuevo_pedido)
            .setPositiveButton("Si"){_,_ ->
                guardarDatos(total, list)
                enviarListaPedidos()
                this.finish()
                Toast.makeText(this,"Registrado Exitosamente", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }


    private fun guardarDatos(total:Double, list: MutableList<ModelListView>) {
        var noPedido: Int
        var idCliente:String?=null
        var nombreCliente:String?=null
        var correoCliente:String?=null
        var imagen:Int?=null
        var codigoEmpleado:String?=null
        var nombreEmpleado:String?=null
        var puestoEmpleado:String?=null
        var imagen2:Int?=null
        var listamenus:ArrayList<Menu>? = ArrayList()

        noPedido = txtNoPedido.text.toString().toInt()

        for (items in listaClientes!!) {
            if (items.nombre == spinnerCliente.selectedItem.toString()) {
                idCliente = items.id
                nombreCliente = items.nombre
                correoCliente = items.correo
                imagen = items.imag
                break
            }
        }
        for (items in listaEmpleados!!) {
            if (items.puesto == "Mesero") {
                if (items.nombre == spinnerEmpleado.selectedItem.toString()) {
                    codigoEmpleado = items.codigo
                    nombreEmpleado = items.nombre
                    puestoEmpleado = items.puesto
                    imagen2 = items.imagen_
                    break
                }
            }
        }
        for (items in listaMenus!!) {
            for (item in list) {
                if (items.codigo == item.codigo) {
                    listamenus!!.add(items)
                }
            }
        }
        listaPedidos!!.add(Pedidos(noPedido,Cliente(idCliente, nombreCliente, correoCliente, imagen),
        Empleado(codigoEmpleado, nombreEmpleado, puestoEmpleado, imagen2), listamenus!!, total,
        R.drawable.ic_list_pedidos))
    }
    private fun validar(){
        txtNoPedido.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()){
                txtNoPedido.error = "No. de Pedido Vacío"
            }
            if (text.toString().length >4 ){
                txtNoPedido.error = "Número no permitido"
            }
            if (text.toString() == "0" ){
                txtNoPedido.error = "No. no debe ser menor o igual a 0"
            }
        }
    }
    private fun validarBoton():Boolean{
        when{
            txtNoPedido.text.length > 4 -> return false
            txtNoPedido.text.toString() == "0" -> return false
            else ->return true
        }
    }
    private fun validarPedido():Boolean{
        var cond=false
        for (items in listaPedidos!!){
            if (items.noPedido == txtNoPedido.text.toString().toInt()){
                cond =  true
                break
            }
        }
        return cond
    }
    private fun calcular(list: MutableList<ModelListView>):Double {
        var total:Double = 0.0
        for (items in list) {
            total += items.subtotal
        }
        return total
    }
}