////package aadd.repositorio;
////
////import aadd.modelo.SitioTuristico;
////import java.util.ArrayList;
////import java.util.List;
////import javax.json.*;
////
////
////public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> {
////   
////    public RepositorioSitiosTuristicosJSON() {
////    	super();       
////    }
////
////    protected JsonObject serializar(SitioTuristico sitio) {
////    	
////        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
////        		.add("id", sitio.getId())
////                .add("nombre", sitio.getNombre())
////        		.add("urlArticulo",sitio.getUrlArticulo());
////        if(!sitio.getResumen().isEmpty())
////        	jsonObjectBuilder.add("resumen", sitio.getResumen());
////             
////        JsonArrayBuilder categoriasArrayBuilder = Json.createArrayBuilder();
////    	if(categoriasArrayBuilder != null) {
////	    	for (String categoria : sitio.getCategorias()) {        	
////	            categoriasArrayBuilder.add(categoria); 
////	        }
////    	}
////        jsonObjectBuilder.add("categorias", categoriasArrayBuilder.build());
////
////    	JsonArrayBuilder enlacesArrayBuilder = Json.createArrayBuilder();
////    	if(enlacesArrayBuilder != null) {
////	    	for (String enlace : sitio.getEnlaces()) {
////	            enlacesArrayBuilder.add(enlace);
////	        }
////    	}
////        jsonObjectBuilder.add("enlaces", enlacesArrayBuilder.build());
////
////        return jsonObjectBuilder.build();
////    }
////
////
////    protected SitioTuristico deserializar(JsonObject jsonObject) {
////        String id = jsonObject.getString("id");
////        String nombre = jsonObject.getString("nombre");
////        String resumen = jsonObject.getString("resumen");
////      
////        String urlArticulo = jsonObject.getString("urlArticulo");
////        
////        
////        JsonArray categoriasArray = jsonObject.getJsonArray("categorias");
////        
////        List<String> categorias = new ArrayList<>();
////        if (categoriasArray != null) {
////	        for (JsonValue categoriaValue : categoriasArray) {
////	            categorias.add(categoriaValue.toString()); //.replace("\"", ""));
////	        }
////        }
////
////        JsonArray enlacesArray = jsonObject.getJsonArray("enlaces");
////        List<String> enlaces = new ArrayList<>();
////        if (enlacesArray != null) {
////	        for (JsonValue enlaceValue : enlacesArray) {
////	            enlaces.add(enlaceValue.toString()); //.replace("\"", ""));
////	        }
////        }
////
////        SitioTuristico sitio = new SitioTuristico(nombre, resumen, urlArticulo);
////        sitio.setId(id);
////        sitio.setCategorias(categorias);
////        sitio.setEnlaces(enlaces);
////
////        return sitio;
////    }
////
////}
//
//import aadd.modelo.SitioTuristico;
//
//import javax.json.JsonObject;
//import javax.json.bind.Jsonb;
//import javax.json.bind.JsonbBuilder;
//import javax.json.bind.JsonbConfig;
//import javax.json.bind.JsonbException;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.ArrayList;
//import java.util.List;
//
//public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> {
//
//    private final Jsonb jsonb;
//
//    public RepositorioSitiosTuristicosJSON() {
//        super();
//        // Configurar Jsonb con opciones (si es necesario)
//        JsonbConfig config = new JsonbConfig().withFormatting(true); // Opcional, para formatear la salida JSON
//        jsonb = JsonbBuilder.create(config);
//    }
//
//    protected JsonObject serializar(SitioTuristico sitio) {
//        try {
//            StringWriter writer = new StringWriter();
//            jsonb.toJson(sitio, writer);
//            return writer;
//        } catch (JsonbException e) {
//            e.printStackTrace();
//            return null; // Maneja el error apropiadamente en tu aplicación
//        }
//    }
//
//    protected SitioTuristico deserializar(String json) {
//        try {
//            SitioTuristico sitio = jsonb.fromJson(json, SitioTuristico.class);
//            return sitio;
//        } catch (JsonbException e) {
//            e.printStackTrace();
//            return null; // Maneja el error apropiadamente en tu aplicación
//        }
//    }
//
////    // Agregar el método para deserializar una lista de objetos JSON
////    protected List<SitioTuristico> deserializarLista(String json) {
////        try {
////            // Aquí usamos Jsonb para deserializar la lista
////            SitioTuristico[] sitios = jsonb.fromJson(json, SitioTuristico[].class);
////            List<SitioTuristico> listaSitios = new ArrayList<>();
////            for (SitioTuristico sitio : sitios) {
////                listaSitios.add(sitio);
////            }
////            return listaSitios;
////        } catch (JsonbException e) {
////            e.printStackTrace();
////            return new ArrayList<>(); // Maneja el error apropiadamente en tu aplicación
////        }
////    }
//
//    // Puedes agregar otros métodos según tus necesidades
//
//    // Liberar recursos al finalizar
//    public void close() {
//        jsonb.close();
//    }
//}
//
package aadd.repositorio;

import aadd.modelo.SitioTuristico;

import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import java.util.ArrayList;
import java.util.List;

public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> {

    private Jsonb jsonb = JsonbBuilder.create(); // Crear un objeto Jsonb

    public RepositorioSitiosTuristicosJSON() {
        super();
    }

//    protected String serializar(SitioTuristico sitio) {
//        return jsonb.toJson(sitio); // Serializar el objeto a JSON
//    }
//
//    protected SitioTuristico deserializar(String jsonString) {
//        return jsonb.fromJson(jsonString, SitioTuristico.class); // Deserializar JSON a objeto
//    }

    protected Class<?> getClase(){
    	return SitioTuristico.class;
    }
   
}
