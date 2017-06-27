package rs.ac.uns.ftn.endpoint;

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
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;
import rs.ac.uns.ftn.exception.DetailSoapFaultDefinitionExceptionResolver;
import rs.ac.uns.ftn.exception.ServiceFaultException;
import rs.ac.uns.ftn.validation.ValidationInterceptor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Jasmina on 23/06/2017.
 */
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
        schemas.put(Mt900Endpoint.NAMESPACE_URI, mt900schema());
        schemas.put(Mt910Endpoint.NAMESPACE_URI, mt910schema());
        schemas.put(NalogEndpoint.NAMESPACE_URI, nalogzaprenos());
        schemas.put(PresekEndpoint.NAMESPACE_URI, presekSchema());
        schemas.put(ZahtevEndpoint.NAMESPACE_URI, zahtevSchema());
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


    @Bean(name = "mt102")
    public DefaultWsdl11Definition Mt102SchemaDefinition(XsdSchema mt102schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("Mt102Port");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt102");
        wsdl11Definition.setSchema(mt102schema);
        return wsdl11Definition;
    }


    @Bean
    public XsdSchema mt102schema() {
        return new SimpleXsdSchema(new ClassPathResource("mt102.xsd"));
    }

    @Bean(name = "mt103")
    public DefaultWsdl11Definition Mt103SchemaDefinition(XsdSchema mt103schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("Mt103Port");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt103");
        wsdl11Definition.setSchema(mt103schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema mt103schema() {
        return new SimpleXsdSchema(new ClassPathResource("mt103.xsd"));
    }

    @Bean(name = "mt900")
    public DefaultWsdl11Definition Mt900SchemaDefinition(XsdSchema mt900schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("Mt900Port");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt900");
        wsdl11Definition.setSchema(mt900schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema mt900schema() {
        return new SimpleXsdSchema(new ClassPathResource("mt900.xsd"));
    }

    @Bean(name = "mt910")
    public DefaultWsdl11Definition Mt910SchemaDefinition(XsdSchema mt910schema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("Mt910Port");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/mt910");
        wsdl11Definition.setSchema(mt910schema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema mt910schema() {
        return new SimpleXsdSchema(new ClassPathResource("mt910.xsd"));
    }

    @Bean(name = "nalog_za_prenos")
    public DefaultWsdl11Definition NalogZaPrenosSchemaDefinition(XsdSchema nalogzaprenos) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("NalogPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/nalog_za_prenos");
        wsdl11Definition.setSchema(nalogzaprenos);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema nalogzaprenos() {
        return new SimpleXsdSchema(new ClassPathResource("nalog_za_prenos.xsd"));
    }

    @Bean(name = "presek")
    public DefaultWsdl11Definition PresekSchemaDefinition(XsdSchema presekSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("PresekPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/presek");
        wsdl11Definition.setSchema(presekSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema presekSchema() {
        return new SimpleXsdSchema(new ClassPathResource("presek.xsd"));
    }

    @Bean(name = "zahtevZaIzvod")
    public DefaultWsdl11Definition ZahtevZaIzvodSchemaDefinition(XsdSchema zahtevSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("ZahtevZaIzvodPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://www.ftn.uns.ac.rs/zahtevZaIzvod");
        wsdl11Definition.setSchema(zahtevSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema zahtevSchema() {
        return new SimpleXsdSchema(new ClassPathResource("zahtev_za_izvod.xsd"));
    }


}
