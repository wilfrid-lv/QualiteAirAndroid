package com.example.recuperevue

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.eclipse.paho.client.mqttv3.*
import android.widget.Toast
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.AdapterView
import android.content.res.Configuration
import androidx.appcompat.app.AlertDialog
import android.app.Activity
import android.content.Context
import java.util.Locale


class MainActivity : AppCompatActivity() {

    //lateinit var locale: Locale
    lateinit var mBtn :Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(R.layout.activity_main)
        setLocate("fr")
        //loadLocate()



        val btnConnexion = findViewById<Button>(R.id.connexion)
        btnConnexion.setOnClickListener {
            val intent = Intent(this, Qualite::class.java)
            //Log.d("MainActivity", "Bouton de connexion appuyé")
            startActivity(intent)
        }
        val actionBar = supportActionBar
        actionBar!!.title = resources.getString(R.string.app_name)


        mBtn = findViewById(R.id.langue)

        mBtn.setOnClickListener {

            showChangeLang()

        }
    }

    private fun showChangeLang() {

        val listItmes = arrayOf("Français", "English", "Español")

        val mBuilder = AlertDialog.Builder(this@MainActivity)
        mBuilder.setTitle("Choose Language")
        mBuilder.setSingleChoiceItems(listItmes, -1) { dialog, which ->
            if (which == 0) {
                setLocate("fr")
                recreate()
            } else if (which == 1) {
                setLocate("en")
                recreate()
            } else if (which == 2) {
                setLocate("es")
                recreate()
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()

        mDialog.show()

    }

    private fun setLocate(Lang: String) {
        val locale = Locale(Lang)
        Locale.setDefault(locale)
        val config = Configuration()
        config.setLocale(locale)
        val newContext = createConfigurationContext(config)
        //baseContext.resources.updateConfiguration(config, baseContext.resources.displayMetrics)
        val editor = getSharedPreferences("Settings", Context.MODE_PRIVATE).edit()
        editor.putString("My_Lang", Lang)
        editor.apply()
    }

    private fun loadLocate() {
        val sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("My_Lang", null)
        if (language != null) {
            setLocate(language)
        }
    }

    fun connexion(view: View) {
        val titre = findViewById<TextView>(R.id.Titre)
        val identifiant = findViewById<EditText>(R.id.identifiant)
        val mdp = findViewById<EditText>(R.id.MotDePasse)
        val connexion = findViewById<Button>(R.id.connexion)
        /*connexion.setOnClickListener {
            val intent = Intent(this, Qualite::class.java)
            startActivity(intent)
        }*/

        if (identifiant.text.toString() == "wilfrid" && mdp.text.toString() == "Patate123"){
            val intent = Intent(this, Qualite::class.java)
            startActivity(intent)
        } else {
            // Afficher un message d'erreur pour informer l'utilisateur
            Toast.makeText(this, "Identifiant ou mot de passe incorrect", Toast.LENGTH_SHORT).show()
        }
    }

       /*private fun setLocale (languageName : String){
            locale = Locale(languageName)
            val res = resources
            val dm = res.displayMetrics
            val conf = res.configuration
            val localeList = ArrayList<Locale>()
            localeList.add(locale)
            conf.setLocale(locale)
            res.updateConfiguration(conf, dm)

           /* var refresh = Intent(ActivityMainBinding@this,ActivityMainBinding::class.java)
            startActivity(intent)*/
           val refresh = Intent(this, MainActivity::class.java)
           startActivity(refresh)
           finish()
        }*/

        /*
        //initialiser le choix de langue
        val languages = resources.getStringArray(R.array.choix_langues).toList()
        val spinner = findViewById<Spinner>(R.id.spinner_langues)
        val adapter = ArrayAdapter.createFromResource(this, R.array.choix_langues, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedLanguage = languages[position]
                //if(spinner.selectedLanguage.tooString == 'Français')
                val selectedLanguage = languages[position]

                if(selectedLanguage == "Français") {
                    // Charger les ressources pour la langue française
                    val config = Configuration(resources.configuration)
                    config.setLocale(Locale.FRENCH)
                    resources.updateConfiguration(config, resources.displayMetrics)
                }
                else {
                    // Charger les ressources pour la langue par défaut (anglais)
                    val config = Configuration(resources.configuration)
                    config.setLocale(Locale.getDefault())
                    resources.updateConfiguration(config, resources.displayMetrics)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Ne rien faire lorsque rien n'est sélectionné
            }
        }*/





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