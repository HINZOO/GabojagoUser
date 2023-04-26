package com.project.gabojago.gabojagouser.service.comm;

import com.project.gabojago.gabojagouser.dto.comm.CommReportDto;
import com.project.gabojago.gabojagouser.mapper.comm.CommReportMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommReportServiceImpl implements CommReportService {
    private CommReportMapper commReportMapper;
    @Override
    public List<CommReportDto> list() {

        return null;
    }

    @Override
    public CommReportDto detail() {
        return null;
    }

    @Override
    public int register(CommReportDto commReport) {
        int register=commReportMapper.insertOne(commReport);
        return register;
    }

    @Override
    public int remove(CommReportDto commReport) {
        return 0;
    }
}
