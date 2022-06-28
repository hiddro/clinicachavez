package com.peru.srv.clinicachavez.utils;

import lombok.extern.slf4j.Slf4j;

import static com.peru.srv.clinicachavez.utils.Constant.*;

@Slf4j
public class FunctionsUtils {

    public Boolean validateKey(String keyToString){
        log.info("Validado Key!");
        return keyToString.equalsIgnoreCase(ACTIVO) || keyToString.equalsIgnoreCase(INACTIVO) ? true : false;
    }

}
