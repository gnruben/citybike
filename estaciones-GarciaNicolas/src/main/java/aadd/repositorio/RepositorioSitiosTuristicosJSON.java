package aadd.repositorio;

import aadd.modelo.SitioTuristico;
import repositorio.EntidadNoEncontrada;
import repositorio.Repositorio;
import repositorio.RepositorioException;

import java.util.ArrayList;
import java.util.List;

public class RepositorioSitiosTuristicosJSON implements Repositorio<SitioTuristico, String> {

    private RepositorioJSON<SitioTuristico> repositorioJSON;

    public RepositorioSitiosTuristicosJSON() {
        this.repositorioJSON = new RepositorioJSON<>("sitiosTuristicos.json");
    }

    @Override
    public String add(SitioTuristico entity) throws RepositorioException {
        return repositorioJSON.add(entity);
    }

    @Override
    public void update(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
        List<SitioTuristico> sitios = repositorioJSON.getAll();
        boolean found = false;
        for (SitioTuristico sitio : sitios) {
            if (sitio.getNombre().equals(entity.getNombre())) {
                found = true;
                break;
            }
        }
        if (found) {
            repositorioJSON.update(entity);
        } else {
            throw new EntidadNoEncontrada("El sitio turístico no existe en el repositorio.");
        }
    }

    @Override
    public void delete(SitioTuristico entity) throws RepositorioException, EntidadNoEncontrada {
        repositorioJSON.delete(entity);
    }

    @Override
    public SitioTuristico getById(String id) throws RepositorioException, EntidadNoEncontrada {
        List<SitioTuristico> sitios = repositorioJSON.getAll();
        for (SitioTuristico sitio : sitios) {
            if (sitio.getNombre().equals(id)) {
                return sitio;
            }
        }
        throw new EntidadNoEncontrada("El sitio turístico no existe en el repositorio.");
    }

    @Override
    public List<SitioTuristico> getAll() throws RepositorioException {
        return repositorioJSON.getAll();
    }

    @Override
    public List<String> getIds() throws RepositorioException {
        List<String> ids = new ArrayList<>();
        for (SitioTuristico sitio : getAll()) {
            ids.add(sitio.getNombre());
        }
        return ids;
    }
}
