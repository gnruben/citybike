package aadd.repositorio;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.conversions.Bson;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.modelo.Estacion;
import repositorio.EntidadNoEncontrada;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioEstacionesMongoDB extends RepositorioMongoDB<Estacion> implements IRepositorioEstacionesAdHoc {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Estacion> coleccion;
	protected MongoCollection<Document> coleccionSinCodificar;

	public RepositorioEstacionesMongoDB() throws RepositorioException {
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

			coleccion = database.getCollection("estaciones", Estacion.class).withCodecRegistry(defaultCodecRegistry);
			coleccionSinCodificar=database.getCollection("estaciones");
			getCollection().createIndex(Indexes.geo2dsphere("ubicacion"));
		} catch (IOException e) {
			throw new RepositorioException("No se pudo crear el repositorio");
		}
	}
	/**
	 * Devuelve las estaciones en orden descendente por el número de sitios turísticos 
	 * @throws EntidadNoEncontrada 
	 * @throws RepositorioException 
	 * */
	@Override
	public List<Estacion> getEstacionesTuristicas() throws RepositorioException, EntidadNoEncontrada {
		List<Estacion> list=new LinkedList<Estacion>();

		Bson unwind=Aggregates.unwind("$sitiosTuristicos");
		Bson group=Aggregates.group("$_id", Accumulators.sum("num_sitios", 1) );
		Bson sort=Aggregates.sort(new Document("num_sitios",-1));
				
		AggregateIterable<Document> resultado=coleccionSinCodificar.aggregate(Arrays.asList(unwind,group,sort));
		for(Document doc:resultado) {
			list.add(getById(doc.get("_id").toString()));
		}	
		return list;
	}

	@Override
	public List<Estacion> getEstacionesCercanasA(double lat, double lng, int limit) {
		List<Estacion>list=new LinkedList<Estacion>();
		
		Point posicion=new Point(new Position(lng, lat));
		Bson filter=Filters.near("ubicacion", posicion, null, null);
		getCollection().find(filter).limit(limit).forEach(e->list.add(e));
		
		return list;
	}	

	@Override
	public MongoCollection<Estacion> getCollection() {
		return coleccion;
	}
	
}
