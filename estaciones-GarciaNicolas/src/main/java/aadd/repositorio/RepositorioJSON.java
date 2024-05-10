package aadd.repositorio;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbConfig;
import javax.json.bind.config.PropertyNamingStrategy;
import javax.json.bind.config.PropertyOrderStrategy;
import javax.json.bind.spi.JsonbProvider;

import repositorio.EntidadNoEncontrada;
import repositorio.Identificable;
import repositorio.RepositorioException;
import repositorio.RepositorioString;

public abstract class RepositorioJSON<T extends Identificable> implements RepositorioString<T> {
	private final static String raiz = "./json/";

	protected abstract Class<?> getClase();

	public RepositorioJSON() {
	}

	@Override
	public String add(T entity) throws RepositorioException {
		String ruta = raiz + entity.getId() + ".json";
		try {
			guardarEnJSON(ruta, entity);
		} catch (IOException e) {
			throw new RepositorioException("Error de repositorio add: " + entity.getId());
		}
		return entity.getId();
	}

	@Override
	public T getById(String id) throws RepositorioException, EntidadNoEncontrada {
		String ruta = raiz + id + ".json";
		T elemento = null;
		try {
			elemento = cargarDesdeJSON(ruta);
		} catch (IOException e) {
			throw new EntidadNoEncontrada("Elemento no encontrado con el siguiente id : " + id);
		}

		return elemento;
	}

	@Override
	public List<T> getAll() throws RepositorioException {
		List<T> listaElementos = new ArrayList<T>();
		File carpeta = new File("./json/");
		T t;
		try {
			for (File f : carpeta.listFiles()) {
				t = cargarDesdeJSON(f.getPath());
				listaElementos.add(t);
			}
		} catch (IOException e) {
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
		String ruta = raiz + entity.getId() + ".json";
		File fichero = new File(ruta);
		if (fichero.exists())
			fichero.delete();
		else
			throw new EntidadNoEncontrada("Entidad no encontrado: " + entity.getId());
	}

	@Override
	public List<String> getIds() throws RepositorioException {
		List<String> listaIdElementos = new ArrayList<String>();
		File carpeta = new File("./json/");

		for (File f : carpeta.listFiles()) {
			String i = f.getName().replace(".json", "");
			listaIdElementos.add(i);
		}

		return listaIdElementos;
	}

	private T cargarDesdeJSON(String ruta) throws IOException {

		JsonbConfig config = new JsonbConfig().withNullValues(true).withFormatting(true)
				.withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
				.withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL);

		Jsonb contexto = JsonbProvider.provider().create().withConfig(config).build();
		@SuppressWarnings("unchecked")
		T elemento = (T) contexto.fromJson(new FileReader(ruta), getClase());
		return elemento;
	}

	private void guardarEnJSON(String ruta, T entity) throws IOException, RepositorioException {

		JsonbConfig config = new JsonbConfig().withNullValues(true).withFormatting(true)
				.withPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES)
				.withPropertyOrderStrategy(PropertyOrderStrategy.LEXICOGRAPHICAL);

		Jsonb contexto = JsonbProvider.provider().create().withConfig(config).build();

		FileWriter writer = new FileWriter(ruta);
		contexto.toJson(entity, writer);
	}
}
