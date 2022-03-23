package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.R
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.navigation.ui.AppBarConfiguration
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistarFacturasBinding
import kotlinx.android.synthetic.main.content_registar_facturas.*
import kotlinx.android.synthetic.main.content_registrar_pedido.*
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

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
        rbuttoms()
        inicializar()
        setSupportActionBar(binding.toolbar)

        btnRegistrarFactura.setOnClickListener {
            if (!validarBoton() || txtNoFact.text.isNullOrEmpty()){
                Toast.makeText(this, "Valide campos", Toast.LENGTH_LONG).show()
            }else if (validarFact()){
                Toast.makeText(this,"No. de Factura ya registado",Toast.LENGTH_LONG).show()
            }else{
                mostrarDialog()
                //enviarListaFacturas()
                //this.finish()
            }

        }
    }
    private fun inicializar() {
        validar()
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
            msj1  = "\nCambio: " + String.format("%.2f",pago - monto)
        }else if (rbtTarjeta.isChecked){
            tipoP = rbtTarjeta.text.toString()
            msj2 = "\nTarjeta: " + String.format("%.2f",monto)
        }else{
            tipoP = rbtMixto.text.toString()
            msj2 = "\nTarjeta: "+ String.format("%.2f",(monto-pago))
        }
        var mensaje = "Pedido: " + spnPedidos.selectedItem.toString() +
                "\nTipo de Pago: " + tipoP +
                "\nEmpleado: " + spnEmpleadoFact.selectedItem.toString() +
                "\nMonto: "+ txvMontoTotalFact.text.toString() +
                "\nPago Efv. Cliente: " +  txtPagoCliente.text.toString() +
                 msj2 + msj1

        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar la siguiente Factura?")
            .setMessage(mensaje)
            .setIcon(hn.edu.ujcv.pdm_2022_i_p2_equipo3.R.drawable.ic_list_factura)
            .setPositiveButton("Si"){_,_ ->
                guardarDatos()
                Toast.makeText(this,"Registrado Exitosamente", Toast.LENGTH_SHORT).show()
                enviarFacturaCorreo()
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
        var tipoP:String = ""
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
                if (items.nombre == spnEmpleadoFact.selectedItem.toString()) {
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
        }else if (rbtMixto.isChecked){
            tipoP = rbtMixto.text.toString()
        }else{
            tipoP = rbtTarjeta.text.toString()
        }
        listaFacturas?.add(Factura(txtNoFact.text.toString().toInt(),Pedidos(noPedido,Cliente(idCliente, nombreCliente, correoCliente, imagen),
            Empleado(codigoEmpleado, nombreEmpleado, puestoEmpleado, imagen2), listamenus!!, total,imagenp),tipoP,txtPagoCliente.text.toString().toDouble(),
            Empleado(codigoEmpleadoC, nombreEmpleadoC, puestoEmpleadoC, imagen3),
            hn.edu.ujcv.pdm_2022_i_p2_equipo3.R.drawable.ic_list_factura))
    }

    private fun rbuttoms(){
        radioGroup1.setOnCheckedChangeListener{radioGroup, i ->
            var rb:RadioButton = findViewById(i)
            if (rb.id == rbtTarjeta.id){
                txtPagoCliente.setText("0.0")
                txtPagoCliente.isEnabled = false
            }
            if (rb.id == rbtMixto.id){
                txtPagoCliente.setText(null)
                txtPagoCliente.isEnabled = true
            }
            if (rb.id == rbtEfectivo.id){
                txtPagoCliente.setText(null)
                txtPagoCliente.isEnabled = true
            }
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
                txtPagoCliente.text.toString().toDouble() >= txvMontoTotalFact.text.toString().toDouble() -> opcion = false
                else ->return opcion
            }
        }
        return opcion
    }
    private fun validar(){
        txtNoFact.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()){
                txtNoFact.error = "No. de Factura Vacío"
            }
            if (text.toString().length >4 ){
                txtNoFact.error = "Número no permitido"
            }
            if (text.toString() == "0" ){
                txtNoFact.error = "No. no debe ser menor o igual a 0"
            }
        }
    }

    private fun validarFact():Boolean{
        var cond=false
        for (items in listaFacturas!!){
            if (items.noFact == txtNoFact.text.toString().toInt()){
               cond =  true
                break
            }
        }
        return cond
    }
    private fun enviarFacturaCorreo(){
        var cliente:String = ""
        var empleado: String= ""
        var emailAddress:String? = ""
        var mensajeMenues :String = " "
        var mensajePrinpal : String=""
        var total:String = ""
        var pie:String= ""
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val hourFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
        var tipoP:String? = " "
        var tar:String? = ""


        for (items in listaFacturas!!){
            if (txtNoFact.text.toString().toInt() == items.noFact){
                mensajePrinpal += "\nNo. de Factura: " + items.noFact + "\nNo. Pedido: " + items.pedido!!.noPedido
                cliente += "Cliente: " + items.pedido!!.cliente!!.id + "            " + items.pedido!!.cliente!!.nombre
                empleado += "Empleado: " +items.empleadoFact!!.nombre
                emailAddress= items.pedido!!.cliente!!.correo
                for (menus in items.pedido!!.listaPedidosMenu!!){
                    mensajeMenues +="\n"+menus.codigo + "           "+menus.nombre+"            "+menus.precio
                }
                if (items.tipoPago == "Efectivo"){
                    tipoP = (items.efvo!! - items.pedido!!.total!!).toString()
                }else if (items.tipoPago == "Tarjeta"){
                    tipoP = "0"
                    tar = items.pedido!!.total!!.toString()
                }else{
                    tipoP = "0"
                    tar  = (items.pedido!!.total!! - items.efvo!!).toString()
                }
                total+= "                                                                                   " + items.pedido!!.total
                pie += "\n\nTipo de Pago: " + items.tipoPago + "\nEfectivo recibido:"+items.efvo + "\nCambio: " + tipoP + "\nTarjeta: " + tar
            }
        }
        var mensaje =
                "\n\nFecha Factura: " + dateFormat.format(Date()) + "\t\t"+hourFormat.format(Date()) +mensajePrinpal+
                "\n"+ cliente + "\n" + empleado + "\n---------------------------------------------------------------------------"+
                        "\nCodigo "+ "             " +"Nombre "+"              " + "Precio Unit."+"            " + "Total" +"\n---------------------------------------------------------------------------"
        mensaje += mensajeMenues
        mensaje += "\n" + total + pie + "\n---------------------------------------------------------------------------"
        val asunto = "Factura Restaurante UJCV"
        val intent = Intent(Intent.ACTION_SEND)
        intent.data = Uri.parse("mailto:")
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, asunto)
        intent.putExtra(Intent.EXTRA_TEXT, mensaje)

        try {
            startActivity(Intent.createChooser(intent,"Elija una opción..."))
        } catch (e: Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
        }
    }
//+
//                        "\n\nTipo de Pago: " + items.tipoPago + "\nEfectivo recibido:"+items.efvo + "\nCambio: " + (items.efvo!! - items.pedido!!.total!!)
}