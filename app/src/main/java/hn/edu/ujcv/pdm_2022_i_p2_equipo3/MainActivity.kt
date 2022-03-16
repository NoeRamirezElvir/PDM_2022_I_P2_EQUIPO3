package hn.edu.ujcv.pdm_2022_i_p2_equipo3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Cliente
import hn.edu.ujcv.pdm_2022_i_p2_equipo3.clases.Menu
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var listaClientes:ArrayList<Cliente>? = ArrayList()
    var listaMenus:ArrayList<Menu>? = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inicio()

        btnVistaCliente.setOnClickListener {enviarListaCliente()}
        btnVistaMenu.setOnClickListener { enviarListaMenu() }
    }
    private fun inicio(){
        val intent = intent
        if(intent.getParcelableArrayListExtra<Cliente>("Clientes") != null){
            listaClientes = intent.getParcelableArrayListExtra("Clientes")!!
        }
        if(intent.getParcelableArrayListExtra<Menu>("Menu") != null){
            listaMenus = intent.getParcelableArrayListExtra("Menu")!!
        }
    }
    private fun enviarListaCliente(){
        val intent = Intent(this, VerClientesActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        startActivity(intent)
    }
    private fun enviarListaMenu(){
        val intent = Intent(this, VerMenusActivity::class.java)
        intent.putExtra("Clientes",listaClientes)
        intent.putExtra("Menu",listaMenus)
        startActivity(intent)
    }

}