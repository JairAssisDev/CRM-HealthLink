package br.edu.ifpe.CRMHealthLink.dto.mapper;

import br.edu.ifpe.CRMHealthLink.dto.appointment.AppointmentCreateDto;
import br.edu.ifpe.CRMHealthLink.entity.Appointment;
import org.modelmapper.ModelMapper;

public class AppointmentMapper {
    private static final ModelMapper modelMapper = new ModelMapper();

    public static Appointment toAppointment(AppointmentCreateDto appointmentCreateDto) {
        return modelMapper.map(appointmentCreateDto, Appointment.class);
    }
}
