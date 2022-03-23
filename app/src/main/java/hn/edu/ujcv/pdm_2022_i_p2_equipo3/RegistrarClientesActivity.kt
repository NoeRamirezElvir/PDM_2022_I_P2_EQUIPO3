package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.ui.AppBarConfiguration
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.*
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistrarClientesBinding
import kotlinx.android.synthetic.main.content_registrar_clientes.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class RegistrarClientesActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    var listaFacturas:ArrayList<Factura>?= ArrayList()

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistrarClientesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarClientesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        recibirInformacion()
        validaciones()

        btnRegistrarCliente.setOnClickListener {
            if(!validarRegistro()){
                Toast.makeText(this, "Comprobar los datos", Toast.LENGTH_SHORT).show()
            }else{
                registrarCliente()
            }

        }
    }
    private fun registrarCliente(){
        val id = txtIdCliente.text.toString()
        val nombre = txtnombreCliente.text.toString()
        val correo = txtCorreoCliente.text.toString()
        if(rbtMasculino.isChecked){
            dialogo(Cliente(id,nombre, correo,R.drawable.ic_mas))
        }else if(rbtFemenino.isChecked){
            dialogo(Cliente(id,nombre, correo,R.drawable.ic_fem))
        }
    }
    private fun recibirInformacion(){
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
        if (intent.getParcelableArrayListExtra<Factura>("Facturas") != null) {
            listaFacturas = intent.getParcelableArrayListExtra("Facturas")!!
        }
    }
    private fun enviarListaMenu(){
        val intent = Intent(this, VerClientesActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        intent.putExtra("Facturas", listaFacturas)
        startActivity(intent)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        enviarListaMenu()
        this.finish()
    }
    private fun dialogo(cliente: Cliente){
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar la siguiente clase?")
            .setMessage("ID:${cliente.id}.\n" +
                    " Nombre: ${cliente.nombre}.\n" +
                    " Correo: ${cliente.correo}.")
            .setIcon(R.drawable.ic_nuevo_menu)
            .setPositiveButton("Si"){_,_ ->
                listaClientes?.add(cliente)
                enviarListaMenu()
                this.finish()
                Toast.makeText(this,"Registro Correcto", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }
    private fun validarRegistro():Boolean {
        return when{
            txtIdCliente.text.isNullOrEmpty() ->false
            txtIdCliente.text.toString().length < 4 ->false
            txtIdCliente.text.toString().length > 13 ->false
            txtnombreCliente.text.isNullOrEmpty() ->false
            txtnombreCliente.text.toString().length < 3 ->false
            txtnombreCliente.text.toString().length > 50 -> false
            txtCorreoCliente.text.isNullOrEmpty() -> false
            txtCorreoCliente.text.toString().length < 3 -> false
            txtCorreoCliente.text.toString().length > 50 -> false
            !correo() -> false
            !Patterns.EMAIL_ADDRESS.matcher(txtCorreoCliente.text.toString()).matches() -> false


            else ->true
        }
    }
    private fun validaciones(){
        txtIdCliente.doAfterTextChanged {txtIdCliente.error = validarId()}
        txtnombreCliente.doAfterTextChanged {txtnombreCliente.error = validarNombre()}
        txtCorreoCliente.doAfterTextChanged {txtCorreoCliente.error = validarCorreo()}
    }
    private fun validarId():String?{
        var m:String?=null
        try{
            if(txtIdCliente.text.isNullOrEmpty()){
                throw IllegalArgumentException("El id esta vacío")
            }
            if(txtIdCliente.text.toString().length < 4){
                throw IllegalArgumentException("El id es muy corto")
            }
            if(txtIdCliente.text.toString().length > 13){
                throw IllegalArgumentException("El id es muy largo")
            }
        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
    private fun validarNombre():String?{
        var m:String?=null
        try{
            if(txtnombreCliente.text.isNullOrEmpty()){
                throw IllegalArgumentException("El id esta vacío")
            }
            if(txtnombreCliente.text.toString().length < 3){
                throw IllegalArgumentException("El id es muy corto")
            }
            if(txtnombreCliente.text.toString().length > 50){
                throw IllegalArgumentException("El id es muy largo")
            }
        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
    private fun validarCorreo():String?{
        var m:String?=null
        try{
            if(txtCorreoCliente.text.isNullOrEmpty()){
                throw IllegalArgumentException("El correo esta vacío")
            }
            if(txtCorreoCliente.text.toString().length < 3){
                throw IllegalArgumentException("El correo es muy corto")
            }
            if(txtCorreoCliente.text.toString().length > 50){
                throw IllegalArgumentException("El correo es muy largo")
            }

            if(!correo()){
                throw IllegalArgumentException("Correo invalido 1")
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(txtCorreoCliente.text.toString()).matches()){
                throw IllegalArgumentException("Correo invalido 2")
            }

        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
    private fun correo():Boolean{
        val  pattern:Pattern= Pattern.compile( "(\\W|^)[\\w.\\-]{3,25}@(hotmail|gmail|ujcv|msn|outlook|yahoo|gmx|zoho|tutonota|protonmail)\\.(com|es|org|edu|hn|uk|co|info|net|site)(\\W|$)")
        val matcher:Matcher = pattern.matcher(txtCorreoCliente.text.toString())
        return matcher.find()
    }
}