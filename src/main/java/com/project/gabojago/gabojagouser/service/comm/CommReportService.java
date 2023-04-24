package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReportDto;

import java.util.List;

public interface CommReportService {
    List<CommReportDto> list();
    CommReportDto detail();
    int register(CommReportDto commReport);
    int remove(CommReportDto commReport);


}
