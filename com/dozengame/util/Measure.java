package com.dozengame.util;

public class Measure {
	/**
	 * �߽���
	 * @param x  Ҫ����x����
	 * @param y  Ҫ����y����
	 * @param minx ��С��x����
	 * @param miny ��С��y����
	 * @param maxx ����x����
	 * @param maxy ����y����
	 * @return
	 */
	public static boolean isInnerBorder(float x,float y, int minx,int miny,int maxx,int maxy){
		
		if(x<minx || x>maxx || y<miny || y>maxy){
			return false;
		}
		return true;
	}
}
