package com.lucasmello.investeasy

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.lucasmello.investeasy.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var tvTotal: TextView
    private lateinit var tvRendimentos: TextView
    private lateinit var tieAporteMensal: TextView
    private lateinit var tieNumMeses: TextView
    private lateinit var tieJuros: TextView
    private lateinit var btnCalculate: Button
    private lateinit var btnClean: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        tvTotal = binding.tvTotal
        tvRendimentos = binding.tvRendimentos
        tieAporteMensal = binding.tieAporteMensal
        tieNumMeses = binding.tieNumMeses
        tieJuros = binding.tieJuros
        btnCalculate = binding.btnCalculate
        btnClean = binding.btnClean

        btnCalculate.setOnClickListener {
            if (tieAporteMensal.text.isNotEmpty() && tieNumMeses.text.isNotEmpty() && tieJuros.text.isNotEmpty()) {
                calculateTotal()
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
        btnClean.setOnClickListener {
            tieAporteMensal.text = ""
            tieNumMeses.text = ""
            tieJuros.text = ""
            tvTotal.text = "0.0"
            tvRendimentos.text = "0.0"
        }
    }

    private fun calculateTotal(){

            val aporteMensal = tieAporteMensal.text.toString().toDoubleOrNull() ?: 0.0
            val numMeses = tieNumMeses.text.toString().toIntOrNull() ?: 0
            val juros = tieJuros.text.toString().toDoubleOrNull() ?: 0.0

            if (numMeses > 0 && juros > 0) {
                val taxaMensal = juros / 100  // Converte a porcentagem para decimal
                val montanteFinal = aporteMensal * ((1 + taxaMensal).pow(numMeses) - 1) / taxaMensal
                val totalInvestido = aporteMensal * numMeses
                val rendimentos = montanteFinal - totalInvestido

                // Formatar valores no padrão brasileiro (R$ 32.200,83)
                val formatoBR = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))

                tvTotal.text = "${formatoBR.format(montanteFinal)}"
                tvRendimentos.text = "${formatoBR.format(rendimentos)}"
            } else {
                tvTotal.text = "Valores inválidos"
                tvRendimentos.text = ""
            }
        }
    }




