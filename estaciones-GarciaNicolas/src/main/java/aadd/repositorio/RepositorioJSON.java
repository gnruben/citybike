package aadd.repositorio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import javax.json.*;
import javax.json.stream.JsonGenerator;

import repositorio.EntidadNoEncontrada;
import repositorio.Identificable;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public abstract class RepositorioJSON<T extends Identificable> implements RepositorioString<T>{
   // private List<T> elementos;
    private final static String raiz = "./json/";

    public RepositorioJSON() {
    }

    
    @Override
	public String add (T entity) throws RepositorioException {
       // elementos.add(entity);
        String ruta = raiz+entity.getId()+".json";
        try {
			guardarEnJSON(ruta, entity);
		} catch (IOException e) {
			// TODO Auto-generated catch block
	        throw new RepositorioException("Error de repositorio add: " + entity.getId());

		}
		return entity.getId();
    }

    @Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
    	String ruta = raiz+id+".json";
    	T elemento = null;
    	try {
    		 elemento = cargarDesdeJSON(ruta);
		} catch (FileNotFoundException e) {
	        throw new EntidadNoEncontrada("Elemento no encontrado con el siguiente id : " + id);

		}
    	
    	return elemento;
	
    }

    @Override
	public List<T> getAll() throws RepositorioException {
    	List<T> listaElementos = new ArrayList<T>();
    	File carpeta = new File("./json/") ;
    	T t;
    	try {
    		for( File f : carpeta.listFiles()) {
        		
        		t = cargarDesdeJSON(f.getPath());
        		listaElementos.add(t);
        	}
		} catch (FileNotFoundException e) {
	        throw new RepositorioException("Fichero no encontrado: " + carpeta);
		}
    	
        
    	return new ArrayList<>(listaElementos);
    }

    @Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {
		T t = getById(entity.getId());
		delete(t);
		add(entity);
		
	}


	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		//T t = getById(entity.getId());
		String ruta = raiz+entity.getId()+".json";
    	File fichero = new File(ruta) ;
    	if(fichero.exists())
    		fichero.delete();
    	else
            throw new EntidadNoEncontrada("Entidad no encontrado: " + entity.getId());

    
	}
	
    @Override
	public List<String> getIds() throws RepositorioException {
    	List<String> listaIdElementos = new ArrayList<String>();
    	File carpeta = new File("./json/") ;
    	
    	for( File f : carpeta.listFiles()) {
			String i = f.getName().replace(".json", "");
			listaIdElementos.add(i);
		}
        
    	return listaIdElementos;
	}

    
    private T cargarDesdeJSON(String ruta) throws FileNotFoundException {
        JsonReader reader = Json.createReader(new FileReader(ruta));
        JsonObject jsonObject = reader.readObject();
        T elemento = deserializar(jsonObject);
        return elemento;
            
    }



	private void guardarEnJSON(String ruta, T entity) throws IOException {

        JsonObject jsonObject = serializar(entity);
     
    	OutputStreamWriter writer = new FileWriter(ruta) ;
    	JsonGenerator generador=Json.createGenerator(writer); 
        generador.write(jsonObject);
        generador.close();
       
    }
    
    protected abstract JsonObject serializar(T elemento);
    protected abstract T deserializar(JsonObject jsonObject);
	
}
