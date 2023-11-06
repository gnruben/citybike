package repositorio;

import java.util.LinkedList;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.client.result.UpdateResult;

public abstract class RepositorioMongoDB<T extends Identificable> implements RepositorioString<T> {

	public abstract MongoCollection<T> getCollection();

	@Override
	public String add(T entity) throws RepositorioException {

		InsertOneResult resultado = getCollection().insertOne(entity);
		if (resultado.getInsertedId() == null) {
			throw new RepositorioException("Error instertando documento");
		}
		return resultado.getInsertedId().asObjectId().getValue().toString();
	}

	@Override
	public void update(T entity) throws RepositorioException, EntidadNoEncontrada {

		UpdateResult resultado = getCollection().replaceOne(new Document("_id", new ObjectId(entity.getId())), entity);
		if (resultado.getMatchedCount() == 0) {
			throw new EntidadNoEncontrada("El documento a actualizar no existe");
		}
		if (resultado.wasAcknowledged()) {
			throw new RepositorioException("El documento no se ha podido actualizar");
		}
	}

	@Override
	public void delete(T entity) throws RepositorioException, EntidadNoEncontrada {
		DeleteResult resultado = getCollection().deleteOne(new Document("_id", new ObjectId(entity.getId())));
		if (resultado.getDeletedCount() == 0) {
			throw new EntidadNoEncontrada("El documento a actualizar no existe");
		}
		if (resultado.wasAcknowledged()) {
			throw new RepositorioException("El documento no se ha podido eliminar");
		}
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		T result;
		FindIterable<T> t = getCollection().find(new Document("_id", new ObjectId(id)));
		if (t == null) {
			throw new EntidadNoEncontrada("El documento a actualizar no existe");
		}
		result = t.first();
		return result;
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		List<T> tlist = new LinkedList<T>();

		getCollection().find().forEach(t -> tlist.add(t));
		//TODO: cuándo se lanza RepositorioException?
		return tlist;
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		// TODO Auto-generated method stub

		List<String> idList = new LinkedList<String>();

		getCollection().find().forEach(t -> t.getId());
		//TODO: cuándo se lanza RepositorioException?
		return idList;
	}

}
