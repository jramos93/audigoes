package audigoes.ues.edu.sv.session;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.TypedQuery;

import audigoes.ues.edu.sv.entities.SuperEntity;

/**
 * Session Bean implementation class audigoesSBSL
 */
@Stateless
@LocalBean
public class audigoesSBSL implements audigoesSBSLLocal {
	
	@PersistenceUnit
	private EntityManagerFactory emf;
	
	private EntityManager em;
	
	private boolean transActiva = false;

    /**
     * Default constructor. 
     */
    public audigoesSBSL() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public SuperEntity insert(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		try {
			if(!isTransActiva()) {
				et = getEm().getTransaction();
				et.begin();
			}
			getEm().persist(entidad);
			getEm().flush();
			if(!isTransActiva()) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(!isTransActiva() && et != null) {
				et.rollback();
			}
		} finally {
			if(!isTransActiva() && getEm() != null) {
				getEm().clear();
				getEm().close();
			}
		}
		return entidad;
	}

	@Override
	public SuperEntity update(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		try {
			if(!isTransActiva()) {
				et = getEm().getTransaction();
				et.begin();
			}
			entidad = (SuperEntity) getEm().merge(entidad);
			getEm().flush();
			if(!isTransActiva()) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(!isTransActiva() && et != null) {
				et.rollback();
			}
		} finally {
			if(!isTransActiva() && getEm() != null) {
				getEm().clear();
				getEm().close();
			}
		}
		return entidad;
	}

	@Override
	public void delete(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		BigDecimal pk = BigDecimal.ZERO;
		try {
			if(!isTransActiva()) {
				et = getEm().getTransaction();
				et.begin();
			}
			PersistenceUnitUtil puu = getEm().getEntityManagerFactory().getPersistenceUnitUtil();
			pk = (BigDecimal) puu.getIdentifier(entidad);
			entidad = (SuperEntity) getEm().find(entidad.getClass(), pk);
			getEm().persist(entidad);
			getEm().flush();
			if(!isTransActiva()) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if(!isTransActiva() && et != null) {
				et.rollback();
			}
		} finally {
			if(!isTransActiva() && getEm() != null) {
				getEm().clear();
				getEm().close();
			}
		}
		
	}

	@Override
	public Object findByPk(Class<? extends Serializable> clase, Object parametros) {
		Object registro = null;
		try {
			registro = getEm().find(clase, parametros);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!isTransActiva() && getEm() != null) {
				getEm().clear();
				getEm().close();
			}
		}
		return registro;
	}

	@Override
	public List<?> findByNamedQuery(Class<? extends Serializable> clase, String nameQuery, Object[] arrayParametros)
			throws Exception {
		List<?> lista = null;
	    try {
	      TypedQuery<? extends Serializable> typedQuery = getEm().createNamedQuery(nameQuery, clase);
	      if (arrayParametros != null)
	        for (int i = 0; i < arrayParametros.length; i++)
	          typedQuery.setParameter(i + 1, arrayParametros[i]);  
	        typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH"); 
	      lista = typedQuery.getResultList();
	    } catch (Exception e) {
	      e.printStackTrace();
	    } finally {
	      if (!isTransActiva() && getEm() != null) {
	        getEm().clear();
	        getEm().close();
	      } 
	    } 
	    return lista;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		return em;
	}

	public void setEm(EntityManager em) {
		this.em = em;
	}

	public boolean isTransActiva() {
		return transActiva;
	}

	public void setTransActiva(boolean transActiva) {
		this.transActiva = transActiva;
	}

}
