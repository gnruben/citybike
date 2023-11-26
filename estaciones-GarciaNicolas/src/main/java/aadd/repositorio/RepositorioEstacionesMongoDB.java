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
import com.mongodb.client.model.BsonField;
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
		} catch (IOException e) {
			throw new RepositorioException("No se pudo crear el repositorio");
		}
	}
	/**
	 * Devuelve las estaciones en orden descendente por el número de sitios turísticos 
	 * */
	@Override
	public List<Estacion> getEstacionesTuristicas() {
		List<Estacion> list;
		//Bson project=Aggregates.project(new Document("estacion",1));
		//Bson unwind=Aggregates.unwind("$estacion");
		//Bson group=Aggregates.group("$estacion", );
		//Bson sort;
		Bson unwind=Aggregates.unwind("$sitiosTuristicos");
		Bson group=Aggregates.group("$_id", Accumulators.sum("num_sitios", 1) );
		Bson lookup=Aggregates.lookup("estaciones", "_id", "_id","estacion");
		Bson sort=Aggregates.sort(new Document("num_sitios",-1));
		
		
		//TODO: aggregation
		AggregateIterable<Document> resultado=coleccionSinCodificar.aggregate(Arrays.asList(unwind,group,lookup,sort));
		list=convertDocumentToEstacion(resultado);
		return list;
	}
/**
 * Función auxiliar de getEstacionesTuristicas
 * 
 * */
	private List<Estacion> convertDocumentToEstacion(AggregateIterable<Document> resultado) {
		List<Estacion> list=new LinkedList<Estacion>();
		
		if(resultado==null) {
			return null;
		}
		
		for(Document doc:resultado) {
			
		}
		// TODO Auto-generated method stub
		return list;
	}
	@Override
	public List<Estacion> getEstacionesCercanasA(double lat, double lng) {
		List<Estacion>list=new LinkedList<Estacion>();
		getCollection().createIndex(Indexes.geo2dsphere("ubicacion"));
		Point posicion=new Point(new Position(lng, lat));
		//Bson filter=Aggregates.geoNear(new Point(new Position(lng, lat)) ,Aggregates.nearField());
		Bson filter=Filters.near("ubicacion", posicion, null, null);
		getCollection().find(filter).forEach(e->list.add(e));//recibimos el resultado y lo añadimos a la lista
		
		return list;
	}	

	@Override
	public MongoCollection<Estacion> getCollection() {
		return coleccion;
	}
	
}
