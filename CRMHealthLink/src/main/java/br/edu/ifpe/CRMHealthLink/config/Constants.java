package br.edu.ifpe.CRMHealthLink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {


    public static Long timeSlot;
    @Value("${crmhealthlink.constants.timeSlot}")
    public void setTimeSlot(Long timeSlot){
        this.timeSlot = timeSlot;
    }
}
