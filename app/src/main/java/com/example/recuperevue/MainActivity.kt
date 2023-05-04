package com.example.recuperevue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.util.Log
import org.eclipse.paho.android.service.MqttAndroidClient
import org.eclipse.paho.client.mqttv3.*
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import android.widget.ArrayAdapter
import android.widget.Spinner




class MainActivity : AppCompatActivity() {


    fun connexion(view: View) {
        val titre = findViewById<TextView>(R.id.Titre)
        val identifiant = findViewById<EditText>(R.id.identifiant)
        val mdp = findViewById<EditText>(R.id.MotDePasse)
        val connexion = findViewById<Button>(R.id.connexion)
        connexion.setOnClickListener {
            val intent = Intent(this, Qualite::class.java)
            startActivity(intent)
        }

        if (identifiant.text.toString() == "wilfrid" && mdp.text.toString() == "Patate123"){
            val intent = Intent(this, Qualite::class.java)
            startActivity(intent)
        } else {
            // Afficher un message d'erreur pour informer l'utilisateur
            Toast.makeText(this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {//il faudrait mettre connexion dedans et enable le bouton tant que des infos ne sont pas renté dans les champs
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //initialiser le choix de langue
        val languages = resources.getStringArray(R.array.choix_langues).toList()
        val spinner = findViewById<Spinner>(R.id.spinner_langues)
        val adapter = ArrayAdapter.createFromResource(this, R.array.choix_langues, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        /*
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                // TODO: Changer la langue de l'application
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire lorsque rien n'est sélectionné
            }
        }*/





        val btnConnexion = findViewById<Button>(R.id.connexion)
        btnConnexion.setOnClickListener {
            val intent = Intent(this, Qualite::class.java)
            Log.d("MainActivity", "Bouton de connexion appuyé")
            startActivity(intent)
        }



        val brokerURL = "tcp://172.16.5.202:1883"
        val clientId = "pi"
        val topic = "LaPorte/qualite_air"
        val qos = 2 // Quality of Service

        /*
        // Créer un client MQTT
        try {
            val client = MqttAndroidClient(this, brokerURL, clientId)
            // Configurer une callback pour la réception des messages
            val options = MqttConnectOptions()
            options.isCleanSession = true
            options.keepAliveInterval = 60
            options.connectionTimeout = 30
            options.isCleanSession = true
            client.setCallback(object : MqttCallbackExtended {
                override fun connectComplete(reconnect: Boolean, serverURI: String?) {
                    // Connecté au broker

                    client.subscribe(topic, qos)
                    println("MQTT Connection : Complete")
                }

                override fun connectionLost(cause: Throwable?) {
                    // Connexion perdue
                    println("MQTT Connection : Lost")
                }

                override fun messageArrived(topic: String?, message: MqttMessage?) {
                    // Message reçu
                    /*val payload = message?.payload
                    if (payload != null) {
                        val payloadString = String(payload, Charsets.UTF_8)
                        /*donnee.setText(payloadString)*/
                        println("MQTT Message: $payloadString")
                    }*/
                }




                override fun deliveryComplete(token: IMqttDeliveryToken?) {
                    // Message délivré
                    println("MQTT Delivery")
                }
            })
            client.connect(options, this, object : IMqttActionListener {
                override fun onSuccess(asyncActionToken: IMqttToken?) {
                    // Connexion réussie
                    println("MQTT Connection : Reussie")
                }

                override fun onFailure(asyncActionToken: IMqttToken?, exception: Throwable?) {
                    // Connexion échouée
                    println("MQTT Connection : Failed")
                    exception?.printStackTrace()
                }
            })

            // Connecter au broker
        } catch (ex: MqttException) {
            // Gérer l'exception et imprimer le code d'erreur
            println("MQTT Connection : Erreur de connexion au broker MQTT: ${ex.reasonCode}")
        }
        */

    }



}