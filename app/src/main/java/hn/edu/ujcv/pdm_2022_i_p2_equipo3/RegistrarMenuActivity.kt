package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.material.snackbar.Snackbar
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Cliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Empleado
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Menu
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Pedidos
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.databinding.ActivityRegistrarMenuBinding
import kotlinx.android.synthetic.main.content_registrar_menu.*

class RegistrarMenuActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()
    var listaEmpleados:ArrayList<Empleado>? = ArrayList()
    var listaPedidos:ArrayList<Pedidos>? = ArrayList()
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityRegistrarMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrarMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        recibirInformacion()
        validaciones()
        btnRegistrarMenu.setOnClickListener {
            if(!validarRegistro()){
                Toast.makeText(this, "Comprobar los datos", Toast.LENGTH_SHORT).show()
            }else{
                registrarClase()
            }
        }
    }
    private fun registrarClase() {
        val codigo = txtCodigoMenu.text.toString()
        val nombre = txtNombreMenu.text.toString()
        val precio = txtPreciomenu.text.toString().toDouble()
        val descripcion = txtDescripcionMenu.text.toString()
        dialogo(Menu(codigo,nombre,precio,descripcion,R.drawable.ic_im_menu))
    }
    private fun recibirInformacion(){
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
    }
    private fun enviarListaMenu(){
        val intent = Intent(this, VerMenusActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        intent.putExtra("Empleados", listaEmpleados)
        intent.putExtra("Pedidos", listaPedidos)
        startActivity(intent)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        enviarListaMenu()
        this.finish()
    }
    private fun dialogo(menu: Menu){
        val dialog = AlertDialog.Builder(this)
            .setTitle("¿Desea registrar la siguiente clase?")
            .setMessage("Código:${menu.codigo}.\n" +
                    " Nombre: ${menu.nombre}.\n" +
                    " Descripción: ${menu.descripcion}.\n"+
                    " Precio: ${menu.precio}.")
            .setIcon(R.drawable.ic_nuevo_menu)
            .setPositiveButton("Si"){_,_ ->
                listaMenus?.add(menu)
                enviarListaMenu()
                this.finish()
                Toast.makeText(this,"Registro Correcto", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No"){_,_ ->
                Toast.makeText(this,"No se ha registrado", Toast.LENGTH_SHORT).show()
            }.create()
        dialog.show()
    }
    private fun validaciones(){
        txtCodigoMenu.doAfterTextChanged {txtCodigoMenu.error = validarCodigo()}
        txtNombreMenu.doAfterTextChanged {txtNombreMenu.error = validarNombre()}
        txtPreciomenu.doAfterTextChanged {txtPreciomenu.error = validarPrecio()}
        txtDescripcionMenu.doAfterTextChanged {txtDescripcionMenu.error = validarDescripcion()}
    }
    private fun validarRegistro():Boolean{
        return when{
            txtCodigoMenu.text.isNullOrEmpty() -> false
            txtCodigoMenu.text.toString().length < 4 -> false
            txtCodigoMenu.text.toString().length > 10 -> false
            txtNombreMenu.text.isNullOrEmpty() -> false
            txtNombreMenu.text.toString().length < 3 -> false
            txtNombreMenu.text.toString().length > 50 -> false
            txtPreciomenu.text.isNullOrEmpty() -> false
            txtPreciomenu.text.toString().isEmpty() -> false
            txtPreciomenu.text.toString().toDouble() <= 0.0 -> false
            txtDescripcionMenu.text.isNullOrEmpty() -> false
            txtDescripcionMenu.text.toString().length < 3 -> false
            txtDescripcionMenu.text.toString().length > 50 -> false
            else -> true
        }
    }
    private fun validarCodigo():String?{
        var m:String?=null
        try{
            if(txtCodigoMenu.text.isNullOrEmpty()){
                throw IllegalArgumentException("El código esta vacío")
            }
            if(txtCodigoMenu.text.toString().length < 4){
                throw IllegalArgumentException("El código es muy corto")
            }
            if(txtCodigoMenu.text.toString().length > 10){
                throw IllegalArgumentException("El código es muy largo")
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
            if(txtNombreMenu.text.isNullOrEmpty()){
                throw IllegalArgumentException("El nombre esta vacío")
            }
            if(txtNombreMenu.text.toString().length < 3){
                throw IllegalArgumentException("El nombre es muy corto")
            }
            if(txtNombreMenu.text.toString().length > 50){
                throw IllegalArgumentException("El nombre es muy largo")
            }
        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
    private fun validarPrecio():String?{
        var m:String?=null
        try{
            if (txtPreciomenu.text.isNullOrEmpty()) {
                throw IllegalArgumentException("Precio vacío")
            }
            if (txtPreciomenu.text.toString().isEmpty()) {
                throw IllegalArgumentException("Precio vacío")
            }
            if (txtPreciomenu.text.toString().toDouble() <= 0.0) {
                throw IllegalArgumentException("Precio no debe ser menor o igual a 0")
            }
        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
    private fun validarDescripcion():String?{
        var m:String?=null
        try{
            if(txtDescripcionMenu.text.isNullOrEmpty()){
                throw IllegalArgumentException("La descripción esta vacía")
            }
            if(txtDescripcionMenu.text.toString().length < 3){
                throw IllegalArgumentException("La descripción es muy corto")
            }
            if(txtDescripcionMenu.text.toString().length > 50 ){
                throw IllegalArgumentException("La descripción muy larga")
            }
        }catch (e:IllegalArgumentException){
            m = e.message
        }
        finally {
            return m
        }
    }
}