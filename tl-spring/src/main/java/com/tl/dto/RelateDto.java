package com.tl.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ŭ������ ���ǵ��� ���� �ʵ尡 xml�� �־ ����
public class RelateDto {
    private String relatenm;  // ����ó��
    private String relateurl; // ����ó URL
}