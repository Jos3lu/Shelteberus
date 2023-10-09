package com.hiberus.services.impl;

import com.hiberus.models.Adoption;
import com.hiberus.repositories.AdoptionsRepository;
import com.hiberus.services.AdoptionsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AdoptionsServiceImpl implements AdoptionsService {
    @Autowired
    private AdoptionsRepository adoptionsRepository;

    @Override
    public List<Adoption> getAdoptions() {
        log.info("Adoptions sent");
        return adoptionsRepository.findAll();
    }
}
