package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.navigation.ui.AppBarConfiguration
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistrarEmpleadoBinding
import kotlinx.android.synthetic.main.content_registrar_empleado.*

class RegistrarEmpleadoActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?= ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistrarEmpleadoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistrarEmpleadoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        inicializar()

        btnRegistrarEmpleado.setOnClickListener {
            if (!validarBoton())
                Toast.makeText(this, mensaje(), Toast.LENGTH_SHORT).show()
            else
                mostrarDialog()
        }

    }
    private fun inicializar(){
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
        if (intent.getParcelableArrayListExtra<Factura>("Facturas") != null) {
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
        validar()
        llenarSpinner()
    }
    private fun enviarListaEmpleados(){
        val intent = Intent(this, VerEmpleadosActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        enviarListaEmpleados()
        this.finish()
    }
    private fun llenarSpinner() {
        var puestos = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item)
        puestos.addAll("Mesero", "Cajero")
        spinnerPuesto.adapter = puestos
    }
    private fun registrarEmpleado() {
        val codigo = txtCodigoEmpleado.text.toString()
        val nombre = txtNombreEmpleado.text.toString()
        val puesto = spinnerPuesto.selectedItem.toString()
        listaEmpleados!!.add(Empleado(codigo, nombre, puesto, R.drawable.ic_list_empleado))
    }
    private fun validar() {
        txtCodigoEmpleado.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                txtCodigoEmpleado.error = "El código no debe estar vacío"
            }
            if (text.toString().length < 5) {
                txtCodigoEmpleado.error = "Ingrese más de cinco caracteres"
            }
            if (text.toString().length > 15) {
                txtCodigoEmpleado.error = "El código es demasiado largo"
            }

        }
        txtNombreEmpleado.doOnTextChanged { text, start, before, count ->
            if (text.isNullOrEmpty()) {
                txtNombreEmpleado.error = "El nombre no debe estar vacío"
            }
            if (text.toString().length < 5) {
                txtNombreEmpleado.error = "El nombre es muy corto"
            }
            if (text.toString().length > 50) {
                txtNombreEmpleado.error = "El nombre es demasiado largo"
            }
        }

    }
    private fun mostrarDialog() {
        var mensaje = "Código: " + txtCodigoEmpleado.text +
                "\nNombre: " + txtNombreEmpleado.text +
                "\nPuesto: " + spinnerPuesto.selectedItem.toString()
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar el siguiente empleado?")
            .setMessage(mensaje)
            .setIcon(R.drawable.ic_empleado)
            .setPositiveButton("Si"){_,_ ->
                registrarEmpleado()
                enviarListaEmpleados()
                this.finish()
                Toast.makeText(this,"Registrado Exitosamente", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }
    private fun validarBoton():Boolean {
        when {
            txtCodigoEmpleado.text.isNullOrEmpty() -> return false
            txtCodigoEmpleado.text.length < 5 -> return false
            txtCodigoEmpleado.text.length > 15 -> return false
            txtNombreEmpleado.text.isNullOrEmpty() -> return false
            txtNombreEmpleado.text.length < 5 -> return false
            txtNombreEmpleado.text.length > 50 -> return false
            else -> return true
        }
    }
    private fun mensaje():String {
        val mensaje1="No deje campos vacios"
        val mensaje2="Corrija datos"
        when {
            txtCodigoEmpleado.text.isNullOrEmpty() -> return mensaje1
            txtNombreEmpleado.text.isNullOrEmpty() -> return mensaje2
            else -> return mensaje2
        }
    }

}