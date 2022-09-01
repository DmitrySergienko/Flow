package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val scope = CoroutineScope(Dispatchers.IO)

       // ==1. create easy flow==================
       val flow = flow{
           repeat(10){
               kotlinx.coroutines.delay(1000)
               emit("flow $it")  //emit data (cold observable)
           }
       }

        //collect flow
        scope.launch {
            flow.collect{
                //println(it)
            }
        }
       //======================================

        // ==2. create listOf() as flow==================
        val flow2 = listOf(1,2,3,4).asFlow()


        //collect flow
        scope.launch {
            flow2.collect{
                //println("flow2 $it")
            }
        }
        //======================================
        // ==3. create flowOf() =================
        val flow3 = flowOf(listOf(2,3,4))


        //collect flow
        scope.launch {
            flow3
                .collect{
                //println("flow3 $it")
            }
        }
        //======================================

        //======Stateflow like LiveData=====to send data from VM ->UI (send data if it change)
//        val stateFlow = MutableStateFlow<Int?>(null)
//        var t = 1
//
//        //emit data
//        findViewById<View>(R.id.button).setOnClickListener {
//            stateFlow.tryEmit(t++)
//        }
//
//
//        //receive data
//        stateFlow
//            .onEach { println("VVV $it") }
//            .launchIn(scope)
        //======Sharedflow like LiveData=====to send data from VM ->UI (send data when receive)
        val sharedFlow = MutableSharedFlow<Int>(replay = 1000)
        var arg = 0

        //emit data
        findViewById<View>(R.id.button).setOnClickListener {
            sharedFlow.tryEmit(arg)
        }

        sharedFlow
            .onEach { println("VVV $it") }
            .launchIn(scope)


    }
}