package com.pagosonline.banco.controlador;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.pagosonline.banco.servicio.ServicioCliente;
import com.pagosonline.banco.servicio.ServicioCuenta;
import com.pagosonline.banco.servicio.ServicioMovimiento;
import com.pagosonline.banco.dominio.Cliente;
import com.pagosonline.banco.dominio.Cuenta;
import com.pagosonline.banco.dominio.Movimiento;
import com.pagosonline.banco.utiles.ClienteResTran;
import com.pagosonline.banco.utiles.Paginas;

/**
 * Handles requests for the application home page.
 */
@RequestMapping("/")
@Controller
public class Controlador {

	private static final Logger logger = LoggerFactory
			.getLogger(Controlador.class);

	@Autowired
	private ServicioCliente servicioCliente;
	@Autowired
	private ServicioCuenta servicioCuenta;
	@Autowired
	private ServicioMovimiento servicioMovimiento;

	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);

		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG,
				DateFormat.LONG, locale);

		String formattedDate = dateFormat.format(date);

		Paginas<Cliente> cliente = servicioCliente.ObtenerClientes("", "", "",
				1);

		model.addAttribute("cliente", cliente.getResultado());
		model.addAttribute("serverTime", formattedDate);

		return "home";
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.GET)
	public String AdmoCliente(Locale locale, Model model, HttpSession session) {

		final int pagina = 1;

		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";
		String direccion = (session.getAttribute("direccion") != null) ? session
				.getAttribute("direccion").toString() : "";
		String telefono = (session.getAttribute("telefono") != null) ? session
				.getAttribute("telefono").toString() : "";

		Paginas<Cliente> clientes = servicioCliente.ObtenerClientes(nombre,
				direccion, telefono, pagina);

		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", clientes.getNroPaginas());
		model.addAttribute("clientes", clientes.getResultado());

		return "AdmoCliente";
	}

	@RequestMapping(value = "/cliente", method = RequestMethod.POST)
	public String BuscarClientes(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "direccion", required = false) String direccion,
			@RequestParam(value = "telefono", required = false) String telefono,
			Model model, HttpSession session) {

		final int pagina = 1;

		Paginas<Cliente> clientes = servicioCliente.ObtenerClientes(nombre,
				direccion, telefono, pagina);
		session.setAttribute("nombre", nombre);
		session.setAttribute("direccion", direccion);
		session.setAttribute("telefono", telefono);

		model.addAttribute("clientes", clientes.getResultado());
		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", clientes.getNroPaginas());

		return "AdmoCliente";
	}

	@RequestMapping(value = "/cliente/{pagina}", method = RequestMethod.GET)
	public String BuscarClientes(
			@PathVariable(value = "pagina") Integer pagina, Model model,
			HttpSession session) {

		if (pagina == null) {
			pagina = 1;
		}

		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";
		String direccion = (session.getAttribute("direccion") != null) ? session
				.getAttribute("direccion").toString() : "";
		String telefono = (session.getAttribute("telefono") != null) ? session
				.getAttribute("telefono").toString() : "";

		Paginas<Cliente> clientes = servicioCliente.ObtenerClientes(nombre,
				direccion, telefono, pagina);

		model.addAttribute("clientes", clientes.getResultado());
		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", clientes.getNroPaginas());

		return "AdmoCliente";
	}

	@RequestMapping(value = "/crear/{pagina}", method = RequestMethod.GET)
	public String CrearCliente(@PathVariable(value = "pagina") int pagina,
			Model model) {
		Cliente cliente = new Cliente();
		model.addAttribute("cliente", cliente);
		model.addAttribute("pagina", pagina);
		return "ActualizaCliente";
	}

	@RequestMapping(value = "/crear/{pagina}", method = RequestMethod.POST)
	public String CrearClienteForm(@Valid Cliente cliente,
			BindingResult bindingResult,
			@PathVariable(value = "pagina") int pagina,
			RedirectAttributes redirectAttributes, Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("mensaje", "No se puedo crear el Cliente.");
			return "ActualizaCliente";

		} else {
			servicioCliente.SalvarCliente(cliente);
			redirectAttributes.addFlashAttribute("mensaje", "Cliente Creado.");
			return "redirect:/cliente/" + pagina;
		}
	}

	@RequestMapping(value = "/actualizar/{id}/{pagina}", method = RequestMethod.GET)
	public String ActualizarCliente(@PathVariable("id") Long idCliente,
			@PathVariable(value = "pagina") int pagina, Model model) {
		model.addAttribute("cliente", servicioCliente.ObtenerCliente(idCliente));
		model.addAttribute("pagina", pagina);
		return "ActualizaCliente";
	}

	@RequestMapping(value = "/actualizar/{id}/{pagina}", method = RequestMethod.POST)
	public String ActualizarClienteForm(@Valid Cliente cliente,
			BindingResult bindingResult,
			@PathVariable(value = "pagina") int pagina,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mensaje", "No se puedo actualizar el Cliente.");
			return "ActualizaCliente";

		}
		servicioCliente.SalvarCliente(cliente);
		redirectAttributes.addFlashAttribute("mensaje", "Cliente Actualizado.");

		return "redirect:/cliente/" + pagina;
	}

	@RequestMapping(value = "/borrar/{pagina}", method = RequestMethod.POST)
	public String BorrarCliente(@PathVariable(value = "pagina") int pagina,
			@RequestParam(value = "ids", required = false) Long[] idClientes,
			Model model) {

		if (idClientes != null && idClientes.length > 0) {
			servicioCliente.BorrarClientes(idClientes);
		}

		return "redirect:/cliente/" + pagina;
	}

	// **************************Cuentas************************************************************

	@RequestMapping(value = "/cuenta", method = RequestMethod.GET)
	public String AdmoCuentas(Locale locale, Model model, HttpSession session) {

		final int pagina = 1;

		String numero = (session.getAttribute("numero") != null) ? session
				.getAttribute("numero").toString() : "";
		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";

		Paginas<Cuenta> cuentas = servicioCuenta.ObtenerCuentas(numero, nombre,
				pagina);
		model.addAttribute("cuentas", cuentas.getResultado());
		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", cuentas.getNroPaginas());
		model.addAttribute("accion", "cuenta");

		return "AdmoCuenta";
	}

	@RequestMapping(value = "/cuenta", method = RequestMethod.POST)
	public String BuscarCuentas(
			@RequestParam(value = "numero", required = false) String numero,
			@RequestParam(value = "nombre", required = false) String nombre,
			Model model, HttpSession session) {

		final int pagina = 1;

		Paginas<Cuenta> cuentas = servicioCuenta.ObtenerCuentas(numero, nombre,
				pagina);
		session.setAttribute("numero", numero);
		session.setAttribute("nombre", nombre);

		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", cuentas.getNroPaginas());
		model.addAttribute("cuentas", cuentas.getResultado());
		model.addAttribute("accion", "cuenta");

		return "AdmoCuenta";
	}

	@RequestMapping(value = "/cuenta/{pagina}", method = RequestMethod.GET)
	public String BuscarCuentas(@PathVariable(value = "pagina") Integer pagina,
			Model model, HttpSession session) {

		if (pagina == null) {
			pagina = 1;
		}

		String numero = (session.getAttribute("numero") != null) ? session
				.getAttribute("numero").toString() : "";
		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";

		Paginas<Cuenta> cuentas = servicioCuenta.ObtenerCuentas(numero, nombre,
				pagina);

		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", cuentas.getNroPaginas());
		model.addAttribute("cuentas", cuentas.getResultado());
		model.addAttribute("accion", "cuenta");

		return "AdmoCuenta";
	}

	@RequestMapping(value = "/cuenta/crear/{pagina}", method = RequestMethod.GET)
	public String CrearCuenta(@PathVariable(value = "pagina") int pagina,
			Model model) {
		Cuenta cuenta = new Cuenta();
		model.addAttribute("clientes", servicioCliente.ObtenerClientes());
		model.addAttribute("cuenta", cuenta);
		model.addAttribute("pagina", pagina);
		return "ActualizaCuenta";
	}

	@RequestMapping(value = "/cuenta/crear/{pagina}", method = RequestMethod.POST)
	public String CrearCuentaForm(@Valid Cuenta cuenta,
			BindingResult bindingResult,
			@PathVariable(value = "pagina") int pagina,
			@RequestParam(value = "clienteS", required = false) Long idCliente,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mensaje", "No se puedo crear la Cuenta.");
			return "ActualizaCuenta";

		} else {
			servicioCuenta.SalvarCuenta(idCliente, cuenta);
			redirectAttributes.addFlashAttribute("mensaje", "Cuenta Creada.");
			return "redirect:/cuenta/" + pagina;
		}
	}

	@RequestMapping(value = "/cuenta/actualizar/{id}/{pagina}", method = RequestMethod.GET)
	public String ActualizarCuenta(@PathVariable("id") Long idCuenta,
			@PathVariable(value = "pagina") int pagina, Model model) {
		model.addAttribute("cuenta", servicioCuenta.ObtenerCuenta(idCuenta));
		model.addAttribute("clientes", servicioCliente.ObtenerClientes());
		model.addAttribute("pagina", pagina);
		return "ActualizaCuenta";
	}

	@RequestMapping(value = "/cuenta/actualizar/{id}/{pagina}", method = RequestMethod.POST)
	public String ActualizarCuentaForm(@Valid Cuenta cuenta,
			BindingResult bindingResult,
			@RequestParam(value = "clienteS", required = true) Long idCliente,
			@PathVariable(value = "pagina") int pagina,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("mensaje", "No se puedo actualizar la Cuenta.");
			 model.addAttribute("clientes", servicioCliente.ObtenerClientes());
			return "ActualizaCuenta";

		}
		servicioCuenta.SalvarCuenta(idCliente, cuenta);
		redirectAttributes.addFlashAttribute("mensaje", "Cuenta Actualizado.");
		redirectAttributes.addFlashAttribute("accion", "cuenta");
		return "redirect:/cuenta/" + pagina;
	}

	@RequestMapping(value = "/cuenta/borrar/{pagina}", method = RequestMethod.POST)
	public String BorrarCuenta(@PathVariable(value = "pagina") int pagina,
			@RequestParam(value = "ids", required = false) Long[] idCuentas,
			Model model) {

		if (idCuentas != null && idCuentas.length > 0) {
			servicioCuenta.BorrarCuentas(idCuentas);
		}
		model.addAttribute("accion", "cuenta");

		return "redirect:/cuenta/" + pagina;
	}

	// **************************Movimientos************************************************************

	@RequestMapping(value = "/movimiento", method = RequestMethod.GET)
	public String ConsultarCuentas(Model model, HttpSession session) {

		final int pagina = 1;

		String numero = (session.getAttribute("numero") != null) ? session
				.getAttribute("numero").toString() : "";
		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";

		Paginas<Cuenta> cuentas = servicioCuenta.ObtenerCuentas(numero, nombre,
				pagina);
		model.addAttribute("cuentas", cuentas.getResultado());
		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", cuentas.getNroPaginas());
		model.addAttribute("accion", "movimiento");

		return "AdmoCuenta";
	}

	@RequestMapping(value = "/movimiento/{pagina}", method = RequestMethod.GET)
	public String ConsultarCuentas(
			@PathVariable(value = "pagina") Integer pagina, Model model,
			HttpSession session) {

		if (pagina == null) {
			pagina = 1;
		}

		String numero = (session.getAttribute("numero") != null) ? session
				.getAttribute("numero").toString() : "";
		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";

		Paginas<Cuenta> cuentas = servicioCuenta.ObtenerCuentas(numero, nombre,
				pagina);

		model.addAttribute("pagina", pagina);
		model.addAttribute("paginas", cuentas.getNroPaginas());
		model.addAttribute("cuentas", cuentas.getResultado());
		model.addAttribute("accion", "movimiento");

		return "AdmoCuenta";
	}

	@RequestMapping(value = "/movimiento/{id}/{pagina}", method = RequestMethod.GET)
	public String CrearMovimiento(@PathVariable(value = "id") Long idCuenta,
			@PathVariable(value = "pagina") int pagina, Model model) {
		model.addAttribute("movimiento", new Movimiento());
		model.addAttribute("cuenta", servicioCuenta.ObtenerCuenta(idCuenta));
		model.addAttribute("pagina", pagina);
		return "CrearMovimiento";
	}

	@RequestMapping(value = "/movimiento/{id}/{pagina}", method = RequestMethod.POST)
	public String CrearMovimiento(
			@Valid Movimiento movimiento,
			BindingResult bindingResult,
			@PathVariable(value = "pagina") int pagina,
			@PathVariable(value = "id") Long idCuenta,
			@RequestParam(value = "tipo", required = false) int tipoMovimiento,
			@RequestParam(value = "valor", required = false) Integer valorMovimiento,
			RedirectAttributes redirectAttributes, Model model) {

		if (bindingResult.hasErrors()) {
			model.addAttribute("accion", "movimiento");
			model.addAttribute("pagina", pagina);
			model.addAttribute("cuenta", servicioCuenta.ObtenerCuenta(idCuenta));
			model.addAttribute("mensaje", "No se pudo crear el Movimiento.");
			return "CrearMovimiento";

		}
		if (!servicioMovimiento.CrearMovimiento(idCuenta, valorMovimiento,
				tipoMovimiento)) {
			model.addAttribute("cuenta", servicioCuenta.ObtenerCuenta(idCuenta));
			model.addAttribute(
					"mensaje",
					"No se pudo crear el Movimiento, el saldo resultante del movimiento no es valido");
			return "CrearMovimiento";
		}

		redirectAttributes.addFlashAttribute("mensaje", "Movimiento Creado.");
		redirectAttributes.addFlashAttribute("accion", "movimiento");
		return "redirect:/movimiento/" + pagina;
	}

	// **************************Reporte************************************************************

	@RequestMapping(value = "/reporte", method = RequestMethod.GET)
	public String ReporteMovimientos(Locale locale, Model model) {

		return "Reporte";
	}

	@RequestMapping(value = "/reporte", method = RequestMethod.POST)
	public String ReporteMovimientosForm(
			@RequestParam(value = "nombre", required = false) String nombre,
			@RequestParam(value = "fechaIni", required = false) String fechaIni,
			@RequestParam(value = "fechaFin", required = false) String fechaFin,
			Model model, HttpSession session) {

		final int pagina = 1;

		session.setAttribute("nombre", nombre);
		session.setAttribute("fechaIni", fechaIni);
		session.setAttribute("fechaFin", fechaFin);

		Paginas<ClienteResTran> reporte = servicioCliente.ObtenerReporte(
				nombre, fechaIni, fechaFin, pagina);

		model.addAttribute("clientes", reporte.getResultado());
		model.addAttribute("paginas", reporte.getNroPaginas());
		model.addAttribute("pagina", pagina);

		return "Reporte";
	}

	@RequestMapping(value = "/reporte/{pagina}", method = RequestMethod.GET)
	public String ReporteMovimientos(
			@PathVariable(value = "pagina") Integer pagina, Model model,
			HttpSession session) {

		if (pagina == null) {
			pagina = 1;
		}

		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";
		String fechaIni = (session.getAttribute("fechaIni") != null) ? session
				.getAttribute("fechaIni").toString() : "";
		String fechaFin = (session.getAttribute("fechaFin") != null) ? session
				.getAttribute("fechaFin").toString() : "";

		Paginas<ClienteResTran> reporte = servicioCliente.ObtenerReporte(
				nombre, fechaIni, fechaFin, pagina);

		model.addAttribute("clientes", reporte.getResultado());
		model.addAttribute("paginas", reporte.getNroPaginas());
		model.addAttribute("pagina", pagina);

		return "Reporte";
	}

	@RequestMapping(value = "/reporte/exportar", method = RequestMethod.GET)
	public String ReporteExpo(Model model,
			HttpSession session,HttpServletResponse httpServletResponse) {

		String nombre = (session.getAttribute("nombre") != null) ? session
				.getAttribute("nombre").toString() : "";
		String fechaIni = (session.getAttribute("fechaIni") != null) ? session
				.getAttribute("fechaIni").toString() : "";
		String fechaFin = (session.getAttribute("fechaFin") != null) ? session
				.getAttribute("fechaFin").toString() : "";		

		model.addAttribute("clientes", servicioCliente.ObtenerReporte(nombre, fechaIni, fechaFin));
		httpServletResponse.setHeader("Content-Disposition", "inline; filename=excel.xls");
		httpServletResponse.setContentType("application/vnd.ms-excel");
		
		

		return "ReporteExcel";
	}
}
