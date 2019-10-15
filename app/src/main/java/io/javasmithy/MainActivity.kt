package io.javasmithy

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private val CART_FILE_NAME = "cart.txt"
    private val GROCERIES_FILE_NAME = "groceries.txt"

    //  Main grocery list
    var groceryList = ArrayList<String>()
    lateinit var groceryListAdapter : ArrayAdapter<String>


    //  Cart List
    var cartList = ArrayList<String>()
    lateinit var cartListAdapter : ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            groceryList = intent.getStringArrayListExtra("groceries")
            cartList = intent.getStringArrayListExtra("cart")
        } catch (e : Exception){
            groceryList = ArrayList<String>()
            cartList = ArrayList<String>()
        }


        groceryListAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, groceryList)
        mainListView.adapter = groceryListAdapter
        cartListAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cartList)


        var item = ""
        // --------On Actions--------
        //  adds to grocery list
        addItemBtn.setOnClickListener {
            groceryList.add(addItemEditText.text.toString())
            groceryListAdapter.notifyDataSetChanged()
        }

        //  grabs last selected item
        mainListView.setOnItemClickListener { _, _, index, _ ->
            item = groceryList.get(index)
        }

        //  adds  last selected item  item to cart
        addToCartBtn.setOnClickListener {
            cartList.add(item)
            cartListAdapter.notifyDataSetChanged()

            groceryList.remove(item)
            groceryListAdapter.notifyDataSetChanged()
        }

        //  changes activity to cart
        viewCartBtn.setOnClickListener {
            val toCart = Intent(this, CartActivity::class.java)
            //writeFile(groceryList, GROCERIES_FILE_NAME )
            //writeFile(cartList, CART_FILE_NAME)
            toCart.putStringArrayListExtra("groceries", groceryList)
            toCart.putStringArrayListExtra("cart", cartList)
            startActivity(toCart)
        }

    }


    fun readFile(list : ArrayList<String>, fileName : String){
        val reader = BufferedReader(FileReader(fileName))
        var line = ""
        //val reader = Scanner(resources.openRawResource(id))
        while (line != null){
            line = reader.readLine()
            list.add(line)
        }
        reader.close()
    }


    fun writeFile(list : ArrayList<String>, fileName : String){
        val outStream = PrintStream(openFileOutput(fileName, MODE_PRIVATE))
        for ( i in 0 until (list.size-1)) {
            outStream.println(list.get(i))
        }
        outStream.close()
    }



}

