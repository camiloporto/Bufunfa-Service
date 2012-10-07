package br.com.bufunfa.finance.account.service.util;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/spring/applicationContext.xml")
@ActiveProfiles(profiles = {"local", "unit-test"})
public class SpringRootTestsConfiguration {

	@Test
	@Ignore
	public void testFoo(){}
	
	/**
	 * TODO Resolver problema de ambientes do sistema usando profiles do SPring. 
	 * - Definir active profile 'local' para testes unitarios
	 * - no web.xml usar alguma propriedade maven para alterar. e usar filtering. deixar o default como 'local' para rodar testes unitarios
	 * - colocar, a principio, apenas as conexoes de banco (dataSource) em profiles diferentes
	 */
	//TODO Adicionar configuracoes dos ambientes de tests (um localmente com persistencia; um no cloud-homologacao; e um no cloud-producao).
	// Criar DataSource de teste (acessado via JNDI no Tomcat local)
	// Criar dataSource de homologacao (acessado no cloud de hmg)
	// Criar dataSource de producao (acessado no cloud de prd)
}
