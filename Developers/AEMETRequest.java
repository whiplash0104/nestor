import java.io.*;
import okhttp3.*;
import javax.json.Json;
import javax.json.JsonReader;
import javax.json.JsonObject;


//
//Listado de estaciones meteorologicas y sus posicionamientos
//https://www.miteco.gob.es/es/cartografia-y-sig/ide/descargas/otros/default.aspx
//

public class AEMETRequest {
  public static String GetAEMETInfo (String station_id, String fecha_inicio, String fecha_fin) throws IOException{
  //public static void main(String []args) throws IOException{
  
    
    String api_key = "";

    OkHttpClient client = new OkHttpClient().newBuilder().build();

    Request request = new Request.Builder()
      .url("https://opendata.aemet.es/opendata/api/valores/climatologicos/diarios/datos/fechaini/" + fecha_inicio + "/fechafin/" + fecha_fin + "/estacion/" + station_id + "/?api_key=" + api_key )
      .get()
      .addHeader("cache-control", "no-cache")
      .build();

    Response response = client.newCall(request).execute();
    JsonReader jsonReader = Json.createReader(new StringReader(response.body().string()));
    JsonObject object = jsonReader.readObject();
    jsonReader.close();

    String datos_url = object.getString("datos");

    //System.out.println("datos url: " + datos_url + "\n");

    Request request2 = new Request.Builder()
       .url(datos_url + "?api_key=" + api_key)
       .get()
       .addHeader("cache-control", "no-cache")
       .build();

    Response response2 =client.newCall(request2).execute();

    //System.out.println(response2.body().string());
    return response2.body().string();
  }
}
