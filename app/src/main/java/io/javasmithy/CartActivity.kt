package io.javasmithy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_cart.*
import java.util.*
import kotlin.collections.HashMap



class CartActivity : AppCompatActivity() {
    val prices = HashMap<String, Double>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        //var groceries = intent.getStringArrayListExtra("groceries")
        //var cart = intent.getStringArrayListExtra("items");

        val cart = intent.getStringArrayListExtra("cart")
        var cartAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, cart)
        cartView.adapter = cartAdapter

        loadPrices()

        var total = calcTotal(prices, cart)
        cartTotal.setText(total.toString())


        backBtn.setOnClickListener {
            val toMain = Intent(this, MainActivity::class.java)
            toMain.putStringArrayListExtra("cart", cart)
            toMain.putStringArrayListExtra("groceries", intent.getStringArrayListExtra("groceries"))
            startActivity(toMain)
        }
    }

    fun loadPrices(){
        val reader = Scanner(resources.openRawResource(R.raw.prices))
        var str = ""
        var dbl = 0.0
        while(reader.hasNextLine()){
            var split = reader.nextLine().split(' ')
            prices.put(split.get(0), split.get(1).toDouble())
        }
    }

    fun calcTotal(map : HashMap<String,Double>, list : ArrayList<String>) : Double{
        println(list)
        var total = 0.0;
        for (i in 0 until list.size ){
            total += (map.get(list.get(i)) as Double)
        }
        return total
    }
}
