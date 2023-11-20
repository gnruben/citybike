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
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;
import com.mongodb.client.model.geojson.Point;
import com.mongodb.client.model.geojson.Position;

import aadd.modelo.Estacion;
import repositorio.RepositorioException;
import repositorio.RepositorioMongoDB;
import utils.PropertiesReader;

public class RepositorioEstacionesMongoDB extends RepositorioMongoDB<Estacion> implements IRepositorioEstacionesAdHoc {

	protected MongoClient mongoClient;
	protected MongoDatabase database;
	protected MongoCollection<Estacion> coleccion;

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

		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new RepositorioException("No se pudo crear el repositorio");
		}
	}
/**
 * Devuelve las estaciones en orden descendente por el número de sitios turísticos 
 * */
	@Override
	public List<Estacion> getEstacionesTuristicas() {
		List<Estacion> list = new LinkedList<Estacion>();
		Bson project=Aggregates.project(new Document("estacion",1));
		Bson unwind=Aggregates.unwind("$estacion");
		Bson group=Aggregates.group("$estacion", );
		Bson sort;
		AggregateIterable<Estacion> resultado=getCollection().aggregate(   Arrays.asList(unwind,group,sort));
		
		return list*;//TODO: hacer la consulta en mongoDB
	}
	@Override
	public List<Estacion> getEstacionesCercanasA(double lat, double lng) {
		// TODO Auto-generated method stub
		
		getCollection().createIndex(Indexes.geo2dsphere("ubicacion"));
		Bson filter=Aggregates.geoNear(new Point(new Position(lng, lat)) ,null);
		//
		*
		return null;
	}	

	@Override
	public MongoCollection<Estacion> getCollection() {
		// TODO Auto-generated method stub
		return coleccion;
	}


}
