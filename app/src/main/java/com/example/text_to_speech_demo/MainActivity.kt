package com.example.text_to_speech_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import com.example.text_to_speech_demo.databinding.ActivityMainBinding
import java.util.Locale
import kotlin.math.log

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    /**
     * first of wall we have to create a text to speech object
     */
    private var tts:TextToSpeech?=null
    private var binding: ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        tts =TextToSpeech(this,this)//only works if our class is listner so  we made mainactivity text to speech listner
        binding?.btn?.setOnClickListener {
            if(binding?.enteredtext?.text!!.isEmpty())
            {
                Toast.makeText(this,"Please enter a text first",
                    Toast.LENGTH_SHORT).show()
            }
            else
            {
                speakOut(binding?.enteredtext?.text.toString())
            }

        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS)
        {
            val result = tts!!.setLanguage(Locale.ENGLISH)
            if(result == TextToSpeech.LANG_MISSING_DATA ||
                result == TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Log.e("TTS","The language specified is not supported")
            }
        }
        else
        {
            Log.e("TTS","initialization failed")
        }
    }
    private fun speakOut(text:String)
    {
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")
    }

    override fun onDestroy() {
        super.onDestroy()
        if (tts !=null)
        {
            tts?.stop()
            tts?.shutdown()
        }
        binding =null
    }
}