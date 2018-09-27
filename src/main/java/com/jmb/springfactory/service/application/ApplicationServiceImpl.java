package com.jmb.springfactory.service.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    
    @Autowired
    private InfoApplicationComponent infoApplicationComponent;

    @Override
    public String getApplicationVersion() {
        return infoApplicationComponent.getVersion();
    }

    @Override
    public String getApplicationName() {
        return infoApplicationComponent.getName();
    }
}
