package aadd.repositorio;

import aadd.modelo.Bicicleta;
import repositorio.RepositorioJPA;

public class RepositorioBicicletasJPA extends RepositorioJPA<Bicicleta> {

	@Override
	public Class<Bicicleta> getClase() {
		// TODO Auto-generated method stub
		return Bicicleta.class;
	}

	@Override
	public String getNombre() {
		// TODO Auto-generated method stub
		return Bicicleta.class.getName();
	}

}
