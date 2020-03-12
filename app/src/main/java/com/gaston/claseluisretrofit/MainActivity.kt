package com.gaston.claseluisretrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

  private lateinit var jsonApi:JsonAPI

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    initRetrofit()
    //fetchPost()
    //createPost()
    //updatePost()
    deletePost()
  }

  private fun initRetrofit(){
    val okHttpClient = OkHttpClient().newBuilder()
      .connectTimeout(1,TimeUnit.MINUTES)
      .readTimeout(1,TimeUnit.MINUTES)
      .writeTimeout(1,TimeUnit.MINUTES)
      .build()

    val retrofit = Retrofit.Builder()
      .baseUrl("https://jsonplaceholder.typicode.com")
      .addConverterFactory(GsonConverterFactory.create())
      .client(okHttpClient)
      .build()

    // Crear la instancia de la interfaz para usar el metodo
     jsonApi = retrofit.create(JsonAPI::class.java)
  }

  private fun fetchPost(){

    val call = jsonApi.getPosts()

    call.enqueue(object:Callback<List<Post>>{

      override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
        if(!response.isSuccessful){
          Toast.makeText(this@MainActivity,"Respuesta: ${response.code()}",Toast.LENGTH_SHORT).show()
          return
        }

        val lista = response.body()
        for(post in lista!!){
          var contenido = ""
          contenido += "${post.body}\n"
          contenido += "${post.userId}\n"
          contenido += "${post.title}\n"
          contenido += "${post.id}\n"
          Log.d("Contenido: ",contenido)
        }

      }

      override fun onFailure(call: Call<List<Post>>, t: Throwable) {
        Log.e("ERROR: ","${t.message}")
      }
    })
  }

  private fun createPost(){

    val post = Post(3,5,"Demo luis","Esta es una demo con un post nuevo")
    val call = jsonApi.createPost(post)
    call.enqueue(object:Callback<Post>{
      override fun onResponse(call: Call<Post>, response: Response<Post>) {
        if(!response.isSuccessful){
          Toast.makeText(this@MainActivity,"Respuesta: ${response.code()}",Toast.LENGTH_SHORT).show()
          return
        }

        val postResponse = response.body()
        var content = ""
        content += "Codigo: ${response.code()}\n" //si es 201 es por que se creo exitosamente
        content += "id: ${postResponse?.id}\n"
        content += "userId: ${postResponse?.userId}\n"
        content += "title: ${postResponse?.title}\n"
        content += "text: ${postResponse?.body}\n"
        Log.d("Post: ",content)

      }

      override fun onFailure(call: Call<Post>, t: Throwable) {
        Log.e("ERROR: ","${t.message}")
      }

    })
  }

  private fun updatePost(){
    val post = Post(3,5,null,"Esta es una demo con un post nuevo")
    val call = jsonApi.patchPost(5,post)
    call.enqueue(object:Callback<Post>{
      override fun onResponse(call: Call<Post>, response: Response<Post>) {
        if(!response.isSuccessful){
          Toast.makeText(this@MainActivity,"Respuesta: ${response.code()}",Toast.LENGTH_SHORT).show()
          return
        }

        val postResponse = response.body()
        var content = ""
        content += "Codigo: ${response.code()}\n" //si es 200 es por que se actualizo correctamente
        content += "id: ${postResponse?.id}\n"
        content += "userId: ${postResponse?.userId}\n"
        content += "title: ${postResponse?.title}\n"
        content += "text: ${postResponse?.body}\n"
        Log.d("Update: ",content)
      }

      override fun onFailure(call: Call<Post>, t: Throwable) {
        Log.e("ERROR: ","${t.message}")
      }

    })
  }

  private fun deletePost(){
    val call = jsonApi.deletePost(5)
    call.enqueue(object:Callback<Unit>{
      override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
        if(!response.isSuccessful){
          Toast.makeText(this@MainActivity,"Respuesta: ${response.code()}",Toast.LENGTH_SHORT).show()
          return
        }

        Log.d("Codigo delete: ","${response.code()}")

      }

      override fun onFailure(call: Call<Unit>, t: Throwable) {
        Log.e("ERROR: ","${t.message}")
      }

    })
  }
}
