package audigoes.ues.edu.sv.session;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceUnit;
import javax.persistence.PersistenceUnitUtil;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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

	@Override
	public SuperEntity insert(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			this.getEm().persist(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!this.transActiva && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return entidad;
	}

	@Override
	public SuperEntity update(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			entidad = (SuperEntity) this.getEm().merge(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!isTransActiva() && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return entidad;
	}

	@Override
	public void delete(SuperEntity entidad) throws Exception {
		EntityTransaction et = null;
		BigDecimal pk = BigDecimal.ZERO;
		try {
			if (!this.transActiva) {
				et = this.getEm().getTransaction();
				et.begin();
			}
			PersistenceUnitUtil puu = this.getEm().getEntityManagerFactory().getPersistenceUnitUtil();
			pk = (BigDecimal) puu.getIdentifier(entidad);
			entidad = (SuperEntity) this.getEm().find(entidad.getClass(), pk);
			this.getEm().persist(entidad);
			this.getEm().flush();
			if (!this.transActiva) {
				et.commit();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (!this.transActiva && et != null) {
				et.rollback();
			}
		} finally {
			if (!this.transActiva && getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}

	}

	@Override
	public Object findByPk(Class<? extends Serializable> clase, Object parametros) {
		Object registro = null;
		try {
			registro = this.getEm().find(clase, parametros);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!this.transActiva && getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return registro;
	}

	@Override
	public List<?> findByNamedQuery(Class<? extends Serializable> clase, String nameQuery, Object[] arrayParametros)
			throws Exception {
		List<?> lista = null;
		try {
			TypedQuery<? extends Serializable> typedQuery = this.getEm().createNamedQuery(nameQuery, clase);
			if (arrayParametros != null)
				for (int i = 0; i < arrayParametros.length; i++)
					typedQuery.setParameter(i + 1, arrayParametros[i]);
			typedQuery.setHint("javax.persistence.cache.storeMode", "REFRESH");
			lista = typedQuery.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return lista;
	}

	public Object findByPropertyUnique(Class<? extends Serializable> clase, String var, Object parametros) {
		Object registro = null;

		try {
			CriteriaBuilder qb = this.getEm().getCriteriaBuilder();
			CriteriaQuery<?> cq = qb.createQuery(clase);
			Root<?> reg = cq.from(clase);
			cq.where(qb.equal(reg.get(var),parametros));
			Query q = this.getEm().createQuery(cq);
			q.setHint("javax.persistence.cache.storeMode", "REFRESH");
			registro=q.getSingleResult();
		} catch (NoResultException e) {
			;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!this.transActiva && this.getEm() != null) {
				this.getEm().clear();
				this.getEm().close();
			}
		}
		return registro;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public EntityManager getEm() {
		if (this.em == null || !this.em.isOpen()) {
			this.em = this.emf.createEntityManager();
		}
		return this.em;
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
