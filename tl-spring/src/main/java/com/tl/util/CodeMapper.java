package com.tl.util;

	import java.util.HashMap;
	import java.util.Map;

	public class CodeMapper {
	    public static final Map<String, String> REGION_MAP = new HashMap<>();
	    public static final Map<String, String> GENRE_MAP = new HashMap<>();

	    static {
	        // ���� �ڵ� ����
	        REGION_MAP.put("����Ư����", "11");
	        REGION_MAP.put("�λ걤����", "26");
	        REGION_MAP.put("�뱸������", "27");
	        REGION_MAP.put("��õ������", "28");
	        REGION_MAP.put("���ֱ�����", "29");
	        REGION_MAP.put("����������", "30");
	        REGION_MAP.put("��걤����", "31");
	        REGION_MAP.put("����Ư����ġ��", "36");
	        REGION_MAP.put("��⵵", "41");
	        REGION_MAP.put("����Ư����ġ��", "51");
	        REGION_MAP.put("��û�ϵ�", "43");
	        REGION_MAP.put("��û����", "44");
	        REGION_MAP.put("����ϵ�", "45");
	        REGION_MAP.put("���󳲵�", "46");
	        REGION_MAP.put("���ϵ�", "47");
	        REGION_MAP.put("��󳲵�", "48");
	        REGION_MAP.put("����Ư����ġ��", "50");

	        // �帣 �ڵ� ����
	        GENRE_MAP.put("����", "AAAA");
	        GENRE_MAP.put("����(����/�ѱ�����)", "BBBC");
	        GENRE_MAP.put("���߹���", "BBBE");
	        GENRE_MAP.put("��������(Ŭ����)", "CCCA");
	        GENRE_MAP.put("�ѱ�����(����)", "CCCC");
	        GENRE_MAP.put("��������", "CCCD");
	        GENRE_MAP.put("����", "EEEA");
	        GENRE_MAP.put("��Ŀ��/����", "EEEB");
	        GENRE_MAP.put("������", "GGGA");
	    }

	    public static String getRegionCode(String regionName) {
	        return REGION_MAP.getOrDefault(regionName, null); // ���� ��� null ��ȯ
	    }

	    public static String getGenreCode(String genreName) {
	        return GENRE_MAP.getOrDefault(genreName, null); // ���� ��� null ��ȯ
	    }
}
