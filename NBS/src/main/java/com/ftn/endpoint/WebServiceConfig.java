package com.ftn.endpoint;

import com.ftn.exception.DetailSoapFaultDefinitionExceptionResolver;
import com.ftn.exception.ServiceFaultException;
import com.ftn.validation.ValidationInterceptor;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {

	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver(){

		final SoapFaultMappingExceptionResolver exceptionResolver = new DetailSoapFaultDefinitionExceptionResolver();

		final SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
		faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		exceptionResolver.setDefaultFault(faultDefinition);

		final Properties errorMappings = new Properties();
		errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
		errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
		exceptionResolver.setExceptionMappings(errorMappings);
		exceptionResolver.setOrder(1);
		return exceptionResolver;
	}

	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {

		final ValidationInterceptor validationInterceptor = new ValidationInterceptor();
		final Map<String, XsdSchema> schemas = new HashMap<>();
		schemas.put(Mt102Endpoint.NAMESPACE_URI, mt102schema());
		schemas.put(Mt103Endpoint.NAMESPACE_URI, mt103schema());
		validationInterceptor.setSchemas(schemas);
		interceptors.add(validationInterceptor);
	}

	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "mt103")
	public DefaultWsdl11Definition mt103Wsdl11Definition(XsdSchema mt103schema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("mt103");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt103");
		wsdl11Definition.setSchema(mt103schema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema mt103schema() {
		return new SimpleXsdSchema(new ClassPathResource("mt103.xsd"));
	}

	@Bean(name = "mt102")
	public DefaultWsdl11Definition mt102Wsdl11Definition(XsdSchema mt102schema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("mt102");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt102");
		wsdl11Definition.setSchema(mt102schema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema mt102schema() {
		return new SimpleXsdSchema(new ClassPathResource("mt102.xsd"));
	}
}
