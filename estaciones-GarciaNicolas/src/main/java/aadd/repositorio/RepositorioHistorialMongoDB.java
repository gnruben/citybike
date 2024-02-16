package aadd.repositorio;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import aadd.modelo.RegistroHistoricoEstacionamiento;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioHistorialMongoDB extends RepositorioMongoDB<RegistroHistoricoEstacionamiento> 
implements IRepositorioHistorialEstacionamientoAdHoc{

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<RegistroHistoricoEstacionamiento> coleccion;

	public RepositorioHistorialMongoDB() throws RepositorioException {
		PropertiesReader properties;

		try {
			properties = new PropertiesReader("mongo.properties");
			String connectionString = properties.getProperty("mongouri");
			MongoClient mongoClient = MongoClients.create(connectionString);
			String mongoDatabase = properties.getProperty("mongodatabase");
			database = mongoClient.getDatabase(mongoDatabase);
			CodecRegistry defaultCodecRegistry = CodecRegistries.fromRegistries(
					MongoClientSettings.getDefaultCodecRegistry(),
					CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));

			coleccion = database.getCollection("historial", RegistroHistoricoEstacionamiento.class).withCodecRegistry(defaultCodecRegistry);

		} catch (IOException e) {
			throw new RepositorioException("No se pudo crear el repositorio");
		}
	}
	@Override
	public MongoCollection<RegistroHistoricoEstacionamiento> getCollection() {
		return coleccion;
	}
	
	@Override
	public List<RegistroHistoricoEstacionamiento> getHistorialByIdBici(String idBici) {
		List<RegistroHistoricoEstacionamiento> listaIds = new LinkedList<RegistroHistoricoEstacionamiento>();
		Document query = new Document("idBici", idBici);
		
		FindIterable<RegistroHistoricoEstacionamiento> resultados = getCollection().find(query);
		MongoCursor<RegistroHistoricoEstacionamiento> it= resultados.iterator();
		while( it.hasNext()) {
			listaIds.add(it.next());
		}
		return listaIds;
	}
	
	@Override
	public RegistroHistoricoEstacionamiento getUltimoRegistroByIdBici(String idBici) throws RepositorioException {
		
		Document query = new Document("idBici", idBici)
	            .append("fechaFin", new Document("$exists", false));
		FindIterable<RegistroHistoricoEstacionamiento> resultados =getCollection().find(query);
		MongoCursor<RegistroHistoricoEstacionamiento> it=resultados.iterator();
		RegistroHistoricoEstacionamiento rh;
		if(it.hasNext()) {
			 rh=it.next();
			 return rh;
		}else {
			throw new RepositorioException("Error del repositorio");
		}	
	}
	
	@Override
	public List<String> getIdBicisByIdEstacion(String idEstacion) {
		List<String> listaIds=new LinkedList<String>();
		Document query = new Document("idEstacion", idEstacion)
	            .append("fechaFin", new Document("$exists", false));
		FindIterable<RegistroHistoricoEstacionamiento> resultados = getCollection().find(query);
		MongoCursor<RegistroHistoricoEstacionamiento> it=resultados.iterator();
		while( it.hasNext()) {
			listaIds.add(it.next().getIdBici());
		}
		//TODO System.out.println("El contenido de la lista de ids de la función getIdBicisByEstacion del Repositorio: ");
		/*
		 for (String s:listaIds)
			TODO System.out.println(s);
		System.out.println("#####################");
		*/
		return listaIds;
	}
	
	@Override
	public int getNumeroBicisEnEstacion(String idEstacion) {		
		return getIdBicisByIdEstacion(idEstacion).size();
	}
	@Override
	public boolean isEstacionada(String idBici) {
		Document query = new Document("idBici", idBici)
	            .append("fechaFin", new Document("$exists", false));
		FindIterable<RegistroHistoricoEstacionamiento> resultado=  getCollection().find(query);
		return resultado.iterator().hasNext();
		
	}

}
