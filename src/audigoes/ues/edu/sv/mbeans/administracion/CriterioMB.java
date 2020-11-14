package audigoes.ues.edu.sv.mbeans.administracion;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import audigoes.ues.edu.sv.controller.AudigoesController;
import audigoes.ues.edu.sv.entities.administracion.Criterio;
import audigoes.ues.edu.sv.entities.administracion.NormativaCedula;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@ManagedBean(name = "criMB")
@ViewScoped
public class CriterioMB extends AudigoesController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

private List<Criterio> criterios = new ArrayList<Criterio>();
	
	public List<Criterio> getCriterios(){
		Criterio cri = new Criterio();
		cri.setCriNombre("Nombre");
		cri.setCriDescripcion("Descripcion");
		criterios.add(cri);
		
		cri = new Criterio();
		cri.setCriNombre("Nombre2");
		cri.setCriDescripcion("Descripcion 2");
		criterios.add(cri);
		
		return criterios;
	}
	
	public void exportarPDF(ActionEvent actionEvent) throws JRException, IOException{
		Map<String,Object> parametros = (Map<String, Object>) new HashMap<String,Object>();
		((HashMap<String, Object>) parametros).put("txtUsuario", "MitoCode");
		
		File jasper = new File(FacesContext.getCurrentInstance().getExternalContext().getRealPath("/rptJSF.jasper"));
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasper.getPath(),(java.util.Map<String, Object>) parametros, new JRBeanCollectionDataSource(this.getCriterios()));
		
		HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
		response.addHeader("Content-disposition","attachment; filename=jsfReporte.pdf");
		ServletOutputStream stream = response.getOutputStream();
		
		JasperExportManager.exportReportToPdfStream(jasperPrint, stream);
		
		stream.flush();
		stream.close();
		FacesContext.getCurrentInstance().responseComplete();
	}
	
	public void setCriterios(List<Criterio> criterios) {
		this.criterios = criterios;
	}
	
	private List<Criterio> filteredCriterios;

	private List<NormativaCedula> normativasList;

	public CriterioMB() {
		super(new Criterio());
	}

	@PostConstruct
	public void init() {
		try {
			super.init();
			fillListado();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public void fillListado() {
		try {
			setListado(
					(List<Criterio>) audigoesLocal.findByNamedQuery(Criterio.class, "criterio.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	public void fillNormativasList() {
		try {
			setNormativasList((List<NormativaCedula>) audigoesLocal.findByNamedQuery(NormativaCedula.class,
					"normativacedula.all", new Object[] {}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean beforeNew() {
		fillNormativasList();
		return super.beforeNew();
	}

	@Override
	public boolean beforeEdit() {
		fillNormativasList();
		return super.beforeEdit();
	}

	@Override
	public boolean beforeShow() {
		fillNormativasList();
		return super.beforeShow();
	}

	public boolean globalFilterFunction(Object value, Object filter, Locale locale) {
		String filterText = (filter == null) ? null : filter.toString().trim().toLowerCase();
		if (filterText == null || filterText.equals("")) {
			return true;
		}
		int filterInt = getInteger(filterText);

		Criterio criterio = (Criterio) value;
		return criterio.getCriNombre().toLowerCase().contains(filterText)
				|| criterio.getCriDescripcion().toLowerCase().contains(filterText) || criterio.getCriId() == filterInt;
	}

	private int getInteger(String string) {
		try {
			return Integer.valueOf(string);
		} catch (NumberFormatException ex) {
			return 0;
		}
	}

	/* GETS y SETS */

	@Override
	public Criterio getRegistro() {
		return (Criterio) super.getRegistro();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Criterio> getListado() {
		return (List<Criterio>) super.getListado();
	}

	@Override
	public void afterSaveNew() {
		getListado().add(getRegistro());
		super.afterSaveNew();
	}

	public List<Criterio> getFilteredCriterios() {
		return filteredCriterios;
	}

	public void setFilteredCriterios(List<Criterio> filteredCriterios) {
		this.filteredCriterios = filteredCriterios;
	}

	public List<NormativaCedula> getNormativasList() {
		return normativasList;
	}

	public void setNormativasList(List<NormativaCedula> normativasList) {
		this.normativasList = normativasList;
	}

}
