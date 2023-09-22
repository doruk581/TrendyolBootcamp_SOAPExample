package com.trendyol.student;


import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

/*
*
* Author: Doruk S. 20.09.2023
* Contact: doruk.su@trendyol.com
*
* WebServiceConfig sınıfı Spring Boot uygulamanızda SOAP web servisleri oluşturmak için bir konfigürasyon dosyasıdır.
* @EnableWs ve @Configuration anotasyonları ile Spring'e bu sınıfın bir web servis konfigürasyonu olduğu belirtilir.
*
* @EnableWs: Spring WS'nin özelliklerini etkinleştirir.
*
* @Configuration: Bu sınıfın Spring konfigürasyon sınıfı olduğunu belirtir.
*
* messageDispatcherServlet(ApplicationContext applicationContext) : Bu bean, SOAP mesajlarını işlemek için kullanılan MessageDispatcherServlet'i yapılandırır.
* Aynı zamanda bu servlet'in hangi URL kalıplarını işleyeceğini de belirler ("/ws/*")
*
* wsdl11Definition.setPortTypeName("StudentsPort"): Port tipini belirtir.
wsdl11Definition.setLocationUri("/ws"): Bu servisin nerede olacağını belirtir.
wsdl11Definition.setTargetNamespace(...): Hedef namespace'i belirtir.
wsdl11Definition.setSchema(studentsSchema): Kullanacağınız şema. Bu şema genellikle XSD (XML Schema Definition) dosyasından gelir.

*   Bir SOAP isteği geldiğinde, MessageDispatcherServlet devreye girer.
    MessageDispatcherServlet uygulama bağlamını (ApplicationContext) kontrol eder ve uygun web servis endpointini bulur.
    Sonrasında DefaultWsdl11Definition SOAP mesajını işler ve ilgili servise yönlendirir.
    İlgili servis XSD şemasına uygun olup olmadığını kontrol eder. Eğer uygunsuz ise hata fırlatılır.
* */

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext applicationContext) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(applicationContext);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "students")
    public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema studentsSchema) {
        DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
        wsdl11Definition.setPortTypeName("StudentsPort");
        wsdl11Definition.setLocationUri("/ws");
        wsdl11Definition.setTargetNamespace("http://spring.io/guides/gs-producing-web-service");
        wsdl11Definition.setSchema(studentsSchema);
        return wsdl11Definition;
    }

    @Bean
    public XsdSchema countriesSchema() {
        return new SimpleXsdSchema(new ClassPathResource("students.xsd"));
    }
}