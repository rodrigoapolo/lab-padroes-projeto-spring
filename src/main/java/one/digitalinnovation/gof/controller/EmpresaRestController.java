package one.digitalinnovation.gof.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import one.digitalinnovation.gof.model.Empresa;
import one.digitalinnovation.gof.service.EmpresaService;

/**
 * Esse {@link RestController} representa nossa <b>Facade</b>, pois abstrai toda
 * a complexidade de integrações (Banco de Dados H2 e API do ViaCEP) em uma
 * interface simples e coesa (API REST).
 * 
 * @author rodrigoapolo
 */
@RestController
@RequestMapping("empresas")
public class EmpresaRestController {

	@Autowired
	private EmpresaService empresaService;

	@GetMapping
	public ResponseEntity<Iterable<Empresa>> buscarTodos() {
		return ResponseEntity.ok(empresaService.buscarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Empresa> buscarPorId(@PathVariable Long id) {
		return ResponseEntity.ok(empresaService.buscarPorId(id));
	}

	@PostMapping
	public ResponseEntity<Empresa> inserir(@RequestBody Empresa empresa) {
		empresaService.inserir(empresa);
		return ResponseEntity.ok(empresa);
	}

	@PutMapping("/{id}")
	public ResponseEntity<Empresa> atualizar(@PathVariable Long id, @RequestBody Empresa empresa) {
		empresaService.atualizar(id, empresa);
		return ResponseEntity.ok(empresa);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deletar(@PathVariable Long id) {
		empresaService.deletar(id);
		return ResponseEntity.ok().build();
	}
}
