package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.adapter.ModelListView
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistarFacturasBinding
import kotlinx.android.synthetic.main.content_registar_facturas.*
import kotlinx.android.synthetic.main.content_registrar_pedido.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegistarFacturasActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>? = ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistarFacturasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistarFacturasBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rbt()
        inicializar()
        setSupportActionBar(binding.toolbar)

        btnRegistrarFactura.setOnClickListener {
            if (!validarBoton()){
                Toast.makeText(this, "Valide campos", Toast.LENGTH_LONG).show()
            }else{
                mostrarDialog()
            }
        }

    }
    private fun inicializar() {
        //validar()
        val intent = intent
        if (intent.getParcelableArrayListExtra<Empleado>("Empleados") != null) {
            listaEmpleados = intent.getParcelableArrayListExtra("Empleados")!!
        }
        if (intent.getParcelableArrayListExtra<Empleado>("Pedidos") != null) {
            listaPedidos = intent.getParcelableArrayListExtra("Pedidos")!!
        }
        if (intent.getParcelableArrayListExtra<Factura>("Facturas") != null) {
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
        if(intent.getParcelableArrayListExtra<Cliente>("Clientes") != null){
            listaClientes = intent.getParcelableArrayListExtra("Clientes")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Menu") != null){
            listaMenus = intent.getParcelableArrayListExtra("Menu")!!
        }
        llenarSpinnerPedido()
        llenarSpinnerEmpleado()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        enviarListaFacturas()
        this.finish()
    }

    private fun llenarSpinnerEmpleado() {
        var empleados = ArrayAdapter<String>(this,
            R.layout.simple_spinner_dropdown_item)
        for (items in listaEmpleados!!) {
            if (items.puesto == "Cajero") {
                empleados.addAll(items.nombre)
            }
        }
        spnEmpleadoFact.adapter = empleados
    }

    private fun llenarSpinnerPedido() {
        var pedidos = ArrayAdapter<String>(this,
        R.layout.simple_spinner_dropdown_item)

        for (items in listaPedidos!!){
            pedidos.addAll(items.noPedido.toString())
        }
        spnPedidos.adapter = pedidos
        spnPedidos.onItemSelectedListener = this
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        var monto : String? = listaPedidos!!.get(position).total.toString()
        txvMontoTotalFact.text = monto
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

    private fun enviarListaFacturas(){
        val intent = Intent(this, VerFacturaActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
    }

    private fun mostrarDialog() {
        var monto = txvMontoTotalFact.text.toString().toDouble()
        var pago  = txtPagoCliente.text.toString().toDouble()
        var tipoP = ""
        var msj1   = ""
        var msj2   = ""
        if (rbtEfectivo.isChecked){
            tipoP = rbtEfectivo.text.toString()
            msj1  = "\nCambio: " + (pago - monto)
        }else if (rbtTarjeta.isChecked){
            tipoP = rbtTarjeta.text.toString()
            msj2 = "Tarjeta: " + monto
        }else{
            tipoP = rbtMixto.text.toString()
            msj2 = "\nTarjeta: "+ (monto-pago)
        }
        var mensaje = "Pedido: " + spnPedidos.selectedItem.toString() +
                "\nTipo de Pago: " + tipoP +
                "\nEmpleado: " + spnEmpleadoFact.selectedItem.toString() +
                "\nMonto: "+ txvMontoTotalFact.text.toString() +
                "\nPago Efv. Cliente: " +  txtPagoCliente.text.toString() +
                 msj2 + msj1
        var msjp = ""
        for (items in listaFacturas!!){
            for (item in listaPedidos!!){
                if (item.noPedido == spnPedidos.selectedItem.toString().toInt()){
                    msj1 = "Pedido :"+ items.pedido!!.noPedido.toString() +
                            "Empleado: " + items.empleadoFact!!.nombre
                }
            }
        }

        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar la siguiente Factura?")
            .setMessage(mensaje)
            .setIcon(hn.edu.ujcv.pdm_2022_i_p2_equipo3.R.drawable.ic_list_factura)
            .setPositiveButton("Si"){_,_ ->
                guardarDatos()
                Toast.makeText(this,"Registrado Exitosamente", Toast.LENGTH_SHORT).show()
                enviarListaFacturas()
                this.finish()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }

    private fun guardarDatos() {
        var noPedido:Int? = null
        var imagenp:Int? = null
        var idCliente:String?=null
        var nombreCliente:String?=null
        var correoCliente:String?=null
        var imagen:Int?=null
        var codigoEmpleado:String?=null
        var nombreEmpleado:String?=null
        var puestoEmpleado:String?=null
        var imagen2:Int?=null
        var codigoEmpleadoC:String?=null
        var nombreEmpleadoC:String?=null
        var puestoEmpleadoC:String?=null
        var tipoP:String? = null
        var imagen3:Int?=null
        var listamenus:ArrayList<Menu>? = ArrayList()
        var total:Double? = null
        for (items in listaPedidos!!){
            if (items.noPedido == spnPedidos.selectedItem.toString().toInt()){
                noPedido       = items.noPedido
                imagenp        = items.imagen
                idCliente      = items.cliente!!.id
                nombreCliente  = items.cliente!!.nombre
                correoCliente  = items.cliente!!.correo
                imagen         = items.cliente!!.imag
                codigoEmpleado = items.empleado!!.codigo
                nombreEmpleado = items.empleado!!.nombre
                puestoEmpleado = items.empleado!!.puesto
                imagen2        = items.empleado!!.imagen_
                listamenus     = items.listaPedidosMenu
                total          = items.total!!
                break
            }
        }
        for (items in listaEmpleados!!) {
            if (items.puesto == "Cajero") {
                if (items.nombre == spinnerEmpleado.selectedItem.toString()) {
                    codigoEmpleadoC = items.codigo
                    nombreEmpleadoC = items.nombre
                    puestoEmpleadoC = items.puesto
                    imagen3 = items.imagen_
                    break
                }
            }
        }
        if (rbtEfectivo.isChecked){
            tipoP = rbtEfectivo.text.toString()
        }else if (rbtTarjeta.isChecked){
            tipoP = rbtTarjeta.text.toString()
        }else{
            tipoP = rbtMixto.text.toString()
        }
        listaFacturas?.add(Factura(Pedidos(noPedido,Cliente(idCliente, nombreCliente, correoCliente, imagen),
            Empleado(codigoEmpleado, nombreEmpleado, puestoEmpleado, imagen2), listamenus!!, total,imagenp),tipoP,txtPagoCliente.text.toString().toDouble(),
            Empleado(codigoEmpleadoC, nombreEmpleadoC, puestoEmpleadoC, imagen3),
            hn.edu.ujcv.pdm_2022_i_p2_equipo3.R.drawable.ic_list_factura))
    }

    private fun rbt(){
        if (rbtTarjeta.isChecked){
            txtPagoCliente.setText("0.0")
            txtPagoCliente.isEnabled = false
            txtPagoCliente.isFocusable = false
        }
    }

    private fun validarBoton():Boolean{
        var opcion:Boolean = true
        if ( rbtEfectivo.isChecked){
            when{
                txtPagoCliente.text.isNullOrEmpty() -> opcion = false
                txtPagoCliente.text.toString().toDouble() <= 0.0 -> opcion = false
                txtPagoCliente.text.toString().toDouble() < txvMontoTotalFact.text.toString().toDouble() -> opcion = false
                else ->return opcion
            }
        }else if (rbtMixto.isChecked){
            when{
                txtPagoCliente.text.isNullOrEmpty() -> opcion = false
                txtPagoCliente.text.toString().toDouble() <= 0.0 -> opcion = false
                txtPagoCliente.text.toString().toDouble() > txvMontoTotalFact.text.toString().toDouble() -> opcion = false
                else ->return opcion
            }
        }
        return opcion
    }

    /*private fun enviarCorreo() {
        var alumno:String = ""
        var emailAddress:String = ""
        var mensajeClases:String = ""
        for (item in listaMatricula!!) {
            if (spnAlumno.selectedItem.toString() == item.alumno.nombre) {
                alumno = "Nombre: " + item.alumno.nombre + "\nNúmero de Cuenta: " + item.alumno.cuenta
                emailAddress = item.alumno.correo
                for (clase in item.listaClases) {
                    mensajeClases += "\nCódigo: " + clase.codigo +
                            "\nClase: " + clase.nombre +
                            "\nSección: " + clase.seccion +
                            "\nHora: " + clase.hora +
                            "\nEdificio/Piso: " + clase.edificio +
                            "\nAula: " + clase.aula + "\n"
                }
                break
            }
        }
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val hourFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        var mensaje = alumno +
                "\n\nFecha de Inscripción: " + dateFormat.format(Date()) +
                "\nHora de Inscripción: " + hourFormat.format(Date()) +
                "\n\nASIGNATURAS INSCRITAS"
        mensaje += mensajeClases
        val asunto = "Inscripción de Clases"
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(Intent.createChooser(intent,"Elija una opción.."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }*/
}