package aadd.repositorio;

import java.io.IOException;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import aadd.modelo.Alquiler;
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
			// TODO Auto-generated catch block
			throw new RepositorioException("No se pudo crear el repositorio");
		}
	}
	@Override
	public MongoCollection<RegistroHistoricoEstacionamiento> getCollection() {
		// TODO Auto-generated method stub
		return coleccion;
	}
	@Override
	public List<RegistroHistoricoEstacionamiento> getHistorialByIdBici(String idBici) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<String> getIdBicisByIdEstacion(String idEstacion) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public int getNumeroBicisEnEstacion(String idEstacion) {
		// TODO Auto-generated method stub
		return 0;
	}

}
