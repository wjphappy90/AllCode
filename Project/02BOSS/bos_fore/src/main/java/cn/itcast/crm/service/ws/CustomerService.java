
package cn.itcast.crm.service.ws;

import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "CustomerService", targetNamespace = "http://ws.service.crm.itcast.cn/")
@XmlSeeAlso({
})
public interface CustomerService {


    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "save", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.Save")
    @ResponseWrapper(localName = "saveResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.SaveResponse")
    public void save(
        @WebParam(name = "arg0", targetNamespace = "")
        Customer arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "assignCustomers2FixedArea", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.AssignCustomers2FixedArea")
    @ResponseWrapper(localName = "assignCustomers2FixedAreaResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.AssignCustomers2FixedAreaResponse")
    public void assignCustomers2FixedArea(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns cn.itcast.crm.service.ws.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findByEmailAndPassword", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByEmailAndPassword")
    @ResponseWrapper(localName = "findByEmailAndPasswordResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByEmailAndPasswordResponse")
    public Customer findByEmailAndPassword(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

    /**
     * 
     * @return
     *     returns java.util.List<cn.itcast.crm.service.ws.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findByFixedAreaIdIsNull", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByFixedAreaIdIsNull")
    @ResponseWrapper(localName = "findByFixedAreaIdIsNullResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByFixedAreaIdIsNullResponse")
    public List<Customer> findByFixedAreaIdIsNull();

    /**
     * 
     * @param arg0
     */
    @WebMethod
    @RequestWrapper(localName = "activeMail", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.ActiveMail")
    @ResponseWrapper(localName = "activeMailResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.ActiveMailResponse")
    public void activeMail(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns cn.itcast.crm.service.ws.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findByTelephone", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByTelephone")
    @ResponseWrapper(localName = "findByTelephoneResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByTelephoneResponse")
    public Customer findByTelephone(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg0
     * @return
     *     returns java.util.List<cn.itcast.crm.service.ws.Customer>
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findByFixedAreaId", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByFixedAreaId")
    @ResponseWrapper(localName = "findByFixedAreaIdResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByFixedAreaIdResponse")
    public List<Customer> findByFixedAreaId(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns cn.itcast.crm.service.ws.Customer
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "findByTelephoneAndPassword", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByTelephoneAndPassword")
    @ResponseWrapper(localName = "findByTelephoneAndPasswordResponse", targetNamespace = "http://ws.service.crm.itcast.cn/", className = "cn.itcast.crm.service.ws.FindByTelephoneAndPasswordResponse")
    public Customer findByTelephoneAndPassword(
        @WebParam(name = "arg0", targetNamespace = "")
        String arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        String arg1);

}