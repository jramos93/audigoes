package audigoes.ues.edu.sv.session;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;

import audigoes.ues.edu.sv.entities.SuperEntity;

@Local
public interface audigoesSBSLLocal {
	SuperEntity insert(SuperEntity entidad) throws Exception;
	SuperEntity update(SuperEntity entidad) throws Exception;
	void delete(SuperEntity entidad) throws Exception;
	Object findByPk(Class<? extends Serializable> clase, Object parametros);
	List<?> findByNamedQuery(Class<? extends Serializable> clase, String nameQuery, Object[] arrayParametros) throws Exception;
	Object findByPropertyUnique(Class<? extends Serializable> clase, String var, Object parametros);
}
