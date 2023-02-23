/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.exavalu.pharmacymgmt.models;

import com.exavalu.pharmacymgmt.services.LoginService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import org.apache.log4j.Logger;
import org.apache.struts2.dispatcher.ApplicationMap;
import org.apache.struts2.dispatcher.SessionMap;
import org.apache.struts2.interceptor.ApplicationAware;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Biswajit
 */
public class Login extends ActionSupport implements ApplicationAware, SessionAware, Serializable {

    private String emailId;
    private String password;
    
    
    private ApplicationMap map = (ApplicationMap) ActionContext.getContext().getApplication();
    private SessionMap<String, Object> sessionMap = (SessionMap) ActionContext.getContext().getSession();
    private static final Logger logger = Logger.getLogger(Login.class.getName());

    @Override
    public void setApplication(Map<String, Object> application) {
        map = (ApplicationMap) application;
    }

    @Override
    public void setSession(Map<String, Object> session) {
        sessionMap = (SessionMap) session;
    }

    /**
     * @return the emailId
     */
    public String getEmailId() {
        return emailId;
    }

    /**
     * @param emailId the emailId to set
     */
    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public String dologin() {

        String result = LoginService.doLogin(this.emailId, this.password);
        if (result.equalsIgnoreCase("ADMIN")) {
            ArrayList verifiedEmpList = EmployeeService.getAllEmployee();
            System.out.println("Returning Success From ADMIN Login");
            sessionMap.put("ADMIN", Admin.getInstance());
            sessionMap.put("VerifiedEmployee", verifiedEmpList);
            result = "ADMIN";
        } else if (result.equalsIgnoreCase("EMPLOYEE")) {
            System.out.println("Returning Success From EMPLOYEE Login");
            sessionMap.put("EMPLOYEE", Employee.getInstance());
            result = "EMPLOYEE";
        }else{
            logger.error("Something error in login page. TIME:"+ LocalDateTime.now());
            result="FAILURE";
        }

        return result;
    }

    public String doLogout() {
        sessionMap.clear();
        return "SUCCESS";
    }
}
