package com.tl.dto;

import lombok.Data;

@Data
public class StageDto {
    private String prfplcnm;           // �������
    private String mt13id;             // ������ ID
    private String seatscale;          // �¼��Ը�
    private String stageorchat;        // ����ü�_���ɽ�Ʈ����Ʈ
    private String stagepracat;        // ����ü�_������
    private String stagedresat;        // ����ü�_�����
    private String stageoutdrat;       // ����ü�_�߿ܰ�����
    private String disabledseatscale;  // ����νü�_������
    private String stagearea;          // ����ü�_�������
}
