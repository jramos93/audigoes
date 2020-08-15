package audigoes.ues.edu.sv.entities.administracion;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the menu database table.
 * 
 */
@Entity
@NamedQuery(name="Menu.findAll", query="SELECT m FROM Menu m")
public class Menu extends audigoes.ues.edu.sv.entities.SuperEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="mnu_id")
	private int mnuId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_crea")
	private Date fecCrea;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="fec_modi")
	private Date fecModi;

	@Column(name="mnu_correlativo")
	private int mnuCorrelativo;

	@Column(name="mnu_descripcion")
	private String mnuDescripcion;

	@Column(name="mnu_nombre")
	private String mnuNombre;

	@Column(name="mnu_tipo")
	private int mnuTipo;

	@Column(name="mnu_url")
	private String mnuUrl;

	@Column(name="reg_activo")
	private int regActivo;

	@Column(name="usu_crea")
	private String usuCrea;

	@Column(name="usu_modi")
	private String usuModi;

	//bi-directional many-to-one association to Menu
	@ManyToOne
	@JoinColumn(name="mnu_mnu_padre")
	private Menu menu;

	//bi-directional many-to-one association to Menu
	@OneToMany(mappedBy="menu")
	private List<Menu> menus;

	//bi-directional many-to-one association to RolMenu
	@OneToMany(mappedBy="menu")
	private List<RolMenu> rolMenus;

	public Menu() {
	}

	public int getMnuId() {
		return this.mnuId;
	}

	public void setMnuId(int mnuId) {
		this.mnuId = mnuId;
	}

	public Date getFecCrea() {
		return this.fecCrea;
	}

	public void setFecCrea(Date fecCrea) {
		this.fecCrea = fecCrea;
	}

	public Date getFecModi() {
		return this.fecModi;
	}

	public void setFecModi(Date fecModi) {
		this.fecModi = fecModi;
	}

	public int getMnuCorrelativo() {
		return this.mnuCorrelativo;
	}

	public void setMnuCorrelativo(int mnuCorrelativo) {
		this.mnuCorrelativo = mnuCorrelativo;
	}

	public String getMnuDescripcion() {
		return this.mnuDescripcion;
	}

	public void setMnuDescripcion(String mnuDescripcion) {
		this.mnuDescripcion = mnuDescripcion;
	}

	public String getMnuNombre() {
		return this.mnuNombre;
	}

	public void setMnuNombre(String mnuNombre) {
		this.mnuNombre = mnuNombre;
	}

	public int getMnuTipo() {
		return this.mnuTipo;
	}

	public void setMnuTipo(int mnuTipo) {
		this.mnuTipo = mnuTipo;
	}

	public String getMnuUrl() {
		return this.mnuUrl;
	}

	public void setMnuUrl(String mnuUrl) {
		this.mnuUrl = mnuUrl;
	}

	public int getRegActivo() {
		return this.regActivo;
	}

	public void setRegActivo(int regActivo) {
		this.regActivo = regActivo;
	}

	public String getUsuCrea() {
		return this.usuCrea;
	}

	public void setUsuCrea(String usuCrea) {
		this.usuCrea = usuCrea;
	}

	public String getUsuModi() {
		return this.usuModi;
	}

	public void setUsuModi(String usuModi) {
		this.usuModi = usuModi;
	}

	public Menu getMenu() {
		return this.menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	public List<Menu> getMenus() {
		return this.menus;
	}

	public void setMenus(List<Menu> menus) {
		this.menus = menus;
	}

	public Menu addMenus(Menu menus) {
		getMenus().add(menus);
		menus.setMenu(this);

		return menus;
	}

	public Menu removeMenus(Menu menus) {
		getMenus().remove(menus);
		menus.setMenu(null);

		return menus;
	}

	public List<RolMenu> getRolMenus() {
		return this.rolMenus;
	}

	public void setRolMenus(List<RolMenu> rolMenus) {
		this.rolMenus = rolMenus;
	}

	public RolMenu addRolMenus(RolMenu rolMenus) {
		getRolMenus().add(rolMenus);
		rolMenus.setMenu(this);

		return rolMenus;
	}

	public RolMenu removeRolMenus(RolMenu rolMenus) {
		getRolMenus().remove(rolMenus);
		rolMenus.setMenu(null);

		return rolMenus;
	}

}