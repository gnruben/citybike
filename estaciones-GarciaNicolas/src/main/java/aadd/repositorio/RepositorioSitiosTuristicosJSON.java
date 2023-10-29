package aadd.repositorio;

import aadd.modelo.SitioTuristico;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;


public class RepositorioSitiosTuristicosJSON extends RepositorioJSON<SitioTuristico> {
   
    public RepositorioSitiosTuristicosJSON() {
    	super();       
    }


    protected JsonObject serializar(SitioTuristico sitio) {
        JsonObjectBuilder jsonObjectBuilder = Json.createObjectBuilder()
        		.add("id", sitio.getId())
                .add("nombre", sitio.getNombre())
        		.add("resumen", sitio.getResumen())
        		.add("urlArticulo",sitio.getUrlArticulo());
             
        JsonArrayBuilder categoriasArrayBuilder = Json.createArrayBuilder();
        for (String categoria : sitio.getCategorias()) {        	
            categoriasArrayBuilder.add(categoria); 
        }
        jsonObjectBuilder.add("categorias", categoriasArrayBuilder.build());

        JsonArrayBuilder enlacesArrayBuilder = Json.createArrayBuilder();
        for (String enlace : sitio.getEnlaces()) {
            enlacesArrayBuilder.add(enlace);
        }

        jsonObjectBuilder.add("enlaces", enlacesArrayBuilder.build());


        return jsonObjectBuilder.build();
    }


    protected SitioTuristico deserializar(JsonObject jsonObject) {
        String id = jsonObject.getString("id");
        String nombre = jsonObject.getString("nombre");
        String resumen = jsonObject.getString("resumen");
        String urlArticulo = jsonObject.getString("urlArticulo");
        
        
        JsonArray categoriasArray = jsonObject.getJsonArray("categorias");
        
        List<String> categorias = new ArrayList<>();
        if (categoriasArray != null) {
	        for (JsonObject categoriaValue : categoriasArray.getValuesAs(JsonObject.class)) {
	            categorias.add(categoriaValue.toString()); //.replace("\"", ""));
	        }
        }

        JsonArray enlacesArray = jsonObject.getJsonArray("enlaces");
        List<String> enlaces = new ArrayList<>();
        if (enlacesArray != null) {
	        for (JsonObject enlaceValue : enlacesArray.getValuesAs(JsonObject.class)) {
	            enlaces.add(enlaceValue.toString()); //.replace("\"", ""));
	        }
        }

        SitioTuristico sitio = new SitioTuristico(nombre, resumen, urlArticulo);
        sitio.setId(id);
        sitio.setCategorias(categorias);
        sitio.setEnlaces(enlaces);

        return sitio;
    }

}
