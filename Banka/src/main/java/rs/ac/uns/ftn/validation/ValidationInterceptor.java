package rs.ac.uns.ftn.validation;

import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.server.endpoint.interceptor.PayloadValidatingInterceptor;
import org.springframework.xml.xsd.XsdSchema;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ValidationInterceptor extends PayloadValidatingInterceptor {

    private Map<String, XsdSchema> schemas = new HashMap<>();

    @Override
    protected final Source getValidationRequestSource(WebServiceMessage webServiceMessage_) {
        Source _source = null;
        try {
            _source = webServiceMessage_.getPayloadSource();
            setCorrectSchema(_source);
          } catch (SoapFaultClientException _soapException) {
          } catch (Exception _exception) {
          }

      return _source;
    }

    private String getNamespace(final Source source_) {
        DOMSource _currentdomSource = (DOMSource) source_;
        Node _currentNode = _currentdomSource.getNode();

        if (_currentNode != null) {
            return _currentNode.getNamespaceURI();
         } else {
             logger.warn("The current namespace could not be determined");
         }
        return null;
    }


    private void setCorrectSchema(Source source_) throws IOException, SAXException {

        final String _namespace = getNamespace(source_);

        if (_namespace != null) {
            SchemaFactory _schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            XsdSchema _schema = schemas.get(_namespace);
            setXsdSchema(_schema);
        } else {
            setFaultStringOrReason("Could not determine namespace!");
        }
    }

    public void setSchemas(Map<String, XsdSchema> schemas) {
        this.schemas = schemas;
    }
}