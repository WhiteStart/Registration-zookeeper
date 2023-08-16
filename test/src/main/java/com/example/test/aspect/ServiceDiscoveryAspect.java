//package com.example.test.aspect;
//
//import org.apache.curator.x.discovery.ServiceDiscovery;
//import org.apache.curator.x.discovery.ServiceInstance;
//import org.apache.curator.x.discovery.details.ServiceDiscoveryImpl;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.springframework.cloud.bootstrap.BootstrapConfiguration;
//import org.springframework.stereotype.Component;
//
//@Aspect
//@BootstrapConfiguration
//public class ServiceDiscoveryAspect {
//
//    @After("execution(* org.apache.curator.x.discovery.details.ServiceDiscoveryImpl.registerService(..)) && args(service)")
//    public void afterInternalRegisterService(ServiceInstance<?> service) {
//        // Your custom logic after internalRegisterService is called
//        System.out.println("------Custom logic after internalRegisterService");
//    }
//
////    private ServiceDiscoveryImpl
//}
